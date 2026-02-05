/*
 * Refactored Application Module
 * Build: 70EC3EBF
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.screens

import com.moonarcade.starlabyrinth.game.actors.ABackground
import com.moonarcade.starlabyrinth.game.actors.main.MainMenuPanel
import com.moonarcade.starlabyrinth.game.utils.Block
import com.moonarcade.starlabyrinth.game.utils.HEIGHT_UI
import com.moonarcade.starlabyrinth.game.utils.TIME_ANIM_SCREEN
import com.moonarcade.starlabyrinth.game.utils.WIDTH_UI
import com.moonarcade.starlabyrinth.game.utils.advanced.PrimaryScreen
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseStage
import com.moonarcade.starlabyrinth.game.utils.gdxGame

class MainMenuScreen: PrimaryScreen() {

    private val aBackground = ABackground(this, gdxGame.presentBackground)
    //private val effectFallingLeaves = AParticleEffectActor(ParticleEffect(gdxGame.particleEffectUtil.FallingLeaves), false)

    override val aMain = MainMenuPanel(this)


    override fun show() {
        //stageUI.addAndFillActor(Image(drawerUtil.getRegion(Color.DARK_GRAY)))
        super.show()
    }

    override fun BaseStage.addActorsOnStageBack() {
        addBackground()
        //addEffectLeaf()
    }

    override fun BaseStage.addActorsOnStageUI() {
        addMain()
    }

    override fun hideScreen(block: Block) {
        aMain.animHideMain { block.invoke() }
    }

    // Actors Back------------------------------------------------------------------------

    private fun BaseStage.addBackground() {
        addActor(aBackground)

        val displayRatio = viewportBack.screenWidth / viewportBack.screenHeight
        val pictureRatio = (WIDTH_UI / HEIGHT_UI)

        val scale = if (displayRatio > pictureRatio) WIDTH_UI / viewportBack.screenWidth else HEIGHT_UI / viewportBack.screenHeight
        aBackground.setSize(WIDTH_UI / scale, HEIGHT_UI / scale)

        aBackground.animToNewTexture(gdxGame.assetsAll.BACKGROUND_1, TIME_ANIM_SCREEN)
        gdxGame.presentBackground = gdxGame.assetsAll.BACKGROUND_1
    }

//    private fun BaseStage.addEffectLeaf() {
//        val yPercent_20 = (viewportBack.screenHeight * 0.2f)
//        val scale       = (viewportBack.screenWidth / 1080f)
//
//        effectFallingLeaves.particleEffect.scaleEffect(scale)
//        effectFallingLeaves.y = yPercent_20
//        addActor(effectFallingLeaves)
//        effectFallingLeaves.start()
//    }

    // Actors UI------------------------------------------------------------------------

    override fun BaseStage.addMain() {
        addAndFillActor(aMain)
    }

}