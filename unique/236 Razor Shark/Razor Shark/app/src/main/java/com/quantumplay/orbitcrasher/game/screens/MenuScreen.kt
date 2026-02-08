package com.quantumplay.orbitcrasher.game.screens

import com.quantumplay.orbitcrasher.game.actors.ABackground
import com.quantumplay.orbitcrasher.game.actors.main.AMainMenu
import com.quantumplay.orbitcrasher.game.utils.Block
import com.quantumplay.orbitcrasher.game.utils.HEIGHT_UI
import com.quantumplay.orbitcrasher.game.utils.TIME_ANIM_SCREEN
import com.quantumplay.orbitcrasher.game.utils.WIDTH_UI
import com.quantumplay.orbitcrasher.game.utils.advanced.AdvancedMainScreen
import com.quantumplay.orbitcrasher.game.utils.advanced.AdvancedStage
import com.quantumplay.orbitcrasher.game.utils.gdxGame

class MenuScreen: AdvancedMainScreen() {

    private val aBackground = ABackground(this, gdxGame.currentBackground)
    //private val effectFallingLeaves = AParticleEffectActor(ParticleEffect(gdxGame.particleEffectUtil.FallingLeaves), false)

    override val aMain = AMainMenu(this)


    override fun show() {
        //stageUI.addAndFillActor(Image(drawerUtil.getRegion(Color.DARK_GRAY)))
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageBack() {
        addBackground()
        //addEffectLeaf()
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

//    private fun AdvancedStage.addEffectLeaf() {
//        val yPercent_20 = (viewportBack.screenHeight * 0.2f)
//        val scale       = (viewportBack.screenWidth / 1080f)
//
//        effectFallingLeaves.particleEffect.scaleEffect(scale)
//        effectFallingLeaves.y = yPercent_20
//        addActor(effectFallingLeaves)
//        effectFallingLeaves.start()
//    }

    // Actors UI------------------------------------------------------------------------

    override fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }

}