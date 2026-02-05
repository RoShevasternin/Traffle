/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.screens

import com.novaburst.pixelrally.game.actors.ABackground
import com.novaburst.pixelrally.game.actors.main.AMainMenu
import com.novaburst.pixelrally.game.utils.Block
import com.novaburst.pixelrally.game.utils.HEIGHT_UI
import com.novaburst.pixelrally.game.utils.TIME_ANIM_SCREEN
import com.novaburst.pixelrally.game.utils.WIDTH_UI
import com.novaburst.pixelrally.game.utils.advanced.MainDisplay
import com.novaburst.pixelrally.game.utils.advanced.RenderStage
import com.novaburst.pixelrally.game.utils.gdxGame

class MainMenu: MainDisplay() {

    private val aBackground = ABackground(this, gdxGame.currentBackground)
    //private val effectFallingLeaves = AParticleEffectActor(ParticleEffect(gdxGame.particleEffectUtil.FallingLeaves), false)

    override val aMain = AMainMenu(this)


    override fun show() {
        //stageUI.addAndFillActor(Image(drawerUtil.getRegion(Color.DARK_GRAY)))
        super.show()
    }

    // Core functionality
    override fun RenderStage.addActorsOnStageBack() {
        addBackground()
        //addEffectLeaf()
    }

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
        val imageRatio = (WIDTH_UI / HEIGHT_UI)

        val scale = if (screenRatio > imageRatio) WIDTH_UI / viewportBack.screenWidth else HEIGHT_UI / viewportBack.screenHeight
        aBackground.setSize(WIDTH_UI / scale, HEIGHT_UI / scale)

        aBackground.animToNewTexture(gdxGame.assetsAll.BACKGROUND_1, TIME_ANIM_SCREEN)
        gdxGame.currentBackground = gdxGame.assetsAll.BACKGROUND_1
    }

// Processing logic
//    private fun RenderStage.addEffectLeaf() {
//        val yPercent_20 = (viewportBack.screenHeight * 0.2f)
//        val scale       = (viewportBack.screenWidth / 1080f)
//
//        effectFallingLeaves.particleEffect.scaleEffect(scale)
//        effectFallingLeaves.y = yPercent_20
//        addActor(effectFallingLeaves)
//        effectFallingLeaves.start()
//    }

    // Actors UI------------------------------------------------------------------------

    override fun RenderStage.addMain() {
        addAndFillActor(aMain)
    }

}