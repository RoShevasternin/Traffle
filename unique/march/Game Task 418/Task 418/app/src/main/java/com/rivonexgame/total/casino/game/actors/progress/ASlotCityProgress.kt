package com.rivonexgame.total.casino.game.actors.progress

import com.badlogic.gdx.scenes.scene2d.ui.Image
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import com.rivonexgame.total.casino.game.actors.masks.Mask
import com.rivonexgame.total.casino.game.utils.GameColor
import com.rivonexgame.total.casino.game.utils.WIDTH_UI
import com.rivonexgame.total.casino.game.utils.advanced.AdvancedGroup
import com.rivonexgame.total.casino.game.utils.advanced.AdvancedScreen
import com.rivonexgame.total.casino.game.utils.runGDX

class ASlotCityProgress(override val screen: AdvancedScreen): AdvancedGroup() {

    private val LENGTH = 1047f

    private val progressImage   = Image(screen.game.loaderAssets.progress)
    private val mask            = Mask(screen, screen.game.loaderAssets.mask, alphaWidth = WIDTH_UI.toInt())

    private val onePercentX = LENGTH / 100f

    // 0 .. 100 %
    val progressPercentFlow = MutableStateFlow(0f)


    override fun addActorsOnGroup() {
        addMask()

        coroutine?.launch {
            progressPercentFlow.collect { percent ->
                runGDX { progressImage.x = percent * onePercentX - LENGTH }
            }
        }

//        addListener(inputListener())
    }

    // ---------------------------------------------------
    // Add Actors
    // ---------------------------------------------------

    private fun AdvancedGroup.addMask() {
        addAndFillActor(mask)
        mask.addAndFillActor(progressImage)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

//    private fun inputListener() = object : InputListener() {
//        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
//            touchDragged(event, x, y, pointer)
//            return true
//        }
//
//        override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
//            progressPercentFlow.value = when {
//                x <= 0 -> 0f
//                x >= LENGTH -> 100f
//                else -> x / onePercentX
//            }
//        }
//    }

    fun setProgressPercent(percent: Float) {
        progressPercentFlow.value = percent
    }


}