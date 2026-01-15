package com.fontantwo.tennisfortwo.game.screens

import com.fontantwo.tennisfortwo.game.GDXGame
import com.fontantwo.tennisfortwo.game.actors.AFountainPanel
import com.fontantwo.tennisfortwo.game.actors.panel.APanelSelect
import com.fontantwo.tennisfortwo.game.utils.*
import com.fontantwo.tennisfortwo.game.utils.actor.animHidePanelSuspend
import com.fontantwo.tennisfortwo.game.utils.actor.animShowPanelSuspend
import com.fontantwo.tennisfortwo.game.utils.actor.setOnClickListener
import com.fontantwo.tennisfortwo.game.utils.advanced.AdvancedScreen
import com.fontantwo.tennisfortwo.game.utils.advanced.AdvancedStage
import kotlinx.coroutines.launch

class SelectScreen(override val game: GDXGame): AdvancedScreen() {

    private val panelSelect   = APanelSelect(this).apply { color.a = 0f }
    private val panelFountain = AFountainPanel(this)

    override fun show() {
        setBackBackground(game.assetsLoader.MAIN.region)
        super.show()
    }

    override fun hideScreen(block: Block) {
        coroutine?.launch {
            runGDX { panelFountain.animHideParticles() }
            animHidePanelSuspend(panelSelect) { block.invoke() }
        }
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addPanel()
                addAndFillActor(panelFountain)
            }
            animShowPanelSuspend(panelSelect)
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun AdvancedStage.addPanel() {
        addAndFillActor(panelSelect)
        panelSelect.x = -WIDTH_UI

        panelSelect.apply {
            backBtn.setOnClickListener { hideScreen { screen.game.navigationManager.back() } }
            goImg.setOnClickListener(game.soundUtil) { hideScreen { screen.game.navigationManager.navigate(GameScreen::class.java.name, SelectScreen::class.java.name) } }
        }
    }

}