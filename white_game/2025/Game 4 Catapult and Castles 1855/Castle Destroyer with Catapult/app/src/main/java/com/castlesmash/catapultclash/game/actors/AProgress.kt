package com.castlesmash.catapultclash.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.castlesmash.catapultclash.game.utils.advanced.AdvancedGroup
import com.castlesmash.catapultclash.game.utils.advanced.AdvancedScreen
import com.castlesmash.catapultclash.game.utils.runGDX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AProgress(override val screen: AdvancedScreen): AdvancedGroup() {

    private val LENGTH = 519f

    private val progressImage = Image(screen.game.allAssets.stone)

    private val onePercentX = LENGTH / 100f

    // 0 .. 100 %
    val progressPercentFlow = MutableStateFlow(0f)


    override fun addActorsOnGroup() {
        addProgress()

        coroutine?.launch {
            progressPercentFlow.collect { percent ->
                runGDX { progressImage.x = percent * onePercentX }
            }
        }

//        addListener(inputListener())
    }

    // ---------------------------------------------------
    // Add Actors
    // ---------------------------------------------------

    private fun AdvancedGroup.addProgress() {
        addActor(progressImage)
        progressImage.setBounds(0f, 0f, 31f, 31f)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    fun setProgressPercent(percent: Float) {
        progressPercentFlow.value = percent
    }


}