package com.skycoin.flight.game.actors.progress

import com.badlogic.gdx.scenes.scene2d.ui.Image
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import com.skycoin.flight.game.actors.masks.Mask
import com.skycoin.flight.game.utils.advanced.AdvancedGroup
import com.skycoin.flight.game.utils.advanced.AdvancedScreen
import com.skycoin.flight.game.utils.runGDX

class AProgressBar(override val screen: AdvancedScreen): AdvancedGroup() {

    private val LENGTH = 476f

    private val progressImage = Image(screen.game.splashAssets.load)
    private val mask          = Mask(screen, screen.game.splashAssets.masks, alphaWidth = 1000)

    private val onePercentX = LENGTH / 100f

    // 0 .. 100 %
    val progressPercentFlow = MutableStateFlow(0f)


    override fun addActorsOnGroup() {
        addMask()

        coroutine?.launch {
            progressPercentFlow.collect { percent ->
                runGDX {
                    progressImage.x = percent * onePercentX - LENGTH
                }
            }
        }

//        addListener(inputListener())
    }

    // ---------------------------------------------------
    // Add Actors
    // ---------------------------------------------------

    private fun AdvancedGroup.addMask() {
        addAndFillActor(mask)
        mask.addProgress()
    }

    private fun AdvancedGroup.addProgress() {
        addActor(progressImage)
        progressImage.setBounds(-LENGTH, 0f, LENGTH, 55f)
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