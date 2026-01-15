package com.princesssand.soundsprinss.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.princesssand.soundsprinss.game.GDXGame
import com.princesssand.soundsprinss.game.actors.ATutorials
import com.princesssand.soundsprinss.game.actors.button.AButton
import com.princesssand.soundsprinss.game.actors.panel.APanelLevels
import com.princesssand.soundsprinss.game.box2d.AbstractBody
import com.princesssand.soundsprinss.game.box2d.BodyId
import com.princesssand.soundsprinss.game.box2d.bodies.BBall
import com.princesssand.soundsprinss.game.box2d.bodies.BItem
import com.princesssand.soundsprinss.game.box2d.bodiesGroup.BGBorders
import com.princesssand.soundsprinss.game.utils.GameColor
import com.princesssand.soundsprinss.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.princesssand.soundsprinss.game.utils.actor.animHide
import com.princesssand.soundsprinss.game.utils.actor.animShow
import com.princesssand.soundsprinss.game.utils.actor.animShowSuspend
import com.princesssand.soundsprinss.game.utils.actor.setOnClickListener
import com.princesssand.soundsprinss.game.utils.advanced.AdvancedStage
import com.princesssand.soundsprinss.game.utils.advanced.AdvancedUserScreen
import com.princesssand.soundsprinss.game.utils.font.FontParameter
import com.princesssand.soundsprinss.game.utils.region
import com.princesssand.soundsprinss.game.utils.runGDX
import com.princesssand.soundsprinss.game.utils.toB2
import com.princesssand.soundsprinss.game.utils.toUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameScreen(override val game: GDXGame): AdvancedUserScreen() {

    companion object {
        var isFastGame = false
        var isBigBall  = false
    }

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars + "/").setSize(40)
    private val font      = fontGenerator_Merienda.generateFont(parameter)

    // Actor
    private val backgroundImg = Image(game.assetsAll.game).apply { color.a = 0f }
    private val bordersImg    = Image(game.assetsAll.borders).apply { color.a = 0f }
    private val bluresImg     = Image(game.assetsAll.blures).apply { color.a = 0f }
    private val back          = AButton(this, AButton.Static.Type.Back).apply { color.a = 0f }
    private val resultFrameImg = Image(game.assetsAll.yellow_frame).apply { color.a = 0f }
    private val resultLbl      = Label("0/${APanelLevels.targetList[APanelLevels.LEVEL_INDEX]}", Label.LabelStyle(font, GameColor.yellow)).apply { color.a = 0f }
    private val princessImg    = Image(game.assetsAll.princess).apply { color.a = 0f }
    private val saveImg        = Image(game.assetsAll.save).apply { color.a = 0f }
    private val tutorials      = ATutorials(this).apply { color.a = 0f }

    // Body
    private val bBall     = BBall(this).apply { actor?.color?.a = 0f }
    private val bItemList = List(if (isFastGame) 12 else 6) { BItem(this, it).apply { actor?.color?.a = 0f } }

    // Body Group
    private val bgBorders = BGBorders(this)

    // Field
    private val resultFlow = MutableStateFlow(0)
    private val itemRandomX: Float get() = (59..1360).random().toFloat()
    private val itemRandomY: Float get() = (72..721).random().toFloat()

    override fun show() {
        game.musicUtil.apply { music = music_2.apply {
            isLooping = true
        } }

        setBackBackground(game.assetsLoader.loader.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        stageBack.addAndFillActor(backgroundImg)
        backgroundImg.animShow(1f) {
            runGDX {
                addBorders()
                addBlurs()
                addBack()
                addResult()
                addPrincess()
                addSave()

                createBG_Borders()
            }

            coroutine?.launch {
                bordersImg.animShowSuspend(0.7f)
                bluresImg.animShowSuspend(0.6f)

                withContext(Dispatchers.Default) {
                    launch { back.animShowSuspend(0.5f) }
                    launch {
                        resultFrameImg.animShowSuspend(0.4f)
                        resultLbl.animShowSuspend(0.4f)
                    }
                }

                princessImg.animShowSuspend(0.7f)
                runGDX {
                    princessImg.addAction(
                        Actions.forever(
                            Actions.sequence(
                                Actions.moveTo(976f, 140f, 5f, Interpolation.sineOut),
                                Actions.moveTo(117f, 140f, 10f, Interpolation.sine),
                                Actions.moveTo(532f, 140f, 5f, Interpolation.sineIn),
                            )
                        )
                    )

                    createB_Ball()
                    createB_ItemList()

                    if (game.isTutorialsUtil.isTutorials) addTutorials()
                }
                bBall.actor?.animShowSuspend(0.4f)
                bItemList.onEach { it.actor?.animShowSuspend(0.150f) }

                if (game.isTutorialsUtil.isTutorials) tutorials.animShowSuspend(TIME_ANIM_SCREEN_ALPHA)
            }
        }
    }

    // Add Actor
    private fun AdvancedStage.addTutorials() {
        addAndFillActor(tutorials)
    }

    private fun AdvancedStage.addBorders() {
        addActor(bordersImg)
        bordersImg.setBounds(2f, 18f, 1523f, 864f)
    }

    private fun AdvancedStage.addBlurs() {
        addActor(bluresImg)
        bluresImg.setBounds(2f, 791f, 1025f, 109f)
    }

    private fun AdvancedStage.addBack() {
        addActor(back)
        back.setBounds(45f, 832f, 98f, 58f)
        back.setOnClickListener {
            hideBlock { game.navigationManager.back() }
        }
    }

    private fun AdvancedStage.addResult() {
        addActors(resultFrameImg, resultLbl)
        resultFrameImg.setBounds(652f, 816f, 224f, 69f)

        resultLbl.apply {
            setBounds(657f, 823f, 214f, 58f)
            setAlignment(Align.center)
        }

        var isWin = false

        coroutine?.launch {
            resultFlow.collect {
                runGDX {
                    resultLbl.setText("$it/${APanelLevels.targetList[APanelLevels.LEVEL_INDEX]}")

                    if (it >= APanelLevels.targetList[APanelLevels.LEVEL_INDEX]) {
                        if (isWin.not()) {
                            isWin = true
                            game.soundUtil.apply { play(win) }
                        }

                        saveImg.addAction(
                            Actions.parallel(
                                Actions.fadeIn(0.3f),
                                Actions.moveTo(1084f, 823f, 0.5f, Interpolation.sineOut)
                            )
                        )
                    }
                }
            }
        }
    }

    private fun AdvancedStage.addPrincess() {
        addActor(princessImg)
        princessImg.setBounds(532f, 140f, 435f, 474f)
    }

    private fun AdvancedStage.addSave() {
        addActor(saveImg)
        saveImg.setBounds(1084f, HEIGHT, 184f, 77f)

        saveImg.setOnClickListener {
            game.soundUtil.apply { play(save) }

            game.resultUtil.update(APanelLevels.LEVEL_INDEX, resultFlow.value)
            game.levelUtil.apply { update(level + 1) }
            hideBlock { game.navigationManager.back() }
        }
    }

    // Create Body
    private fun createB_Ball() {
        if (game.isTutorialsUtil.isTutorials) {
            bBall.bodyDef.gravityScale = 0f

            this.touchDownBallBlock = {
                bBall.bodyDef.gravityScale = 1f

                game.isTutorialsUtil.update(false)

                tutorials.addAction(Actions.sequence(
                    Actions.fadeOut(TIME_ANIM_SCREEN_ALPHA),
                    Actions.removeActor()
                ))
            }
        }

        val size = if (isBigBall) 150f else 88f

        bBall.create(720f, 406f, size, size)

        bBall.beginContactBlockArray.add(AbstractBody.ContactBlock { enemy, contact ->
            when(enemy.id) {
                BodyId.DYNAMIC -> {
                    enemy as BItem
                    enemy.startEffect(contact.worldManifold.points.first().toUI)
                    game.soundUtil.apply { play(get) }

                    resultFlow.value += (1..5).random()

                    runGDX {
                        enemy.body?.setLinearVelocity(0f, 0f)
                        enemy.body?.angularVelocity = 0f
                        enemy.body?.setTransform(
                            itemRandomX.toB2 + enemy.center.x,
                            itemRandomY.toB2 + enemy.center.y,
                            0f
                        )
                    }
                }
            }
        })
    }

    private fun createB_ItemList() {
        bItemList.onEach {
            it.create(itemRandomX, itemRandomY, 109f, 109f)
            it.addEffect()
        }
    }

    // Create Body Group
    private fun createBG_Borders() {
        bgBorders.create(44f, 50f, 1440f, 800f)
    }

    // Logic
    private fun hideBlock(block: () -> Unit) {
        stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) {
            backgroundImg.animHide(TIME_ANIM_SCREEN_ALPHA) {
                game.musicUtil.apply { music = music_1.apply {
                    isLooping = true
                } }
                block()
            }
        }
    }

}