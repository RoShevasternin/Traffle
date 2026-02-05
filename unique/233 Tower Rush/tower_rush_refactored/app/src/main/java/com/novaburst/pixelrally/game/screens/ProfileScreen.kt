/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.novaburst.pixelrally.game.actors.ABackground
import com.novaburst.pixelrally.game.actors.main.AMainProfile
import com.novaburst.pixelrally.game.actors.panel.APanelAchievement
import com.novaburst.pixelrally.game.utils.Acts
import com.novaburst.pixelrally.game.utils.Block
import com.novaburst.pixelrally.game.utils.HEIGHT_UI
import com.novaburst.pixelrally.game.utils.TIME_ANIM_SCREEN
import com.novaburst.pixelrally.game.utils.WIDTH_UI
import com.novaburst.pixelrally.game.utils.actor.setBoundsScaled
import com.novaburst.pixelrally.game.utils.advanced.MainDisplay
import com.novaburst.pixelrally.game.utils.advanced.RenderStage
import com.novaburst.pixelrally.game.utils.gdxGame

class ProfileScreen: MainDisplay() {

    private val aBackground = ABackground(this, gdxGame.currentBackground)
    private val aPanelAchievement = APanelAchievement(this)

    override val aMain = AMainProfile(this)

    override fun RenderStage.addActorsOnStageBack() {
        addBackground()
        addAPanelAchievement()
    }

    override fun RenderStage.addActorsOnStageUI() {
        addMain()
    }

    // Processing logic
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

    private fun RenderStage.addAPanelAchievement() {
        addActor(aPanelAchievement)
        aPanelAchievement.setBoundsScaled(sizeScalerScreen, 20f, -730f, 1039f, 708f)
    }

    // Actors UI------------------------------------------------------------------------

    override fun RenderStage.addMain() {
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