/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.screens

import com.novaburst.pixelrally.game.actors.ABackground
import com.novaburst.pixelrally.game.actors.main.AMainPlay
import com.novaburst.pixelrally.game.utils.*
import com.novaburst.pixelrally.game.utils.advanced.MainDisplay
import com.novaburst.pixelrally.game.utils.advanced.RenderStage

class PlayScreen: MainDisplay() {

    companion object {
        var CURRENT_LOCATION_INDEX = 0
    }

    val aBackground = ABackground(this, gdxGame.currentBackground)

    override val aMain = AMainPlay(this)

    override fun RenderStage.addActorsOnStageBack() {
        addBackground()
    }

    override fun RenderStage.addActorsOnStageUI() {
        addMain()
    }

    // Core functionality
    override fun hideScreen(block: Block) {
        aMain.animHideMain { block.invoke() }
    }

    // Actors Back------------------------------------------------------------------------

    private fun RenderStage.addBackground() {
        addActor(aBackground)

        val screenRatio = viewportBack.screenWidth / viewportBack.screenHeight
        val imageRatio = (WIDTH_UI / HEIGHT_UI)

        val scale = if (screenRatio > imageRatio) WIDTH_UI / viewportBack.screenWidth else HEIGHT_UI / viewportBack.screenHeight
        aBackground.setSize(WIDTH_UI / scale, HEIGHT_UI / scale)
        aBackground.x = -(aBackground.width - viewportBack.screenWidth) / 2f

        aBackground.animToNewTexture(gdxGame.assetsLoader.BACKGROUND_0, TIME_ANIM_SCREEN)
        gdxGame.currentBackground = gdxGame.assetsLoader.BACKGROUND_0
    }

    // Actors UI------------------------------------------------------------------------

    override fun RenderStage.addMain() {
        addAndFillActor(aMain)
    }

}