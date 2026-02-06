package com.fruithaven.juicydashx.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.fruithaven.juicydashx.game.actors.ABackground
import com.fruithaven.juicydashx.game.actors.main.AMainWheel_of_Fortune
import com.fruithaven.juicydashx.game.utils.Block
import com.fruithaven.juicydashx.game.utils.HEIGHT_UI
import com.fruithaven.juicydashx.game.utils.TIME_ANIM_SCREEN
import com.fruithaven.juicydashx.game.utils.WIDTH_UI
import com.fruithaven.juicydashx.game.utils.actor.disable
import com.fruithaven.juicydashx.game.utils.actor.setBoundsScaled
import com.fruithaven.juicydashx.game.utils.advanced.AdvancedMainScreen
import com.fruithaven.juicydashx.game.utils.advanced.AdvancedStage
import com.fruithaven.juicydashx.game.utils.gdxGame

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

        aBackground.animToNewTexture(gdxGame.assetsAll.BACKGROUND_1, TIME_ANIM_SCREEN)
        gdxGame.currentBackground = gdxGame.assetsAll.BACKGROUND_1
    }

    // Actors UI------------------------------------------------------------------------

    override fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }

    // Actors Top Back------------------------------------------------------------------------

    private fun AdvancedStage.addImgGorilla() {
        addActor(imgGorilla)
        imgGorilla.setBoundsScaled(sizeScalerScreen, 72f, -283f, 1122f, 1122f)
        imgGorilla.disable()

        val ny = sizeScalerScreen.scaled(3f)

        imgGorilla.addAction(Actions.forever(Actions.sequence(
            Actions.moveBy(0f, -ny, 0.55f, Interpolation.sineIn),
            Actions.moveBy(0f, ny, 0.55f, Interpolation.sineOut),
        )))
    }

}