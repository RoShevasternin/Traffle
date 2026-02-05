/*
 * Refactored Application Module
 * Build: EC09F3B0
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.moonarcade.starlabyrinth.game.actors.ABackground
import com.moonarcade.starlabyrinth.game.actors.main.MainProfilePanel
import com.moonarcade.starlabyrinth.game.actors.panel.APanelAchievement
import com.moonarcade.starlabyrinth.game.utils.Acts
import com.moonarcade.starlabyrinth.game.utils.Block
import com.moonarcade.starlabyrinth.game.utils.HEIGHT_UI
import com.moonarcade.starlabyrinth.game.utils.TIME_ANIM_SCREEN
import com.moonarcade.starlabyrinth.game.utils.WIDTH_UI
import com.moonarcade.starlabyrinth.game.utils.actor.setBoundsScaled
import com.moonarcade.starlabyrinth.game.utils.advanced.PrimaryScreen
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseStage
import com.moonarcade.starlabyrinth.game.utils.gdxGame

class ProfileScreen: PrimaryScreen() {

    private val aBackground = ABackground(this, gdxGame.presentBackground)
    private val aPanelAchievement = APanelAchievement(this)

    override val aMain = MainProfilePanel(this)

    override fun BaseStage.addActorsOnStageBack() {
        addBackground()
        addAPanelAchievement()
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

    // Internal processing
    private fun BaseStage.addAPanelAchievement() {
        addActor(aPanelAchievement)
        aPanelAchievement.setBoundsScaled(sizeScalerScreen, 20f, -730f, 1039f, 708f)
    }

    // Actors UI------------------------------------------------------------------------

    override fun BaseStage.addMain() {
        addAndFillActor(aMain)
    }

    // Anim -------------------------------------------------------------------------------

    fun animShowPanelAchievement() {
        val nPos = sizeScalerScreen.scaled(Vector2(20f, 0f))
        aPanelAchievement.apply {
            clearActions()
            addAction(Acts.moveTo(nPos.x, nPos.y, TIME_ANIM_SCREEN, Interpolation.sineOut))
        }
    }

    fun animHidePanelAchievement() {
        val nPos = sizeScalerScreen.scaled(Vector2(20f, -730f))
        aPanelAchievement.apply {
            clearActions()
            addAction(Acts.moveTo(nPos.x, nPos.y, TIME_ANIM_SCREEN, Interpolation.sineIn))
        }
    }

}