package com.viade.bepuzzle.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.viade.bepuzzle.game.actors.ACircles
import com.viade.bepuzzle.game.actors.APanelItemGame
import com.viade.bepuzzle.game.actors.APuzzleGroup
import com.viade.bepuzzle.game.screens.MenuScreen
import com.viade.bepuzzle.game.screens.SelectLevelScreen
import com.viade.bepuzzle.game.utils.Block
import com.viade.bepuzzle.game.utils.TIME_ANIM_SCREEN
import com.viade.bepuzzle.game.utils.actor.animHideSuspend
import com.viade.bepuzzle.game.utils.actor.animShowSuspend
import com.viade.bepuzzle.game.utils.advanced.AdvancedGroup
import com.viade.bepuzzle.game.utils.advanced.AdvancedScreen
import com.viade.bepuzzle.game.utils.runGDX
import com.viade.bepuzzle.util.OneTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AMainGame(override val screen: AdvancedScreen): AdvancedGroup() {

    private val texturePuzzle = screen.game.assetsAll.listPhoto[MenuScreen.CATEGORY_INDEX][SelectLevelScreen.LEVEL_INDEX]

    private val aCircles = ACircles(screen)

    private val imgGray      = Image(screen.game.assetsAll.game_gray)
    private val aPanelItem   = APanelItemGame(screen)
    private val aPuzzleGroup = APuzzleGroup(screen, texturePuzzle, APuzzleGroup.Diff.entries[SelectLevelScreen.DIFFICULTY_INDEX])

    // Field
    private val oneTimeWin = OneTime()

    var blockPuzzleCompleted = {}
    var blockK1              = {}

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                addAndFillActor(aCircles)
                addImgGray()
                addPanelItem()
                addPuzzleGroup()

                children.onEach { it.color.a = 0f }
            }

            delay(100)
            animShowMain()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgGray() {
        addActor(imgGray)
        imgGray.setBounds(27f, 264f, 1027f, 1471f)
    }

    private fun addPanelItem() {
        addActor(aPanelItem)
        aPanelItem.setBounds(0f, 0f, 1057f, 253f)

        aPanelItem.apply {
            blockK2 = { aPuzzleGroup.useX2() }
            blockK5 = { aPuzzleGroup.useX5() }
            blockK1 = { this@AMainGame.blockK1() }
        }
    }

    private fun addPuzzleGroup() {
        addActor(aPuzzleGroup)
        aPuzzleGroup.setBounds(160f, 334f, 760f, 1330f)
        aPuzzleGroup.setOrigin(Align.center)
        aPuzzleGroup.scaleBy(0.075f)

        aPuzzleGroup.blockPuzzleCompleted = {
            oneTimeWin.use { blockPuzzleCompleted() }
        }
    }

    // Anim Main ------------------------------------------------

    private suspend fun animShowMain() {
        withContext(Dispatchers.Default) {
            children.onEach {
                it.animShowSuspend(0.4f)
            }
        }
    }

    // Anim ------------------------------------------------

    suspend fun animHideMain(block: Block = Block {  }) {
        withContext(Dispatchers.Default) {
            animHideSuspend(TIME_ANIM_SCREEN)
        }
        block.invoke()
    }

}