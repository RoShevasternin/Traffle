package com.viade.bepuzzle.game.screens

import com.viade.bepuzzle.game.GDXGame
import com.viade.bepuzzle.game.actors.APanelTop
import com.viade.bepuzzle.game.actors.main.AMainSelectLevel
import com.viade.bepuzzle.game.utils.Block
import com.viade.bepuzzle.game.utils.advanced.AdvancedScreen
import com.viade.bepuzzle.game.utils.advanced.AdvancedStage
import com.viade.bepuzzle.game.utils.region
import com.viade.bepuzzle.game.utils.runGDX
import kotlinx.coroutines.launch

class SelectLevelScreen(override val game: GDXGame): AdvancedScreen() {

    companion object {
        var LEVEL_INDEX      = 0
        var DIFFICULTY_INDEX = 0
    }

    private val aPanelTop        = APanelTop(this)
    private val aMainSelectLevel = AMainSelectLevel(this)

    override fun show() {
        LEVEL_INDEX      = 0
        DIFFICULTY_INDEX = 0
        setBackBackground(game.assetsLoader.background.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageBack() {
        addPanelTop()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMainSelectLevel()
    }

    override fun hideScreen(block: Block) {
        coroutine?.launch {
            aMainSelectLevel.animHideMain {
                runGDX { block.invoke() }
            }
        }
    }

    // Actors Back------------------------------------------------------------------------

    private fun AdvancedStage.addPanelTop() {
        addActor(aPanelTop)
        aPanelTop.isBtnBack = true

        val w = sizeScaler_Ui_Back.scaled(1080f)
        val h = sizeScaler_Ui_Back.scaled(228f)
        val x = (viewportBack.worldWidth / 2) - (w / 2)
        val y = (viewportBack.worldHeight - h)
        aPanelTop.setBounds(x, y, w, h)
    }

    // Actors UI------------------------------------------------------------------------

    private fun AdvancedStage.addMainSelectLevel() {
        addAndFillActor(aMainSelectLevel)
    }

}