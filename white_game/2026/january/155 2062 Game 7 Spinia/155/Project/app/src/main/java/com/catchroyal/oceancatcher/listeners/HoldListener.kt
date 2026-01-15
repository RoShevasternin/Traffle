package com.catchroyal.oceancatcher.listeners

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import kotlinx.coroutines.*

class HoldListener(private val actor: Actor) {

    private var onInsideBlock: () -> Unit = { }
    private var onOutsideBlock: () -> Unit = { }

    private lateinit var coroutineHold: CoroutineScope


    private fun Actor.getListener() = object : InputListener() {
        var isInside = false

        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
            isInside = true
            coroutineHold = CoroutineScope(Dispatchers.Main).apply {
                launch { while (isInside) {
                        delay(10)
                        onInsideBlock()
                    } }
            }
            touchDragged(event, x, y, pointer)
            return true
        }

        override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
            isInside = x in 0f..width && y in 0f..height
        }

        override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
            coroutineHold.cancel()
            onOutsideBlock()
        }
    }



    fun setOnHoldListener(blockInside: () -> Unit, blockOutside: () -> Unit) {
        onInsideBlock = blockInside
        onOutsideBlock = blockOutside
        with(actor) { addListener(getListener()) }
    }

}

fun Actor.toHoldeble() = HoldListener(this)