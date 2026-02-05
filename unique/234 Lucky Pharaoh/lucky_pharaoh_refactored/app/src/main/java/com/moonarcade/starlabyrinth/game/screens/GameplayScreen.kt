/*
 * Refactored Application Module
 * Build: 5A267D20
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.screens

import com.moonarcade.starlabyrinth.game.actors.ABackground
import com.moonarcade.starlabyrinth.game.actors.main.MainGameInterface
import com.moonarcade.starlabyrinth.game.utils.*
import com.moonarcade.starlabyrinth.game.utils.advanced.PrimaryScreen
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseStage

class GameplayScreen: PrimaryScreen() {

    val aBackground = ABackground(this, gdxGame.presentBackground)

    override val aMain = MainGameInterface(this)

    override fun BaseStage.addActorsOnStageBack() {
        addBackground()
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
        aBackground.x = -(aBackground.width - viewportBack.screenWidth) / 2f

        //aBackground.animToNewTexture(gdxGame.assetsLoader.BACKGROUND_0, TIME_ANIM_SCREEN)
        //gdxGame.currentBackground = gdxGame.assetsLoader.BACKGROUND_0
    }

    // Actors UI------------------------------------------------------------------------

    override fun BaseStage.addMain() {
        addAndFillActor(aMain)
    }

}