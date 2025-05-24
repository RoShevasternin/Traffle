package com.viade.bepuzzle.game.screens

import com.viade.bepuzzle.game.GDXGame
import com.viade.bepuzzle.game.actors.APanelTop
import com.viade.bepuzzle.game.actors.main.AMainInApp
import com.viade.bepuzzle.game.utils.Block
import com.viade.bepuzzle.game.utils.advanced.AdvancedScreen
import com.viade.bepuzzle.game.utils.advanced.AdvancedStage
import com.viade.bepuzzle.game.utils.region
import com.viade.bepuzzle.game.utils.runGDX
import kotlinx.coroutines.launch

class InAppScreen(override val game: GDXGame): AdvancedScreen() {

    private val aPanelTop  = APanelTop(this)
    private val aMainInApp = AMainInApp(this)

    override fun show() {
        setBackBackground(game.assetsLoader.background.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageBack() {
        addPanelTop()

    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMainInApp()
    }

    override fun hideScreen(block: Block) {
        coroutine?.launch {
            aMainInApp.animHideMain {
                runGDX { block.invoke() }
            }
        }
    }

    // Actors Back------------------------------------------------------------------------

    private fun AdvancedStage.addPanelTop() {
        addActor(aPanelTop)
        aPanelTop.isBtnBack  = true
        aPanelTop.isPlusCoin = false

        val w = sizeScaler_Ui_Back.scaled(1080f)
        val h = sizeScaler_Ui_Back.scaled(228f)
        val x = (viewportBack.worldWidth / 2) - (w / 2)
        val y = (viewportBack.worldHeight - h)
        aPanelTop.setBounds(x, y, w, h)
    }

    // Actors UI------------------------------------------------------------------------

    private fun AdvancedStage.addMainInApp() {
        addAndFillActor(aMainInApp)
    }

}