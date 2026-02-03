package com.gorillaz.puzzlegame.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.gorillaz.puzzlegame.game.actors.ABackground
import com.gorillaz.puzzlegame.game.actors.main.AMainMenu
import com.gorillaz.puzzlegame.game.actors.main.AMainWheel_of_Fortune
import com.gorillaz.puzzlegame.game.utils.Block
import com.gorillaz.puzzlegame.game.utils.HEIGHT_UI
import com.gorillaz.puzzlegame.game.utils.TIME_ANIM_SCREEN
import com.gorillaz.puzzlegame.game.utils.WIDTH_UI
import com.gorillaz.puzzlegame.game.utils.actor.disable
import com.gorillaz.puzzlegame.game.utils.actor.setBoundsScaled
import com.gorillaz.puzzlegame.game.utils.actor.setOnClickListener
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedMainScreen
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedStage
import com.gorillaz.puzzlegame.game.utils.gdxGame
import com.gorillaz.puzzlegame.util.log

class Wheel_of_FortuneScreen: AdvancedMainScreen() {

    private val aBackground      = ABackground(this, gdxGame.currentBackground)
    private val imgGorilla       = Image(gdxGame.assetsLoader.gorilla)

    override val aMain = AMainWheel_of_Fortune(this)

    override fun AdvancedStage.addActorsOnStageBack() {
        addBackground()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMain()
    }

    override fun AdvancedStage.addActorsOnStageTopBack() {
        addImgGorilla()
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

    // Actors UI------------------------------------------------------------------------

    override fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }

    // Actors Top Back------------------------------------------------------------------------

    private fun AdvancedStage.addImgGorilla() {
        addActor(imgGorilla)
        imgGorilla.setBoundsScaled(sizeScalerScreen, 0f, 0f, 800f, 839f)
        imgGorilla.disable()

        val ny = sizeScalerScreen.scaled(25f)

        imgGorilla.addAction(Actions.forever(Actions.sequence(
            Actions.moveBy(0f, -ny, 0.45f, Interpolation.sineIn),
            Actions.moveBy(0f, ny, 0.45f, Interpolation.sineOut),
        )))
    }

}