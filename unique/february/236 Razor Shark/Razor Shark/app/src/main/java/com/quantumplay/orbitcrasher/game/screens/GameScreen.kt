package com.quantumplay.orbitcrasher.game.screens

import com.quantumplay.orbitcrasher.game.actors.ABackground
import com.quantumplay.orbitcrasher.game.actors.main.AMainGame
import com.quantumplay.orbitcrasher.game.utils.*
import com.quantumplay.orbitcrasher.game.utils.advanced.AdvancedMainScreen
import com.quantumplay.orbitcrasher.game.utils.advanced.AdvancedStage

class GameScreen: AdvancedMainScreen() {

    val aBackground = ABackground(this, gdxGame.currentBackground)

    override val aMain = AMainGame(this)

    override fun AdvancedStage.addActorsOnStageBack() {
        addBackground()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMain()
    }

    override fun hideScreen(block: Block) {
        aMain.animHideMain { block.invoke() }
    }

    // Actors Back------------------------------------------------------------------------

    private fun AdvancedStage.addBackground() {
        addActor(aBackground)

        val screenRatio = viewportBack.screenWidth / viewportBack.screenHeight
        val imageRatio  = (WIDTH_UI / HEIGHT_UI)

        val scale = if (screenRatio > imageRatio) WIDTH_UI / viewportBack.screenWidth else HEIGHT_UI / viewportBack.screenHeight
        aBackground.setSize(WIDTH_UI / scale, HEIGHT_UI / scale)
        aBackground.x = -(aBackground.width - viewportBack.screenWidth) / 2f

        //aBackground.animToNewTexture(gdxGame.assetsLoader.BACKGROUND_0, TIME_ANIM_SCREEN)
        //gdxGame.currentBackground = gdxGame.assetsLoader.BACKGROUND_0
    }

    // Actors UI------------------------------------------------------------------------

    override fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }

}