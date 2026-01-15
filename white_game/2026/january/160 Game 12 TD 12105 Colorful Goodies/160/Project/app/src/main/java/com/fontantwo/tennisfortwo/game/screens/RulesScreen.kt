package com.fontantwo.tennisfortwo.game.screens

import com.fontantwo.tennisfortwo.game.GDXGame
import com.fontantwo.tennisfortwo.game.actors.AFountainPanel
import com.fontantwo.tennisfortwo.game.actors.panel.APanelRules
import com.fontantwo.tennisfortwo.game.utils.*
import com.fontantwo.tennisfortwo.game.utils.actor.animHidePanelSuspend
import com.fontantwo.tennisfortwo.game.utils.actor.animShowPanelSuspend
import com.fontantwo.tennisfortwo.game.utils.advanced.AdvancedScreen
import com.fontantwo.tennisfortwo.game.utils.advanced.AdvancedStage
import kotlinx.coroutines.launch

class RulesScreen(override val game: GDXGame): AdvancedScreen() {

    private val panelRules    = APanelRules(this).apply { color.a = 0f }
    private val panelFountain = AFountainPanel(this)

    override fun show() {
        setBackBackground(game.assetsLoader.MAIN.region)
        super.show()
    }

    override fun hideScreen(block: Block) {
        coroutine?.launch {
            runGDX { panelFountain.animHideParticles() }
            animHidePanelSuspend(panelRules) { block.invoke() }
        }
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addPanel()
                addAndFillActor(panelFountain)
            }
            animShowPanelSuspend(panelRules)
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun AdvancedStage.addPanel() {
        addAndFillActor(panelRules)
        panelRules.x = -WIDTH_UI

        panelRules.apply {
            backBtn.setOnClickListener { hideScreen { screen.game.navigationManager.back() } }
        }
    }

}