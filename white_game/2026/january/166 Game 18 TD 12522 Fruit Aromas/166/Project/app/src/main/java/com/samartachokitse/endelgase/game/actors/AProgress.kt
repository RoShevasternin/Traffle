package com.samartachokitse.endelgase.game.actors

import com.samartachokitse.endelgase.game.utils.HEIGHT_UI
import com.samartachokitse.endelgase.game.utils.advanced.AdvancedGroup
import com.samartachokitse.endelgase.game.utils.advanced.AdvancedScreen
import com.samartachokitse.endelgase.game.utils.runGDX
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Image
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AProgress(override val screen: AdvancedScreen): AdvancedGroup() {

    private val LENGTH = 675f

    private val assets = screen.game.all

    private val progressImage = Image(assets.prg)
    private val frmImage      = Image(assets.frm)
    private val cursorImage   = Image(assets.cursor)
    private val mask          = Mask(screen, assets.MSK, alphaHeight = HEIGHT_UI.toInt())

    private val onePercentY = LENGTH / 100f

    // 0 .. 100 %
    val progressPercentFlow = MutableStateFlow(0f)


    override fun addActorsOnGroup() {
        addFrame()
        addMask()
        addCursor()

        coroutine?.launch {
            progressPercentFlow.collect { percent ->
                runGDX {
                    progressImage.y = percent * onePercentY - LENGTH
                    cursorImage.y = percent * onePercentY - 80f
                }
            }
        }

        addListener(inputListener())
    }

    // ---------------------------------------------------
    // Add Actors
    // ---------------------------------------------------

    private fun AdvancedGroup.addFrame() {
        addActor(frmImage)
        frmImage.setBounds(18f, 0f, 144f, 683f)
    }

    private fun AdvancedGroup.addMask() {
        addActor(mask)
        mask.setBounds(22f, 4f, 136f, 675f)
        mask.addAndFillActor(progressImage)
    }

    private fun AdvancedGroup.addCursor() {
        addActor(cursorImage)
        cursorImage.setBounds(0f, 0f, 181f, 167f)
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