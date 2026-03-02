package com.monkeystreet.roadracejungle.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.monkeystreet.roadracejungle.game.actors.AEggPanelA
import com.monkeystreet.roadracejungle.game.actors.AEggPanelB
import com.monkeystreet.roadracejungle.game.actors.ATmpGroup
import com.monkeystreet.roadracejungle.game.utils.Block
import com.monkeystreet.roadracejungle.game.utils.GameState
import com.monkeystreet.roadracejungle.game.utils.GameStateMachine
import com.monkeystreet.roadracejungle.game.utils.HEIGHT_UI
import com.monkeystreet.roadracejungle.game.utils.TIME_ANIM_SCREEN
import com.monkeystreet.roadracejungle.game.utils.WIDTH_UI
import com.monkeystreet.roadracejungle.game.utils.actor.HAlign
import com.monkeystreet.roadracejungle.game.utils.actor.VAlign
import com.monkeystreet.roadracejungle.game.utils.actor.addActorAligned
import com.monkeystreet.roadracejungle.game.utils.actor.addActors
import com.monkeystreet.roadracejungle.game.utils.actor.animDelay
import com.monkeystreet.roadracejungle.game.utils.actor.animHide
import com.monkeystreet.roadracejungle.game.utils.actor.animShow
import com.monkeystreet.roadracejungle.game.utils.actor.disable
import com.monkeystreet.roadracejungle.game.utils.actor.enable
import com.monkeystreet.roadracejungle.game.utils.actor.setOnClickListener
import com.monkeystreet.roadracejungle.game.utils.advanced.AdvancedScreen
import com.monkeystreet.roadracejungle.game.utils.font.FontParameter
import com.monkeystreet.roadracejungle.game.utils.gdxGame
import com.monkeystreet.roadracejungle.util.log

class GameScreen(): AdvancedScreen() {

    // Field
    private val listPointA = getListPointPosA()
    private val listPointB = getListPointPosB()

    private var pointCountA = listPointA.size
        set(value) {
            field = value
            lblA.setText("$value")
        }
    private var pointCountB = listPointB.size
        set(value) {
            field = value
            lblB.setText("$value")
        }

    // Font
    private val parameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font49    = fontGenerator_GochiHand_Regular.generateFont(parameter.setSize(49))

    // Actors
    private val aPANEL    = ATmpGroup(this)
    private val imgFinish = Image(gdxGame.assetsAll.FINISH)
    private val btnState  = Image(gdxGame.assetsAll.START)
    private val imgA      = Image(gdxGame.assetsAll.A)
    private val imgB      = Image(gdxGame.assetsAll.B)
    private val imgPanelA = Image(gdxGame.assetsAll.PANEL)
    private val imgPanelB = Image(gdxGame.assetsAll.PANEL)
    private val lblA      = Label("$pointCountA", Label.LabelStyle(font49, Color.WHITE))
    private val lblB      = Label("$pointCountB", Label.LabelStyle(font49, Color.WHITE))

    private val aEggPanelA = AEggPanelA(this)
    private val aEggPanelB = AEggPanelB(this)

    // Field

    private var blockState = { }
    private val stateMachine = GameStateMachine(GameUIHandler())

    private val PERS_START_SIZE  = 310f
    private val PERS_FINISH_SIZE = 78f
    private val personageScaleCoff = (PERS_START_SIZE - PERS_FINISH_SIZE) / listPointA.size

    override fun show() {
        stageUI.root.color.a = 0f

        setBackBackground(gdxGame.assetsAll.BACKGROUND_GAME)

        super.show()

        animShow { stateMachine.change(GameState.START) }
    }

    override fun animShow(blockEnd: Block) {
        stageUI.root.animShow(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    override fun animHide(blockEnd: Block) {
        stageUI.root.animHide(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    override fun Group.addActorsOnStageUI() {
        aPANEL.setSize(WIDTH_UI, HEIGHT_UI)
        addActorAligned(aPANEL, HAlign.CENTER, VAlign.CENTER)
        aPANEL.apply {
            addImgPoints()
            addImgFinish()
            addBtnState()
            addPanelAB()
            addImgAB()

            addEggPanelA()
            addEggPanelB()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun Group.addImgFinish() {
        addActor(imgFinish)
        imgFinish.setBounds(386f, 1569f, 307f, 307f)
    }

    private fun Group.addImgPoints() {
        listPointA.forEachIndexed { index, vector2 ->
            addActor(Image(gdxGame.assetsAll.POINT).also {
                it.setBounds(vector2.x, vector2.y, 46f, 46f)
            })
        }
        listPointB.forEachIndexed { index, vector2 ->
            addActor(Image(gdxGame.assetsAll.POINT).also {
                it.setBounds(vector2.x, vector2.y, 46f, 46f)
            })
        }
    }

    private fun Group.addBtnState() {
        addActor(btnState)
        btnState.setBounds(385f, 249f, 310f, 119f)

        btnState.setOnClickListener { blockState() }
    }

    private fun Group.addImgAB() {
        addActors(imgB, imgA)
        imgA.setBounds(28f, 65f, 310f, 310f)
        imgB.setBounds(742f, 65f, 310f, 310f)
    }

    private fun Group.addPanelAB() {
        addActors(imgPanelA, lblA, imgPanelB, lblB)
        imgPanelA.setBounds(96f, 16f, 173f, 98f)
        lblA.setBounds(149f, 36f, 68f, 60f)
        lblA.setAlignment(Align.center)

        imgPanelB.setBounds(810f, 16f, 173f, 98f)
        lblB.setBounds(863f, 36f, 68f, 60f)
        lblB.setAlignment(Align.center)
    }

    private fun Group.addEggPanelA() {
        addActors(aEggPanelA)
        aEggPanelA.setBounds(300f, 719f, 481f, 481f)
        aEggPanelA.color.a = 0f
    }

    private fun Group.addEggPanelB() {
        addActors(aEggPanelB)
        aEggPanelB.setBounds(300f, 719f, 481f, 481f)
        aEggPanelB.color.a = 0f
    }

    // Logic ------------------------------------------------------------------------

    private fun getListPointPosA() = listOf(
        Vector2(160f, 382f),
        Vector2(219f, 440f),
        Vector2(292f, 486f),
        Vector2(338f, 556f),
        Vector2(384f, 618f),
        Vector2(413f, 686f),
        Vector2(459f, 754f),
        Vector2(517f, 822f),
        Vector2(575f, 895f),
        Vector2(637f, 941f),
        Vector2(696f, 1017f),
        Vector2(765f, 1085f),
        Vector2(694f, 1144f),
        Vector2(694f, 1232f),
        Vector2(614f, 1255f),
        Vector2(579f, 1323f),
        Vector2(614f, 1391f),
        Vector2(563f, 1446f),
        Vector2(517f, 1523f),
        Vector2(517f, 1581f),
    )

    private fun getListPointPosB() = listOf(
        Vector2(1080f - 46 - 160f, 382f),
        Vector2(1080f - 46 - 219f, 440f),
        Vector2(1080f - 46 - 292f, 486f),
        Vector2(1080f - 46 - 338f, 556f),
        Vector2(1080f - 46 - 384f, 618f),
        Vector2(1080f - 46 - 413f, 686f),
        Vector2(1080f - 46 - 459f, 754f),
        Vector2(1080f - 46 - 517f, 822f),
        Vector2(1080f - 46 - 575f, 895f),
        Vector2(1080f - 46 - 637f, 941f),
        Vector2(1080f - 46 - 696f, 1017f),
        Vector2(1080f - 46 - 765f, 1085f),
        Vector2(1080f - 46 - 694f, 1144f),
        Vector2(1080f - 46 - 694f, 1232f),
        Vector2(1080f - 46 - 614f, 1255f),
        Vector2(1080f - 46 - 579f, 1323f),
        Vector2(1080f - 46 - 614f, 1391f),
        Vector2(1080f - 46 - 563f, 1446f),
        Vector2(1080f - 46 - 517f, 1523f),
        Vector2(1080f - 46 - 517f, 1581f),
    )

    inner class GameUIHandler() {
        var pointCount = 0

        fun setButtonAction(block: Block) {
            blockState = block
        }

        //fun setButtonTexture(texture: TextureRegion) {}

        fun showShakeElementA() {
            btnState.enable()
            btnState.drawable = TextureRegionDrawable(gdxGame.assetsAll.SHAKE)
            aEggPanelA.animShow(0.3f)
        }

        fun shake(block: Block) {
            btnState.disable()
            btnState.animHide(0.2f)
            aEggPanelA.shakeEgg {
                pointCount = it
                block()
            }
        }

        fun moveA(block: Block) {
            aEggPanelA.animHide(0.3f)
            btnState.drawable = TextureRegionDrawable(gdxGame.assetsAll.MOVING)
            btnState.animShow(0.3f)

            pointCount = if (pointCount > pointCountA) pointCountA else pointCount
            log("pointCount = $pointCount")

            imgA.addAction(
                Actions.sequence(
                    Actions.repeat(pointCount, Actions.sequence(
                        Actions.run {
                            // Оновлюємо значення тут
                            val targetW = imgA.width - personageScaleCoff
                            val targetH = imgA.height - personageScaleCoff

                            val targetPointIndex = listPointA.size - pointCountA
                            pointCountA -= 1

                            // Створюємо тимчасовий екшен зміни розміру і додаємо його актору
                            // Це спрацює, бо цей код виконується ПІД ЧАС анімації
                            imgA.addAction(Actions.sequence(
                                Actions.run { gdxGame.soundUtil.apply { play(game_step) } },
                                Actions.parallel(
                                    Actions.sizeTo(targetW, targetH, 0.5f, Interpolation.swingIn),
                                    Actions.moveTo(listPointA[targetPointIndex].x - (targetW / 2f) + 23f, listPointA[targetPointIndex].y, 0.5f)
                                )
                            ))

                        },
                        Actions.delay(0.5f)
                    )),
                    Actions.delay(0.7f),
                    Actions.run {
                        if (pointCountA <= 0) {
                            log("FINISH")
                            GDX_IS_WIN = true
                            animHide { gdxGame.navigationManager.navigate(ResultScreen::class.java.name) }
                        }

                        block()
                    }
                )
            )
        }

        fun showShakeElementB() {
            btnState.drawable = TextureRegionDrawable(gdxGame.assetsAll.AI_TURN)
            aEggPanelB.animShow(0.3f)
        }

        fun shakeAI(block: Block) {
            aEggPanelB.shakeEgg {
                pointCount = it
                block()
            }
        }

        fun moveB(block: Block) {
            aEggPanelB.animHide(0.3f)
            btnState.drawable = TextureRegionDrawable(gdxGame.assetsAll.MOVING)
            btnState.animShow(0.3f)

            pointCount = if (pointCount > pointCountB) pointCountB else pointCount
            log("pointCount = $pointCount")

            imgB.addAction(
                Actions.sequence(
                    Actions.repeat(pointCount, Actions.sequence(
                        Actions.run {
                            // Оновлюємо значення тут
                            val targetW = imgB.width - personageScaleCoff
                            val targetH = imgB.height - personageScaleCoff

                            val targetPointIndex = listPointB.size - pointCountB
                            pointCountB -= 1

                            // Створюємо тимчасовий екшен зміни розміру і додаємо його актору
                            // Це спрацює, бо цей код виконується ПІД ЧАС анімації
                            imgB.addAction(Actions.sequence(
                                Actions.run { gdxGame.soundUtil.apply { play(game_step) } },
                                Actions.parallel(
                                    Actions.sizeTo(targetW, targetH, 0.5f, Interpolation.swingIn),
                                    Actions.moveTo(listPointB[targetPointIndex].x - (targetW / 2f) + 23f, listPointB[targetPointIndex].y, 0.5f)
                                )
                            ))
                        },
                        Actions.delay(0.5f)
                    )),
                    Actions.delay(0.7f),
                    Actions.run {
                        if (pointCountB <= 0) {
                            log("FINISH")
                            GDX_IS_WIN = false
                            animHide { gdxGame.navigationManager.navigate(ResultScreen::class.java.name) }
                        }

                        block()
                    }
                )
            )
        }


    }

}