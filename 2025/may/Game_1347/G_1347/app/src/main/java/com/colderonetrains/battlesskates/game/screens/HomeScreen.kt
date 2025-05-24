package com.colderonetrains.battlesskates.game.screens

import com.colderonetrains.battlesskates.game.actors.ABackground
import com.colderonetrains.battlesskates.game.actors.main.AMainHome
import com.colderonetrains.battlesskates.game.utils.Block
import com.colderonetrains.battlesskates.game.utils.TIME_ANIM_SCREEN
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedMainScreen
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedStage
import com.colderonetrains.battlesskates.game.utils.gdxGame

class HomeScreen: AdvancedMainScreen() {

    private val aBackground = ABackground(this, gdxGame.currentBackground)
    override val aMain      = AMainHome(this)

    override fun AdvancedStage.addActorsOnStageBack() {
        addAndFillActor(aBackground)
        aBackground.animToNewTexture(gdxGame.assetsAll.BACKGROUND_HOME, TIME_ANIM_SCREEN)
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