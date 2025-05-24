package com.viade.bepuzzle.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.viade.bepuzzle.game.actors.ACircles
import com.viade.bepuzzle.game.actors.AGallery
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

class AMainGallery(override val screen: AdvancedScreen): AdvancedGroup() {

    private val aCircles = ACircles(screen)

    private val imgEmpty = Image(screen.game.assetsAll.gallery_empty)
    private val aGallery = AGallery(screen)

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                addAndFillActor(aCircles)
                if (screen.game.dataStoreGalleryStarsUtil.starsFlow.value.isEmpty()) {
                    addImgEmpty()
                } else {
                    addAGallery()
                }

                children.onEach { it.color.a = 0f }
            }

            delay(100)
            animShowMain()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgEmpty() {
        addActor(imgEmpty)
        imgEmpty.setBounds(99f, 623f, 883f, 674f)
    }

    private fun addAGallery() {
        addActor(aGallery)
        aGallery.setBounds(101f, -16f, 885f, 1655f)
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