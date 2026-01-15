package com.farm.puzzletiles.game.screens

import com.farm.puzzletiles.game.actors.main.AMainResultDone
import com.farm.puzzletiles.game.utils.Block
import com.farm.puzzletiles.game.utils.advanced.AdvancedMainScreen
import com.farm.puzzletiles.game.utils.advanced.AdvancedStage
import com.farm.puzzletiles.game.utils.gdxGame
import com.farm.puzzletiles.game.utils.region

class ResultDoneScreen: AdvancedMainScreen() {

    override val aMain = AMainResultDone(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsAll.BACKGROUND_WIN.region)
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