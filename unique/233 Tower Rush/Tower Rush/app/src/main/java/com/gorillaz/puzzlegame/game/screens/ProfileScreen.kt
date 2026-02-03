package com.gorillaz.puzzlegame.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.gorillaz.puzzlegame.game.actors.ABackground
import com.gorillaz.puzzlegame.game.actors.main.AMainMenu
import com.gorillaz.puzzlegame.game.actors.main.AMainProfile
import com.gorillaz.puzzlegame.game.actors.panel.APanelAchievement
import com.gorillaz.puzzlegame.game.utils.Acts
import com.gorillaz.puzzlegame.game.utils.Block
import com.gorillaz.puzzlegame.game.utils.HEIGHT_UI
import com.gorillaz.puzzlegame.game.utils.TIME_ANIM_SCREEN
import com.gorillaz.puzzlegame.game.utils.WIDTH_UI
import com.gorillaz.puzzlegame.game.utils.actor.setBoundsScaled
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedMainScreen
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedStage
import com.gorillaz.puzzlegame.game.utils.gdxGame

class ProfileScreen: AdvancedMainScreen() {

    private val aBackground       = ABackground(this, gdxGame.currentBackground)
    private val aPanelAchievement = APanelAchievement(this)

    override val aMain = AMainProfile(this)

    override fun AdvancedStage.addActorsOnStageBack() {
        addBackground()
        addAPanelAchievement()
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

        aBackground.animToNewTexture(gdxGame.assetsLoader.BACKGROUND_0, TIME_ANIM_SCREEN)
        gdxGame.currentBackground = gdxGame.assetsLoader.BACKGROUND_0
    }

    private fun AdvancedStage.addAPanelAchievement() {
        addActor(aPanelAchievement)
        aPanelAchievement.setBoundsScaled(sizeScalerScreen, 20f, -730f, 1039f, 728f)
    }

    // Actors UI------------------------------------------------------------------------

    override fun AdvancedStage.addMain() {
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