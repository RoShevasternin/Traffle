package com.bounceroval.mazedackq.game.screens

import com.badlogic.gdx.math.Interpolation
import com.bounceroval.mazedackq.game.LibGDXGame
import com.bounceroval.mazedackq.game.utils.actor.animHide
import com.bounceroval.mazedackq.game.utils.advanced.AdvancedScreen
import com.bounceroval.mazedackq.game.utils.advanced.AdvancedStage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.bounceroval.mazedackq.game.actors.AButton
import com.bounceroval.mazedackq.game.actors.AInfo
import com.bounceroval.mazedackq.game.utils.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class InfoScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val info   = AInfo(this)
    private val scroll = ScrollPane(info)
    private val back   = AButton(this, AButton.Static.Type.Back)

    override fun show() {
        setBackBackground(game.splash.backgrounds.random().region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addBtns()
                animShowScreen()
            }
            delay(300)
        }
    }

    private fun AdvancedStage.addBtns() {
        addActors(scroll,back)
        scroll.apply { setBounds(52f, 1890f, 959f, 1476f) }
        back.apply {
            setBounds(313f, -201f, 436f, 201f)
            setOnClickListener {
                animHideScreen {
                    stageUI.root.animHide(TIME_ANIM) { game.navigationManager.back() }
                }
            }
        }
    }

    private fun animShowScreen() {
        val time = 0.5f
        scroll.addAction(Actions.moveTo(52f, 335f, time, Interpolation.swingOut))
        back.addAction(Actions.moveTo(313f, 82f, time, Interpolation.swingOut))
    }

    private fun animHideScreen(block: () -> Unit) {
        val time = 0.5f
        scroll.addAction(Actions.moveTo(52f, 1890f, time, Interpolation.swingIn))

        back.addAction(Actions.sequence(
            Actions.moveTo(313f, -201f, time, Interpolation.swingIn),
            Actions.run { block() }
        ))
    }

}