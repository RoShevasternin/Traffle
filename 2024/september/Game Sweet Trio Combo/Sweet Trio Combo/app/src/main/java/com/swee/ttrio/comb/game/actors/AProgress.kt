package com.swee.ttrio.comb.game.actors

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.swee.ttrio.comb.game.utils.WIDTH_UI
import com.swee.ttrio.comb.game.utils.advanced.AdvancedGroup
import com.swee.ttrio.comb.game.utils.advanced.AdvancedScreen
import com.swee.ttrio.comb.game.utils.runGDX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AProgress(override val screen: AdvancedScreen): AdvancedGroup() {

    private val LENGTH = 325f

    private val assets = screen.game.all

    private val progressImage   = Image(assets.PROGRESS)
    private val progImage       = Image(assets.PROG)
    private val mask            = Mask(screen, assets.MASK, alphaWidth = WIDTH_UI.toInt())

    private val onePercentX = LENGTH / 100f

    // 0 .. 100 %
    val progressPercentFlow = MutableStateFlow(0f)


    override fun addActorsOnGroup() {
        addAndFillActor(Image(assets.PROG_BACK))
        addMask()
        addActor(progImage)
        progImage.setBounds(314f,0f,27f,26f)

        coroutine?.launch {
            progressPercentFlow.collect { percent ->
                runGDX {
                    progressImage.x = percent * onePercentX - LENGTH
                    progImage.x = progressImage.x+LENGTH-5f
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
        mask.setBounds(10f,8f,325f,13f)
        mask.addAndFillActor(progressImage)
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