package com.bigfish.pairtoper.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.bigfish.pairtoper.game.actors.button.AButton
import com.bigfish.pairtoper.game.screens.MenuScreen
import com.bigfish.pairtoper.game.screens.GameScreen
import com.bigfish.pairtoper.game.screens.ResultFailScreen
import com.bigfish.pairtoper.game.screens.SettScreen
import com.bigfish.pairtoper.game.utils.Block
import com.bigfish.pairtoper.game.utils.TIME_ANIM_SCREEN
import com.bigfish.pairtoper.game.utils.actor.animDelay
import com.bigfish.pairtoper.game.utils.actor.animHide
import com.bigfish.pairtoper.game.utils.actor.animShow
import com.bigfish.pairtoper.game.utils.actor.setBounds
import com.bigfish.pairtoper.game.utils.actor.setOnClickListener
import com.bigfish.pairtoper.game.utils.advanced.AdvancedMainGroup
import com.bigfish.pairtoper.game.utils.gdxGame

class AMainResultFail(override val screen: ResultFailScreen): AdvancedMainGroup() {

    private val imgPanel = Image(gdxGame.assetsAll.RES)
    private val listBtn  = List(2) { Actor() }
    private val imgText  = Image(gdxGame.assetsAll.lose)

    override fun addActorsOnGroup() {
        color.a = 0f

        gdxGame.soundUtil.apply { play(lose) }

        addImgPanel()
        addBtn()

        addActor(imgText)
        imgText.setBounds(357f, 1174f, 366f, 150f)

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgPanel() {
        addActor(imgPanel)
        imgPanel.setBounds(140f, 398f, 778f, 1027f)
    }

    private fun addBtn() {
        listBtn.forEachIndexed { index, image ->
            addActor(image)
            when(index) {
                0 -> {
                    image.setBounds(276f, 941f, 504f, 192f)
                    image.setOnClickListener(gdxGame.soundUtil) {
                        screen.hideScreen {
                            //gdxGame.navigationManager.clearBackStack()
                            gdxGame.navigationManager.navigate(GameScreen::class.java.name)
                        }
                    }
                }
                1 -> {
                    image.setBounds(276f, 714f, 504f, 192f)
                    image.setOnClickListener(gdxGame.soundUtil) {
                        screen.hideScreen {
                            gdxGame.navigationManager.navigate(SettScreen::class.java.name)
                            //gdxGame.activity.webViewHelper.loadUrl(gdxGame.activity.getPrivacyURL(), false)
                        }
                    }
                }

            }
        }
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
//        screen.stageBack.root.animShow(TIME_ANIM_SCREEN)

        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
//        screen.stageBack.root.animHide(TIME_ANIM_SCREEN)

        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

}