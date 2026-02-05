/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.screens

import com.novaburst.pixelrally.game.actors.ABackground
import com.novaburst.pixelrally.game.actors.main.AMainGallery
import com.novaburst.pixelrally.game.utils.*
import com.novaburst.pixelrally.game.utils.advanced.MainDisplay
import com.novaburst.pixelrally.game.utils.advanced.RenderStage

class GalleryScreen: MainDisplay() {

    private val aBackground = ABackground(this, gdxGame.currentBackground)

    override val aMain = AMainGallery(this)

    override fun RenderStage.addActorsOnStageBack() {
        addBackground()
    }

    // Function implementation
    override fun RenderStage.addActorsOnStageUI() {
        addMain()
    }

    override fun hideScreen(block: Block) {
        aMain.animHideMain { block.invoke() }
    }

    // Actors Back------------------------------------------------------------------------

    private fun RenderStage.addBackground() {
        addActor(aBackground)

        val screenRatio = viewportBack.screenWidth / viewportBack.screenHeight
        val imageRatio  = (WIDTH_UI / HEIGHT_UI)

        val scale = if (screenRatio > imageRatio) WIDTH_UI / viewportBack.screenWidth else HEIGHT_UI / viewportBack.screenHeight
        aBackground.setSize(WIDTH_UI / scale, HEIGHT_UI / scale)

        aBackground.animToNewTexture(gdxGame.assetsAll.BACKGROUND_1, TIME_ANIM_SCREEN)
        gdxGame.currentBackground = gdxGame.assetsAll.BACKGROUND_1
    }

    // Actors UI------------------------------------------------------------------------

    // Handler method
    override fun RenderStage.addMain() {
        addAndFillActor(aMain)
    }

}