package com.portalend.fruitomaner.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.portalend.fruitomaner.game.LibGDXGame
import com.portalend.fruitomaner.game.actors.AButton
import com.portalend.fruitomaner.game.utils.*
import com.portalend.fruitomaner.game.utils.actor.*
import com.portalend.fruitomaner.game.utils.advanced.AdvancedScreen
import com.portalend.fruitomaner.game.utils.advanced.AdvancedStage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class RulesScreen(override val game: LibGDXGame) : AdvancedScreen() {

    // Actor
    private val btnMenu  = AButton(this, AButton.Static.Type.Back)
    private val imgPanel = Image(game.all.RULES)
    private val imgLeft  = Image(game.all.ic_left)
    private val imgAple  = Image(game.all.ic_apels)

    override fun show() {
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
            launch { btnMenu.animByNY(TIME_ANIM, 246f, 76f) }
            delay(TIME_ANIM.toMS / 2)
            launch { imgLeft.animShowSuspend(TIME_ANIM) }
            launch { imgLeft.animByNY(TIME_ANIM, 74f, 1091f) }
            launch { imgAple.animShowSuspend(TIME_ANIM) }
            launch { imgAple.animByNY(TIME_ANIM, 517f, 413f) }

        }
    }

    private fun AdvancedStage.addMenu() {
        addActors(btnMenu)
        btnMenu.apply {
            color.a = 0f
            setBounds(246f, -169f, 376f, 169f)
            setOrigin(Align.center)
            setOnClickListener {
                animHideScreen { game.navigationManager.back() }
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