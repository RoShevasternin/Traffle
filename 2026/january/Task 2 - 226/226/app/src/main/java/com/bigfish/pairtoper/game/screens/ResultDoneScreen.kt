package com.bigfish.pairtoper.game.screens

import com.bigfish.pairtoper.game.actors.main.AMainResultDone
import com.bigfish.pairtoper.game.actors.main.AMainSelecte
import com.bigfish.pairtoper.game.utils.Block
import com.bigfish.pairtoper.game.utils.advanced.AdvancedMainScreen
import com.bigfish.pairtoper.game.utils.advanced.AdvancedStage
import com.bigfish.pairtoper.game.utils.gdxGame
import com.bigfish.pairtoper.game.utils.region

class ResultDoneScreen: AdvancedMainScreen() {

    override val aMain = AMainResultDone(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(if (AMainSelecte.INDEX == 0) gdxGame.assetsAll.B_GAME1_WIN.region else gdxGame.assetsAll.B_GAME2_WIN.region)
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