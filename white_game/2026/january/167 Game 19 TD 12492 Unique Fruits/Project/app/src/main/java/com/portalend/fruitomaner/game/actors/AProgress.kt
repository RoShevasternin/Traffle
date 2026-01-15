package com.portalend.fruitomaner.game.actors

import com.portalend.fruitomaner.game.utils.HEIGHT_UI
import com.portalend.fruitomaner.game.utils.advanced.AdvancedGroup
import com.portalend.fruitomaner.game.utils.advanced.AdvancedScreen
import com.portalend.fruitomaner.game.utils.runGDX
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Image
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AProgress(override val screen: AdvancedScreen): AdvancedGroup() {

    private val LENGTH = 796f

    private val assets = screen.game.all

    private val progressImage = Image(assets.proger)
    private val mask          = Mask(screen, assets.MASKA, alphaHeight = HEIGHT_UI.toInt())

    private val onePercentY = LENGTH / 100f

    // 0 .. 100 %
    val progressPercentFlow = MutableStateFlow(0f)


    override fun addActorsOnGroup() {
        addAndFillActor(Image(screen.game.all.framer))
        addMask()

        coroutine?.launch {
            progressPercentFlow.collect { percent ->
                runGDX {
                    progressImage.y = percent * onePercentY - LENGTH
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
        mask.setBounds(7f, 7f, 122f, 796f)
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
                y <= 0 -> 0f
                y >= LENGTH -> 100f
                else -> y / onePercentY
            }

            event?.stop()
        }
    }

}