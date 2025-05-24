package com.colderonetrains.battlesskates.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.colderonetrains.battlesskates.game.actors.ABackground
import com.colderonetrains.battlesskates.game.actors.AFailed
import com.colderonetrains.battlesskates.game.actors.ALanded
import com.colderonetrains.battlesskates.game.actors.main.AMainGame
import com.colderonetrains.battlesskates.game.utils.Block
import com.colderonetrains.battlesskates.game.utils.TIME_ANIM_SCREEN
import com.colderonetrains.battlesskates.game.utils.actor.disable
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedMainScreen
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedStage
import com.colderonetrains.battlesskates.game.utils.gdxGame

class GameScreen: AdvancedMainScreen() {

    val aLanded   = ALanded(this)
    val aFailed   = AFailed(this)
    val imgShadow = Image(drawerUtil.getTexture(Color.valueOf("000B2A").apply { a = 0.65f }))

    private val aBackground = ABackground(this, gdxGame.currentBackground)
    override val aMain      = AMainGame(this)

    override fun AdvancedStage.addActorsOnStageBack() {
        addAndFillActor(aBackground)
        aBackground.animToNewTexture(gdxGame.assetsAll.BACKGROUND_GAME, TIME_ANIM_SCREEN)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMain()
    }

    override fun AdvancedStage.addActorsOnStageTopBack() {
        addAndFillActor(imgShadow)
        imgShadow.color.a = 0f
    }

    override fun AdvancedStage.addActorsOnStageTopUI() {
        addAndFillActors(aLanded, aFailed)

        aLanded.color.a = 0f
        aLanded.disable()

        aFailed.color.a = 0f
        aFailed.disable()
    }

    override fun hideScreen(block: Block) {
        aMain.animHideMain { block.invoke() }
    }

    // Actors UI------------------------------------------------------------------------

    override fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }
}