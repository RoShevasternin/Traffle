package com.viade.bepuzzle.game.actors.main

import com.viade.bepuzzle.game.actors.AAword
import com.viade.bepuzzle.game.actors.ACircles
import com.viade.bepuzzle.game.actors.AScrollPane
import com.viade.bepuzzle.game.actors.ATmpGroup
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

class AMainAwords(override val screen: AdvancedScreen): AdvancedGroup() {

    private val listSum = listOf(
        250, 500, 750, 1000, 2000,
        2500, 2750, 3000, 4500, 5000
    )

    private val aCircles  = ACircles(screen)
    private val listAword = List(10) { AAword(screen, it, listSum[it]) }
    private val tmpGroup  = ATmpGroup(screen)

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                addAndFillActor(aCircles)
                addScroll()

                children.onEach { it.color.a = 0f }
            }

            delay(100)
            animShowMain()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addScroll() {
        addActor(tmpGroup)
        tmpGroup.setBounds(45f, 0f, 989f, 1787f)

        var nx = 0f
        var ny = 1584f

        tmpGroup.apply {
            listAword.onEachIndexed { index, aAword ->
                addActor(aAword)
                aAword.setBounds(nx, ny, 475f, 311f)
                nx += (39 + 475)
                if (index.inc() % 2 == 0) {
                    nx = 0f
                    ny -= (60 + 311)
                }
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