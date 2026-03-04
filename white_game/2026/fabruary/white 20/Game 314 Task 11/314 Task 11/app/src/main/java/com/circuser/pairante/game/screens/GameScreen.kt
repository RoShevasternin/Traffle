package com.circuser.pairante.game.screens

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.circuser.pairante.game.actors.ATimer
import com.circuser.pairante.game.actors.ATmpGroup
import com.circuser.pairante.game.actors.WTF
import com.circuser.pairante.game.actors.button.AButton
import com.circuser.pairante.game.utils.Block
import com.circuser.pairante.game.utils.TIME_ANIM_SCREEN
import com.circuser.pairante.game.utils.actor.addActorWithConstraints
import com.circuser.pairante.game.utils.actor.addActors
import com.circuser.pairante.game.utils.actor.addAndFillActor
import com.circuser.pairante.game.utils.actor.animDelay
import com.circuser.pairante.game.utils.actor.animHide
import com.circuser.pairante.game.utils.actor.animShow
import com.circuser.pairante.game.utils.actor.disable
import com.circuser.pairante.game.utils.actor.enable
import com.circuser.pairante.game.utils.actor.setBounds
import com.circuser.pairante.game.utils.actor.setOnClickListener
import com.circuser.pairante.game.utils.advanced.AdvancedGroup
import com.circuser.pairante.game.utils.advanced.AdvancedScreen
import com.circuser.pairante.game.utils.gdxGame
import com.circuser.pairante.game.utils.region

class GameScreen: AdvancedScreen() {

    private val aMenuGroup = ATmpGroup(this)
    private val aBackBtn   = AButton(this, AButton.Type.BACK)

    data class Data(
        val id    : Int,
        val region: TextureRegion,
    )

    private val list16   by lazy { List(8) { index -> Data(index.inc(), gdxGame.assetsAll.listItems[index].region) } }
    private val dataList by lazy { list16 + list16 }

    private var firstOpenClose : WTF? = null
    private var secondOpenClose: WTF? = null

    private var firstData : Data?    = null
    private var secondData: Data?    = null

    private var countPair = 0

    override fun show() {
        setBackBackground(if (SelecteScreen.INDEX == 0) gdxGame.assetsAll.B_GAME else gdxGame.assetsAll.B_DEF)
        super.show()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        addMenuGroup()
        addBackBtn()

        animShowScreen()
    }

    override fun animHideScreen(blockEnd: Block) {
        stageUI.root.animHide(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    override fun animShowScreen(blockEnd: Block) {
        stageUI.root.animShow(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    // Actors ------------------------------------------------------------------------

    private fun Group.addMenuGroup() {
        aMenuGroup.setSize(839f, 1077f)
        addActorWithConstraints(aMenuGroup) {
            startToStartOf   = this@addMenuGroup
            endToEndOf       = this@addMenuGroup
            topToTopOf       = this@addMenuGroup
            bottomToBottomOf = this@addMenuGroup
        }

        val aMenuImg = Image(gdxGame.assetsAll.GAME)
        aMenuGroup.addAndFillActor(aMenuImg)

        val aTimer = ATimer(this@GameScreen)
        aMenuGroup.addActor(aTimer)
        aTimer.setBounds(344f, 963f, 152f, 74f)
        aTimer.startTimer(60)
        aTimer.finishBlock = {
            animHideScreen { gdxGame.navigationManager.navigate(ResultFailScreen::class.java.name) }
        }

        aMenuGroup.addItems()
    }

    private fun Group.addBackBtn() {
        aBackBtn.setSize(100f, 100f)
        addActorWithConstraints(aBackBtn) {
            startToStartOf   = this@addBackBtn
            topToTopOf       = this@addBackBtn

            marginStart = 96f
            marginTop   = 96f
        }

        aBackBtn.setOnClickListener { animHideScreen { gdxGame.navigationManager.back() } }
    }

    private fun Group.addItems() {
        var newX = 24f
        var newY = 671f

        dataList.shuffled().onEachIndexed { index, data ->
            WTF(this@GameScreen).also { item ->
                addActor(item)
                item.image.drawable = TextureRegionDrawable(data.region)

                item.setBounds(newX, newY, 144f, 144f)
                newX += 71 + 144

                if (index.inc() % 4 == 0) {
                    newX = 24f
                    newY -= 71 + 144
                }

                item.setOnClickListener {
                    gdxGame.soundUtil.apply { play(click) }

                    item.disable()
                    item.open {
                        if (firstOpenClose == null) {
                            firstOpenClose = item
                            firstData      = data
                        } else {
                            this.disable()
                            secondOpenClose = item
                            secondData      = data

                            if (firstData?.id != secondData?.id) {
                                this.clearActions()

                                // fail
                                gdxGame.soundUtil.apply { play(lose_game) }

                                animDelay(0.4f) {
                                    firstOpenClose?.enable()
                                    secondOpenClose?.enable()

                                    firstOpenClose?.close()
                                    secondOpenClose?.close {
                                        firstOpenClose  = null
                                        secondOpenClose = null
                                        this.enable()
                                    }
                                }
                            } else {
                                this.clearActions()

                                // win
                                gdxGame.soundUtil.apply { play(win_game) }

                                animDelay(0.4f) {
                                    this.enable()

                                    firstOpenClose  = null
                                    secondOpenClose = null

                                    if (++countPair == 8) gdxGame.navigationManager.navigate(ResultDoneScreen::class.java.name)
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}