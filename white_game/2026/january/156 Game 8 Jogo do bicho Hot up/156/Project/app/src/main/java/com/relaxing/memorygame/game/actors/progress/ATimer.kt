package com.relaxing.memorygame.game.actors.progress

import com.badlogic.gdx.scenes.scene2d.ui.Image
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import com.relaxing.memorygame.game.actors.masks.Mask
import com.relaxing.memorygame.game.utils.advanced.AdvancedGroup
import com.relaxing.memorygame.game.utils.advanced.AdvancedScreen
import com.relaxing.memorygame.game.utils.runGDX
import com.relaxing.memorygame.util.log
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

class ATimer(override val screen: AdvancedScreen): AdvancedGroup() {

    private val LENGTH = 369f

    private val backgroundImage = Image(screen.game.gameAssets.TIME_BACKGROUND)
    private val progressImage   = Image(screen.game.gameAssets.TIME_LOADER)
    private val mask            = Mask(screen, screen.game.gameAssets.TIME_MASK, alphaWidth = 1000)
    private val icon = Image(screen.game.gameAssets.TIME)

    private val onePercentX = LENGTH / 100f

    // 0 .. 100 %
    private val progressPercentFlow = MutableStateFlow(100f)

    var isPause = false

    override fun addActorsOnGroup() {
        addAndFillActor(backgroundImage)
        addMask()
        addIcon()

        coroutine?.launch {
            progressPercentFlow.collect { percent ->
                runGDX { progressImage.x = percent * onePercentX - LENGTH }
            }
        }

    }

    // ---------------------------------------------------
    // Add Actors
    // ---------------------------------------------------

    private fun AdvancedGroup.addMask() {
        addActor(mask)
        mask.setBounds(54f, 33f, 369f, 38f)
        mask.addProgressImg()
    }

    private fun AdvancedGroup.addProgressImg() {
        addActor(progressImage)
        progressImage.setBounds(0f, 0f, 369f, 38f)
    }

    private fun AdvancedGroup.addIcon() {
        addActor(icon)
        icon.setBounds(-34f, -7f, 115f, 113f)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    fun startTimer(finishBlock: () -> Unit) {
        coroutine?.launch {
            var time = 100f
            while (isActive && time > 0) {
                if (isPause.not()) {
                    delay(1000)
                    progressPercentFlow.value = --time
                    log("t = $time")
                }
            }
            cancel()
            finishBlock()
        }
    }


}