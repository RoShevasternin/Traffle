package com.pixe.lkicko.perlin.game.actors

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.pixe.lkicko.perlin.game.utils.advanced.AdvancedGroup
import com.pixe.lkicko.perlin.game.utils.advanced.AdvancedScreen
import com.pixe.lkicko.perlin.game.utils.runGDX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AProgress(override val screen: AdvancedScreen): AdvancedGroup() {

    private val LENGTH = 857f

    private val assets = screen.game.all

    private val progressImage  = Image(assets.progress_bar)
    private val progImage      = Image(assets.progress)

    private val onePercentX = LENGTH / 100f

    // 0 .. 100 %
    val progressPercentFlow = MutableStateFlow(0f)


    override fun addActorsOnGroup() {
        addActor(progressImage)
        progressImage.setBounds(-3f,80f,922f,40f)
        addActor(progImage)
        progImage.setBounds(0f,46f, 58f, 108f)

        coroutine?.launch {
            progressPercentFlow.collect { percent ->
                runGDX {
                    progImage.x = percent * onePercentX
                }
            }
        }

        addListener(inputListener())
    }

    // Logic ---------------------------------------------------

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