package com.colderonetrains.battlesskates.game.screens

import com.colderonetrains.battlesskates.game.actors.ABackground
import com.colderonetrains.battlesskates.game.actors.main.AMainBattleSetup
import com.colderonetrains.battlesskates.game.actors.main.AMainHome
import com.colderonetrains.battlesskates.game.actors.main.AMainRandom
import com.colderonetrains.battlesskates.game.actors.main.AMainTrickCatalog
import com.colderonetrains.battlesskates.game.utils.Block
import com.colderonetrains.battlesskates.game.utils.TIME_ANIM_SCREEN
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedMainScreen
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedStage
import com.colderonetrains.battlesskates.game.utils.gdxGame

class BattleSetupScreen: AdvancedMainScreen() {
    companion object {
        val GLOBAL_listPlayerName   = mutableListOf<String>()
        val GLOBAL_listCustomTrick  = mutableListOf<String>()
        var GLOBAL_isUseCustomTrick = false
    }

    private val aBackground = ABackground(this, gdxGame.currentBackground)
    override val aMain      = AMainBattleSetup(this)

    override fun AdvancedStage.addActorsOnStageBack() {
        GLOBAL_listPlayerName.clear()
        GLOBAL_listCustomTrick.clear()
        GLOBAL_isUseCustomTrick = false

        addAndFillActor(aBackground)
        aBackground.animToNewTexture(gdxGame.assetsAll.BACKGROUND_GAME, TIME_ANIM_SCREEN)
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