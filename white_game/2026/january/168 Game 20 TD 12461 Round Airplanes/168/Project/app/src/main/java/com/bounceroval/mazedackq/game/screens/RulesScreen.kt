package com.bounceroval.mazedackq.game.screens

import com.badlogic.gdx.math.Interpolation
import com.bounceroval.mazedackq.game.LibGDXGame
import com.bounceroval.mazedackq.game.utils.actor.animHide
import com.bounceroval.mazedackq.game.utils.advanced.AdvancedScreen
import com.bounceroval.mazedackq.game.utils.advanced.AdvancedStage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.bounceroval.mazedackq.game.actors.AButton
import com.bounceroval.mazedackq.game.utils.*
import com.bounceroval.mazedackq.game.utils.actor.animShowSuspend
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RulesScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val tRules = Image(game.all.t_rules)
    private val panel  = Image(game.all.rules_pan)
    private val text   = Image(game.all.text_rules).apply {
        color.a = 0f
        setSize(644f, 1679f)
    }
    private val scroll = ScrollPane(text)
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
            text.animShowSuspend(0.5f)
        }
    }

    private fun AdvancedStage.addBtns() {
        addActors(tRules,panel,scroll,back)
        tRules.apply { setBounds(298f, HEIGHT_UI, 467f, 174f) }
        panel.apply { setBounds(126f, -1232f, 811f, 1232f) }
        scroll.apply { setBounds(209f, 520f, 644f, 934f) }
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
        tRules.addAction(Actions.moveTo(298f, 1622f, time, Interpolation.swingOut))
        panel.addAction(Actions.moveTo(126f, 329f, time, Interpolation.swingOut))
        back.addAction(Actions.moveTo(313f, 82f, time, Interpolation.swingOut))
    }

    private fun animHideScreen(block: () -> Unit) {
        val time = 0.5f
        text.animHide(time) {
            tRules.addAction(Actions.moveTo(298f, HEIGHT_UI, time, Interpolation.swingIn))
            panel.addAction(Actions.moveTo(126f, -1232f, time, Interpolation.swingIn))

            back.addAction(Actions.sequence(
                Actions.moveTo(313f, -201f, time, Interpolation.swingIn),
                Actions.run { block() }
            ))
        }
    }

}