package com.viade.bepuzzle.game.actors.main

import com.viade.bepuzzle.game.actors.ACircles
import com.viade.bepuzzle.game.actors.ASelectCard
import com.viade.bepuzzle.game.actors.ASelectLVLofDiff
import com.viade.bepuzzle.game.screens.GameScreen
import com.viade.bepuzzle.game.screens.MenuScreen
import com.viade.bepuzzle.game.screens.SelectLevelScreen
import com.viade.bepuzzle.game.utils.Block
import com.viade.bepuzzle.game.utils.TIME_ANIM_SCREEN
import com.viade.bepuzzle.game.utils.actor.animHideSuspend
import com.viade.bepuzzle.game.utils.actor.animShowSuspend
import com.viade.bepuzzle.game.utils.advanced.AdvancedGroup
import com.viade.bepuzzle.game.utils.advanced.AdvancedScreen
import com.viade.bepuzzle.game.utils.dataStore.DataStoreGalleryStarsUtil
import com.viade.bepuzzle.game.utils.runGDX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AMainSelectLevel(override val screen: AdvancedScreen): AdvancedGroup() {

    private val aCircles = ACircles(screen)

    private val aSelectCard      = ASelectCard(screen)
    private val aSelectLVLofDiff = ASelectLVLofDiff(screen)

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                addAndFillActor(aCircles)
                addASelectCard()
                addASelectLVLofDiff()

                children.onEach { it.color.a = 0f }
            }

            delay(100)
            animShowMain()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addASelectCard() {
        addActor(aSelectCard)
        aSelectCard.setBounds(18f, 37f, 968f, 1711f)

        aSelectCard.blockPlay = {
            SelectLevelScreen.LEVEL_INDEX = it
            screen.hideScreen {
                screen.game.navigationManager.navigate(GameScreen::class.java.name, screen::class.java.name,)
            }
        }
    }

    private fun addASelectLVLofDiff() {
        addActor(aSelectLVLofDiff)
        aSelectLVLofDiff.setBounds(699f, 242f, 402f, 840f)

        aSelectLVLofDiff.blockCheck = { SelectLevelScreen.DIFFICULTY_INDEX = it }
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