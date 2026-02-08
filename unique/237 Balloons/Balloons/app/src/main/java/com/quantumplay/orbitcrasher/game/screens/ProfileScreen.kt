package com.quantumplay.orbitcrasher.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.quantumplay.orbitcrasher.game.actors.ABackground
import com.quantumplay.orbitcrasher.game.actors.main.AMainProfile
import com.quantumplay.orbitcrasher.game.actors.panel.APanelAchievement
import com.quantumplay.orbitcrasher.game.utils.Acts
import com.quantumplay.orbitcrasher.game.utils.Block
import com.quantumplay.orbitcrasher.game.utils.HEIGHT_UI
import com.quantumplay.orbitcrasher.game.utils.TIME_ANIM_SCREEN
import com.quantumplay.orbitcrasher.game.utils.WIDTH_UI
import com.quantumplay.orbitcrasher.game.utils.actor.setBoundsScaled
import com.quantumplay.orbitcrasher.game.utils.advanced.AdvancedMainScreen
import com.quantumplay.orbitcrasher.game.utils.advanced.AdvancedStage
import com.quantumplay.orbitcrasher.game.utils.gdxGame

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

        aBackground.animToNewTexture(gdxGame.assetsAll.BACKGROUND_1, TIME_ANIM_SCREEN)
        gdxGame.currentBackground = gdxGame.assetsAll.BACKGROUND_1
    }

    private fun AdvancedStage.addAPanelAchievement() {
        addActor(aPanelAchievement)
        aPanelAchievement.setBoundsScaled(sizeScalerScreen, 20f, -730f, 1039f, 708f)
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