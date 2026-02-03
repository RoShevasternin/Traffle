package com.puzdever.puzsweet.game.screens

import com.puzdever.puzsweet.game.actors.main.AMainPuzzle
import com.puzdever.puzsweet.game.utils.Block
import com.puzdever.puzsweet.game.utils.advanced.AdvancedMainScreen
import com.puzdever.puzsweet.game.utils.advanced.AdvancedStage
import com.puzdever.puzsweet.game.utils.gdxGame
import com.puzdever.puzsweet.game.utils.region

class PuzzleScreen: AdvancedMainScreen() {

    override val aMain = AMainPuzzle(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsAll.BACKGROUND_GAME.region)
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