package com.puzdever.puzsweet.game.screens

import com.puzdever.puzsweet.game.actors.main.AMainRules
import com.puzdever.puzsweet.game.utils.Block
import com.puzdever.puzsweet.game.utils.advanced.AdvancedMainScreen
import com.puzdever.puzsweet.game.utils.advanced.AdvancedStage
import com.puzdever.puzsweet.game.utils.gdxGame
import com.puzdever.puzsweet.game.utils.region

class RulesScreen: AdvancedMainScreen() {

    override val aMain = AMainRules(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsAll.BACKGROUND_MENU.region)
        addMain()
    }

    override fun hideScreen(block: Block) {
        aMain.animHideMain { block.invoke() }
    }

    // Actors UI ------------------------------------------------------------------------

    override fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }
}