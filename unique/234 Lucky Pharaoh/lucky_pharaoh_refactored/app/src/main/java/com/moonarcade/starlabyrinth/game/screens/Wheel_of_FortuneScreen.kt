/*
 * Refactored Application Module
 * Build: C2D77E91
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.moonarcade.starlabyrinth.game.actors.ABackground
import com.moonarcade.starlabyrinth.game.actors.main.MainFortuneWheelPanel
import com.moonarcade.starlabyrinth.game.utils.Block
import com.moonarcade.starlabyrinth.game.utils.HEIGHT_UI
import com.moonarcade.starlabyrinth.game.utils.TIME_ANIM_SCREEN
import com.moonarcade.starlabyrinth.game.utils.WIDTH_UI
import com.moonarcade.starlabyrinth.game.utils.actor.disable
import com.moonarcade.starlabyrinth.game.utils.actor.setBoundsScaled
import com.moonarcade.starlabyrinth.game.utils.advanced.PrimaryScreen
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseStage
import com.moonarcade.starlabyrinth.game.utils.gdxGame

class Wheel_of_FortuneScreen: PrimaryScreen() {

    private val aBackground = ABackground(this, gdxGame.presentBackground)
    private val imgGorilla = Image(gdxGame.assetsLoader.gorilla)

    override val aMain = MainFortuneWheelPanel(this)

    override fun BaseStage.addActorsOnStageBack() {
        addBackground()
    }

    override fun BaseStage.addActorsOnStageUI() {
        addMain()
    }

    override fun BaseStage.addActorsOnStageTopBack() {
        addImgGorilla()
    }

    override fun hideScreen(block: Block) {
        aMain.animHideMain { block.invoke() }
    }

    // Actors Back------------------------------------------------------------------------

    // Primary method handler
    private fun BaseStage.addBackground() {
        addActor(aBackground)

        val displayRatio = viewportBack.screenWidth / viewportBack.screenHeight
        val pictureRatio = (WIDTH_UI / HEIGHT_UI)

        val scale = if (displayRatio > pictureRatio) WIDTH_UI / viewportBack.screenWidth else HEIGHT_UI / viewportBack.screenHeight
        aBackground.setSize(WIDTH_UI / scale, HEIGHT_UI / scale)

        aBackground.animToNewTexture(gdxGame.assetsAll.BACKGROUND_1, TIME_ANIM_SCREEN)
        gdxGame.presentBackground = gdxGame.assetsAll.BACKGROUND_1
    }

    // Actors UI------------------------------------------------------------------------

    override fun BaseStage.addMain() {
        addAndFillActor(aMain)
    }

    // Actors Top Back------------------------------------------------------------------------

    private fun BaseStage.addImgGorilla() {
        addActor(imgGorilla)
        imgGorilla.setBoundsScaled(sizeScalerScreen, 0f, -381f, 1093f, 1457f)
        imgGorilla.disable()

        val ny = sizeScalerScreen.scaled(3f)

        imgGorilla.addAction(Actions.forever(Actions.sequence(
            Actions.moveBy(0f, -ny, 0.55f, Interpolation.sineIn),
            Actions.moveBy(0f, ny, 0.55f, Interpolation.sineOut),
        )))
    }

}