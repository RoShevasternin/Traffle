package com.portalend.fruitomaner.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.portalend.fruitomaner.game.LibGDXGame
import com.portalend.fruitomaner.game.utils.*
import com.portalend.fruitomaner.game.utils.actor.*
import com.portalend.fruitomaner.game.utils.advanced.AdvancedScreen
import com.portalend.fruitomaner.game.utils.advanced.AdvancedStage
import com.portalend.fruitomaner.game.utils.font.FontParameter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ResultScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS).setSize(79)
    private val font          = fontGenerator_Regular.generateFont(fontParameter)

    // Actor
    private val btnMenu  = Image(game.all.mmap)
    private val imgPanel = Image(game.all.you_collect)
    private val imgLeft  = Image(game.all.ic_left)
    private val imgAple  = Image(game.all.ic_apels)
    private val lblRecord = Label("${GameScreen.record}", Label.LabelStyle(font, Color.valueOf("522D16")))

    override fun show() {
        game.soundUtil.apply {
            game.soundUtil.apply { play(win, 0.5f) }
        }
        setBackBackground(game.all.backgrounds.random().region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addMenu()
                addRules()
                addICS()
            }

            launch { imgPanel.animShowSuspend(TIME_ANIM) }
            launch { imgPanel.animByNY(TIME_ANIM, 21f, 599f) }
            delay(TIME_ANIM.toMS / 2)
            launch { btnMenu.animShowSuspend(TIME_ANIM) }
            launch { btnMenu.animByNY(TIME_ANIM, 43f, 76f) }
            delay(TIME_ANIM.toMS / 2)
            launch { imgLeft.animShowSuspend(TIME_ANIM) }
            launch { imgLeft.animByNY(TIME_ANIM, 74f, 1091f) }
            launch { imgAple.animShowSuspend(TIME_ANIM) }
            launch { imgAple.animByNY(TIME_ANIM, 517f, 413f) }

            launch { lblRecord.animShowSuspend(TIME_ANIM) }


        }
    }

    private fun AdvancedStage.addMenu() {
        addActors(btnMenu)
        btnMenu.apply {
            color.a = 0f
            setBounds(43f, -170f, 805f, 169f)
        }

        val menu = Actor()
        addActor(menu)
        menu.apply {
            setBounds(43f, 76f, 376f, 169f)
            setOnClickListener {
                animHideScreen {
                    game.navigationManager.clearBStack()
                    game.navigationManager.navigate(MenuScreen::class.java.name)
                }
            }
        }
        val mapa = Actor()
        addActor(mapa)
        mapa.apply {
            setBounds(472f, 76f, 376f, 169f)
            setOnClickListener {
                animHideScreen {
                    game.navigationManager.navigate(MapScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
    }

    private fun AdvancedStage.addRules() {
        addActors(imgPanel)
        imgPanel.apply {
            color.a = 0f
            setBounds(21f, -574f, 826f, 574f)
            setOrigin(Align.center)
        }

        addActor(lblRecord)
        lblRecord.apply {
            setBounds(301f, 772f, 265f, 99f)
            setAlignment(Align.center)
            color.a = 0f
        }
    }

    private fun AdvancedStage.addICS() {
        addActors(imgLeft, imgAple)
        imgLeft.apply {
            color.a = 0f
            setBounds(-283f, 1100f, 283f, 283f)
            setOrigin(Align.center)
        }
        imgAple.apply {
            color.a = 0f
            setBounds(870f, 300f, 331f, 331f)
            setOrigin(Align.center)
        }
    }

    // Anim ------------------------------------------------------------------------

    private suspend fun Actor.animByNY(time: Float, nx: Float, ny: Float) = suspendCoroutine { continuation ->
        runGDX {
            addAction(Actions.rotateBy(720f, time))
            addAction(Actions.sequence(
                Actions.moveTo(nx, ny, time, Interpolation.sineOut),
                Actions.run { continuation.resume(Unit) }
            ))
        }
    }

    private fun animHideScreen(block: () -> Unit) {
        coroutine?.launch {
            launch { lblRecord.animHideSuspend(TIME_ANIM) }
            launch { imgAple.animByNY(TIME_ANIM, 870f, 300f) }
            launch { imgLeft.animByNY(TIME_ANIM, -283f, 1100f) }
            delay(TIME_ANIM.toMS / 2)
            launch { btnMenu.animByNY(TIME_ANIM, 246f, -169f) }
            delay(TIME_ANIM.toMS / 2)
            launch { imgPanel.animByNY(TIME_ANIM, 21f, -574f) }

            delay((TIME_ANIM+0.25f).toMS)
            runGDX { block() }
        }
    }

}