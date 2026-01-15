package com.farm.tilerotate.game.screens

import com.farm.tilerotate.game.actors.main.AMainResultFail
import com.farm.tilerotate.game.actors.main.AMainSelecte
import com.farm.tilerotate.game.utils.Block
import com.farm.tilerotate.game.utils.advanced.AdvancedMainScreen
import com.farm.tilerotate.game.utils.advanced.AdvancedStage
import com.farm.tilerotate.game.utils.gdxGame
import com.farm.tilerotate.game.utils.region

class ResultFailScreen: AdvancedMainScreen() {

    override val aMain = AMainResultFail(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(if (AMainSelecte.INDEX == 0) gdxGame.assetsAll.B_GAME1_LOSE.region else gdxGame.assetsAll.B_GAME2_LOSE.region)
        //addAndFillActor(Image(gdxGame.assetsAll.WIN))
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