package com.bounceroval.mazedackq.game.screens

import com.badlogic.gdx.math.Interpolation
import com.bounceroval.mazedackq.game.LibGDXGame
import com.bounceroval.mazedackq.game.utils.TIME_ANIM
import com.bounceroval.mazedackq.game.utils.actor.animHide
import com.bounceroval.mazedackq.game.utils.actor.setOnClickListener
import com.bounceroval.mazedackq.game.utils.advanced.AdvancedScreen
import com.bounceroval.mazedackq.game.utils.advanced.AdvancedStage
import com.bounceroval.mazedackq.game.utils.region
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.bounceroval.mazedackq.game.utils.WIDTH_UI
import com.bounceroval.mazedackq.game.utils.runGDX
import kotlinx.coroutines.launch

class MenuScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        private var isFirst = true
    }

    private val play     = Image(game.all.play)
    private val settings = Image(game.all.settings)
    private val rules    = Image(game.all.rules)
    private val exit     = Image(game.all.exit)

    override fun show() {
        if (isFirst) {
            isFirst = false
            game.musicUtil.apply { music = weekend.apply {
                isLooping = true
                volumeLevelFlow.value = 26f
            } }
        }

        setBackBackground(game.splash.backgrounds.random().region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addBtns()

                animShowScreen()
            }
        }
    }

    private fun AdvancedStage.addBtns() {
        addActors(play,settings,rules,exit)
        play.apply {
            setBounds(WIDTH_UI, 1309f, 515f, 432f)
            setOnClickListener(game.soundUtil) {
                animHideScreen {
                    game.navigationManager.navigate(PrePlayScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        settings.apply {
            setBounds(-515f, 817f, 515f, 432f)
            setOnClickListener(game.soundUtil) {
                animHideScreen {
                    game.navigationManager.navigate(SettingScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        rules.apply {
            setBounds(WIDTH_UI, 325f, 515f, 432f)
            setOnClickListener(game.soundUtil) {
                animHideScreen {
                    game.navigationManager.navigate(RulesScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        exit.apply {
            setBounds(383f, -94f, 296f, 94f)
            setOnClickListener(game.soundUtil) {
                animHideScreen {
                    stageUI.root.animHide(TIME_ANIM) { game.navigationManager.exit() }
                }
            }
        }
    }

    private fun animShowScreen() {
        val time = 0.5f
        play.addAction(Actions.moveTo(274f, 1309f, time, Interpolation.swingOut))
        settings.addAction(Actions.moveTo(274f, 817f, time, Interpolation.swingOut))
        rules.addAction(Actions.moveTo(274f, 325f, time, Interpolation.swingOut))
        exit.addAction(Actions.moveTo(383f, 148f, time, Interpolation.swingOut))
    }

    private fun animHideScreen(block: () -> Unit) {
        val time = 0.5f
        play.addAction(Actions.moveTo(WIDTH_UI, 1309f, time, Interpolation.swingIn))
        settings.addAction(Actions.moveTo(-515f, 817f, time, Interpolation.swingIn))
        rules.addAction(Actions.moveTo(WIDTH_UI, 325f, time, Interpolation.swingIn))

        exit.addAction(Actions.sequence(
            Actions.moveTo(383f, -94f, time, Interpolation.swingIn),
            Actions.run { block() }
        ))
    }

}