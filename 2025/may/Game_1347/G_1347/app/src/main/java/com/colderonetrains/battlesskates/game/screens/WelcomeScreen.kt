package com.colderonetrains.battlesskates.game.screens

import com.colderonetrains.battlesskates.game.actors.ABackground
import com.colderonetrains.battlesskates.game.actors.main.AMainWelcome
import com.colderonetrains.battlesskates.game.utils.Block
import com.colderonetrains.battlesskates.game.utils.TIME_ANIM_SCREEN
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedMainScreen
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedStage
import com.colderonetrains.battlesskates.game.utils.gdxGame

class WelcomeScreen: AdvancedMainScreen() {

    private val aBackground = ABackground(this, gdxGame.currentBackground)
    override val aMain      = AMainWelcome(this)

    override fun AdvancedStage.addActorsOnStageBack() {
        addAndFillActor(aBackground)
        aBackground.animToNewTexture(gdxGame.assetsAll.BACKGROUND_WELCOME, TIME_ANIM_SCREEN)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMain()
    }

    override fun hideScreen(block: Block) {
        aMain.animHideMain { block.invoke() }
    }

    // Actors UI------------------------------------------------------------------------

    override fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }
}