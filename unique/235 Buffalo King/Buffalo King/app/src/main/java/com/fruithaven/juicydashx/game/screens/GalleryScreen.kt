package com.fruithaven.juicydashx.game.screens

import com.fruithaven.juicydashx.game.actors.ABackground
import com.fruithaven.juicydashx.game.actors.main.AMainGallery
import com.fruithaven.juicydashx.game.utils.*
import com.fruithaven.juicydashx.game.utils.advanced.AdvancedMainScreen
import com.fruithaven.juicydashx.game.utils.advanced.AdvancedStage

class GalleryScreen: AdvancedMainScreen() {

    private val aBackground = ABackground(this, gdxGame.currentBackground)

    override val aMain = AMainGallery(this)

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

        aBackground.animToNewTexture(gdxGame.assetsAll.BACKGROUND_1, TIME_ANIM_SCREEN)
        gdxGame.currentBackground = gdxGame.assetsAll.BACKGROUND_1
    }

    // Actors UI------------------------------------------------------------------------

    override fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }

}