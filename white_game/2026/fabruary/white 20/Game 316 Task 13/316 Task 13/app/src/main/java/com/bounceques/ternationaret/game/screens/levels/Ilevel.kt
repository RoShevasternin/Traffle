package com.bounceques.ternationaret.game.screens.levels

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.bounceques.ternationaret.game.LibGDXGame
import com.bounceques.ternationaret.game.actors.button.AButton
import com.bounceques.ternationaret.game.box2d.AbstractBody
import com.bounceques.ternationaret.game.box2d.BodyId
import com.bounceques.ternationaret.game.box2d.WorldUtil
import com.bounceques.ternationaret.game.box2d.bodies.BBall
import com.bounceques.ternationaret.game.box2d.bodiesGroup.BGBorders
import com.bounceques.ternationaret.game.screens.LoseScreen
import com.bounceques.ternationaret.game.screens.PinkMenuScreen
import com.bounceques.ternationaret.game.screens.WinScreen
import com.bounceques.ternationaret.game.utils.HEIGHT_BOX2D
import com.bounceques.ternationaret.game.utils.TIME_ANIM
import com.bounceques.ternationaret.game.utils.WIDTH_BOX2D
import com.bounceques.ternationaret.game.utils.actor.animHide
import com.bounceques.ternationaret.game.utils.actor.setOnClickListener
import com.bounceques.ternationaret.game.utils.advanced.AdvancedBox2dScreen
import com.bounceques.ternationaret.game.utils.advanced.AdvancedStage
import com.bounceques.ternationaret.game.utils.font.FontParameter
import com.bounceques.ternationaret.game.utils.runGDX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

abstract class Ilevel(final override val game: LibGDXGame): AdvancedBox2dScreen(WorldUtil()) {

    private val fontP = FontParameter().setCharacters(FontParameter.CharType.NUMBERS).setSize(64)
    private val font  = fontGenerator_KameronBold.generateFont(fontP)

    private val homeBtn  by lazy { AButton(this, AButton.Static.Type.BACK) }
    private val panelImg    = Image(game.assetsAll.TIMER_PAN)
    private val countLbl    = Label("", Label.LabelStyle(font, Color.valueOf("9910EC")))

    // BodyGroup
    private val bgBorders by lazy { BGBorders(this) }

    // Body
    abstract val bBall: BBall

    protected val flowCountStar = MutableStateFlow(0)

    final override fun AdvancedStage.addActorsOnStageUI() {
        addHomeBtn()
        addPanelImg()
        addCountLbl()
        addJoystickImg()

        createBG_Borders()

        addActorsOnStage()

        bBall.beginContactBlockArray.add(AbstractBody.ContactBlock {
            when(it.id) {
                BodyId.BORDERS -> game.soundUtil.apply { play(BORDER) }
                BodyId.COIN -> {
                    game.soundUtil.apply { play(STAR) }
                    runGDX {
                        it.body?.setTransform(WIDTH_BOX2D*2, HEIGHT_BOX2D*2, 0f)
                        flowCountStar.value++

                        if (flowCountStar.value == 2) {
                            stageUI.root.animHide(TIME_ANIM) { game.navigationManager.navigate(WinScreen::class.java.name) }
                        }
                    }
                }
                BodyId.ENEMY -> {
                    runGDX {
                        flowCountStar.value = 0
                        stageUI.root.animHide(TIME_ANIM) { game.navigationManager.navigate(LoseScreen::class.java.name) }
                    }
                }
            }
        })
    }

    abstract fun AdvancedStage.addActorsOnStage()



    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun AdvancedStage.addHomeBtn() {
        addActor(homeBtn)
        homeBtn.apply {
            setBounds(70f, 1736f, 156f, 165f)
            setOnClickListener { stageUI.root.animHide(TIME_ANIM) { game.navigationManager.back() } }
        }
    }

    private fun AdvancedStage.addPanelImg() {
        addActor(panelImg)
        panelImg.apply {
            setBounds(358f, 1712f, 363f, 157f)
        }
    }

    private fun AdvancedStage.addCountLbl() {
        addActor(countLbl)
        countLbl.apply {
            setBounds(503f, 1755f, 75f, 72f)
            setAlignment(Align.center)
        }

        coroutine?.launch {
            flowCountStar.collect { count ->
                runGDX { countLbl.setText(count) }
            }
        }
    }

    private fun AdvancedStage.addJoystickImg() {
        val left  = AButton(this@Ilevel, AButton.Static.Type.LEFT)
        val right = AButton(this@Ilevel, AButton.Static.Type.RIGHT)
        val up    = AButton(this@Ilevel, AButton.Static.Type.UP)
        addActors(left, right, up)
        left.apply {
            setBounds(70f, 70f, 156f, 164f)
            setOnClickListener {
                bBall.body?.apply {
                    applyLinearImpulse(Vector2(-4f, 0f), worldCenter, true)
                }
            }
        }
        right.apply {
            setBounds(256f, 70f, 156f, 164f)
            setOnClickListener {
                bBall.body?.apply {
                    applyLinearImpulse(Vector2(4f, 0f), worldCenter, true)
                }
            }
        }
        up.apply {
            setBounds(884f, 70f, 156f, 164f)
            setOnClickListener {
                bBall.body?.apply {
                    applyLinearImpulse(Vector2(0f, 7f), worldCenter, true)
                }
            }
        }

    }

    // ---------------------------------------------------
    // create BodyGroup
    // ---------------------------------------------------

    private fun createBG_Borders() {
        bgBorders.create(0f, 0f, WIDTH, HEIGHT)
    }


}