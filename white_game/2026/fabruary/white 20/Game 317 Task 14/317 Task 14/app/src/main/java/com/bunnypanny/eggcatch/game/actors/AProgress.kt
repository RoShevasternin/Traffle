package com.bunnypanny.eggcatch.game.actors

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.bunnypanny.eggcatch.game.utils.WIDTH_UI
import com.bunnypanny.eggcatch.game.utils.advanced.AdvancedGroup
import com.bunnypanny.eggcatch.game.utils.advanced.AdvancedScreen
import com.bunnypanny.eggcatch.game.utils.gdxGame
import com.bunnypanny.eggcatch.game.utils.runGDX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AProgress(override val screen: AdvancedScreen): AdvancedGroup() {

    private val LENGTH = 407f

    private val progressImage   = Image(gdxGame.assetsAll.PROGRESS)
    private val mask            = AOldMask(screen, gdxGame.assetsAll.MASK, alphaWidth = WIDTH_UI.toInt())
    private val pipImage        = Image(gdxGame.assetsAll.PIP)

    private val onePercentX = LENGTH / 100f

    // 0 .. 100 %
    val progressPercentFlow = MutableStateFlow(0f)


    override fun addActorsOnGroup() {
        addMask()
        addPip()

        coroutine?.launch {
            progressPercentFlow.collect { percent ->
                runGDX {
                    progressImage.x = (percent * onePercentX) - LENGTH
                    pipImage.x = progressImage.x + LENGTH - 10f
                }
            }
        }

        addListener(inputListener())
    }

    // ---------------------------------------------------
    // Add Actors
    // ---------------------------------------------------

    private fun AdvancedGroup.addMask() {
        addActor(mask)
        mask.setBounds(11f, 17f, 407f, 25f)
        mask.addProgress()
    }

    private fun AdvancedGroup.addProgress() {
        addAndFillActor(progressImage)
    }

    private fun AdvancedGroup.addPip() {
        addActor(pipImage)
        pipImage.y = 8f
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

            event?.stop()
        }
    }

}