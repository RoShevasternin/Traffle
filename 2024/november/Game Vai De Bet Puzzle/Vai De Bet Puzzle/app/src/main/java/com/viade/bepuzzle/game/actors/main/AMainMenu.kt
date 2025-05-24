package com.viade.bepuzzle.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.viade.bepuzzle.game.actors.ACategory
import com.viade.bepuzzle.game.actors.ACircles
import com.viade.bepuzzle.game.screens.MenuScreen
import com.viade.bepuzzle.game.screens.SelectLevelScreen
import com.viade.bepuzzle.game.utils.Block
import com.viade.bepuzzle.game.utils.TIME_ANIM_SCREEN
import com.viade.bepuzzle.game.utils.actor.animHideSuspend
import com.viade.bepuzzle.game.utils.actor.animShowSuspend
import com.viade.bepuzzle.game.utils.advanced.AdvancedGroup
import com.viade.bepuzzle.game.utils.advanced.AdvancedScreen
import com.viade.bepuzzle.game.utils.runGDX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AMainMenu(override val screen: AdvancedScreen): AdvancedGroup() {

    private val aCircles = ACircles(screen)

    private val imgSports = Image(screen.game.assetsAll.sports)
    private val aCategory = ACategory(screen)

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                addAndFillActor(aCircles)
                addImgSports()
                addACategory()

                children.onEach { it.color.a = 0f }
            }

            delay(100)
            animShowMain()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgSports() {
        addActor(imgSports)
        imgSports.setBounds(259f, 1297f, 778f, 382f)
    }

    private fun addACategory() {
        addActor(aCategory)
        aCategory.setBounds(27f, 37f, 1026f, 1273f)

        aCategory.blockPlay = {
            MenuScreen.CATEGORY_INDEX = it
            screen.hideScreen {
                screen.game.navigationManager.navigate(SelectLevelScreen::class.java.name, screen::class.java.name,)
            }
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