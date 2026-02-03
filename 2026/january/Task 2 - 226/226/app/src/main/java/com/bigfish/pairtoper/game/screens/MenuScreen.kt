package com.bigfish.pairtoper.game.screens

import com.bigfish.pairtoper.game.actors.main.AMainMenu
import com.bigfish.pairtoper.game.utils.Block
import com.bigfish.pairtoper.game.utils.advanced.AdvancedMainScreen
import com.bigfish.pairtoper.game.utils.advanced.AdvancedStage
import com.bigfish.pairtoper.game.utils.gdxGame
import com.bigfish.pairtoper.game.utils.region

class MenuScreen: AdvancedMainScreen() {

    override val aMain = AMainMenu(this)

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