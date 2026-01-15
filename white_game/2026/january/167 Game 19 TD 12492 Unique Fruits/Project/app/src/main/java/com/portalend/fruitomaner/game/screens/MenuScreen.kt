package com.portalend.fruitomaner.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.utils.Align
import com.portalend.fruitomaner.game.LibGDXGame
import com.portalend.fruitomaner.game.actors.AButton
import com.portalend.fruitomaner.game.utils.*
import com.portalend.fruitomaner.game.utils.actor.animHide
import com.portalend.fruitomaner.game.utils.advanced.AdvancedScreen
import com.portalend.fruitomaner.game.utils.advanced.AdvancedStage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MenuScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        private var isFirst = true
    }

    private val btnWorld    = AButton(this, AButton.Static.Type.Map)
    private val btnSettings = AButton(this, AButton.Static.Type.Settings)
    private val btnRules    = AButton(this, AButton.Static.Type.Rules)
    private val btnExit     = AButton(this, AButton.Static.Type.Exit)

    override fun show() {
        if (isFirst) {
            isFirst = false
            game.musicUtil.apply { music = whip_afro.apply {
                isLooping = true
                volumeLevelFlow.value = 23f
            } }
        }

        setBackBackground(game.all.backgrounds.random().region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addBtns()
            }
            launch { btnWorld.animByNY(TIME_ANIM, 179f, 1041f) }
            delay(TIME_ANIM.toMS / 2)
            launch { btnSettings.animByNY(TIME_ANIM, 179f, 715f) }
            delay(TIME_ANIM.toMS / 2)
            launch { btnRules.animByNY(TIME_ANIM, 179f, 389f) }
            delay(TIME_ANIM.toMS / 2)
            launch { btnExit.animByNY(TIME_ANIM, 246f, 157f) }
        }
    }

    private fun AdvancedStage.addBtns() {
        addActors(btnWorld, btnSettings, btnRules, btnExit)
        btnWorld.apply {
            setBounds(-509f, -264f, 509f, 264f)
            setOrigin(Align.center)
            setOnClickListener {
                animHideScreen {
                    game.navigationManager.navigate(MapScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        btnSettings.apply {
            setBounds(-509f, -264f, 509f, 264f)
            setOrigin(Align.center)
            setOnClickListener {
                animHideScreen {
                    game.navigationManager.navigate(SettingScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        btnRules.apply {
            setBounds(-509f, -264f, 509f, 264f)
            setOrigin(Align.center)
            setOnClickListener {
                animHideScreen {
                    game.navigationManager.navigate(RulesScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        btnExit.apply {
            setBounds(-376f, -170f, 376f, 170f)
            setOnClickListener {
                animHideScreen {
                    stageUI.root.animHide(TIME_ANIM) { game.navigationManager.exit() }
                }
            }
        }
    }

    // Anim ------------------------------------------------------------------------

    private suspend fun Actor.animByNY(time: Float, nx: Float, ny: Float) = suspendCoroutine { continuation ->
        runGDX {
            clearActions()
            addAction(Actions.rotateBy(720f, time))
            addAction(Actions.sequence(
                Actions.moveTo(nx, ny, time, Interpolation.sineOut),
                Actions.run { continuation.resume(Unit) }
            ))
        }
    }

    private fun animHideScreen(block: () -> Unit) {
        coroutine?.launch {
            launch { btnWorld.animByNY(TIME_ANIM, -509f, -264f) }
            delay(TIME_ANIM.toMS / 2)
            launch { btnSettings.animByNY(TIME_ANIM, -509f, -264f) }
            delay(TIME_ANIM.toMS / 2)
            launch { btnRules.animByNY(TIME_ANIM, -509f, -264f) }
            delay(TIME_ANIM.toMS / 2)
            launch { btnExit.animByNY(TIME_ANIM, -376f, -170f) }

            delay((TIME_ANIM+0.25f).toMS)
            runGDX { block() }
        }
    }

}