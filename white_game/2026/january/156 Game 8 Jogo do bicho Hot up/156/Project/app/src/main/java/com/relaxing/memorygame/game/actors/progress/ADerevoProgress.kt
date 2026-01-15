package com.relaxing.memorygame.game.actors.progress

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Image
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import com.relaxing.memorygame.game.actors.masks.Mask
import com.relaxing.memorygame.game.utils.advanced.AdvancedGroup
import com.relaxing.memorygame.game.utils.advanced.AdvancedScreen
import com.relaxing.memorygame.game.utils.runGDX

class ADerevoProgress(override val screen: AdvancedScreen): AdvancedGroup() {

    private val LENGTH = 311f

    private val backgroundImage = Image(screen.game.gameAssets.ARM_PANEL)
    private val progressImage   = Image(screen.game.gameAssets.ARM)

    private val onePercentX = LENGTH / 100f

    // 0 .. 100 %
    val progressPercentFlow = MutableStateFlow(0f)


    override fun addActorsOnGroup() {
        addAndFillActor(backgroundImage)
        addProgressImg()

        coroutine?.launch {
            progressPercentFlow.collect { percent ->
                runGDX { progressImage.x = percent * onePercentX }
            }
        }

        addListener(inputListener())
    }

    // ---------------------------------------------------
    // Add Actors
    // ---------------------------------------------------

    private fun AdvancedGroup.addProgressImg() {
        addActor(progressImage)
        progressImage.setBounds(0f, 0f, 74f, 35f)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun inputListener() = object : InputListener() {
        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
            touchDragged(event, x, y, pointer)
            return true
        }

        override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
            progressPercentFlow.value = when {
                x <= 0 -> 0f
                x >= LENGTH -> 100f
                else -> x / onePercentX
            }
        }
    }

    fun setProgressPercent(percent: Float) {
        progressPercentFlow.value = percent
    }


}