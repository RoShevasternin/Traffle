package com.farm.tilerotate.game.screens

import com.farm.tilerotate.game.actors.main.AMainSett
import com.farm.tilerotate.game.utils.Block
import com.farm.tilerotate.game.utils.advanced.AdvancedMainScreen
import com.farm.tilerotate.game.utils.advanced.AdvancedStage
import com.farm.tilerotate.game.utils.gdxGame
import com.farm.tilerotate.game.utils.region

class SettScreen: AdvancedMainScreen() {

    override val aMain = AMainSett(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsAll.B_DEF.region)
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