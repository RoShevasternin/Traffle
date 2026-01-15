package com.toyscatcher.factoryatoy.advanced

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.utils.Disposable

open class AdvancedActor : Actor(), Disposable {

    private var onClickBlock: () -> Unit = { }



    override fun dispose() {}



    fun setOnClickListener(sound: Sound? = null, block: () -> Unit) {
        onClickBlock = block
        addListener(getListener())
    }



    private fun Actor.getListener(sound: Sound? = null) = object : InputListener() {
        var isWithin = false

        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
            //sound?.play(SOUND_VOLUME)
            touchDragged(event, x, y, pointer)
            return true
        }

        override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
            isWithin = x in 0f..width && y in 0f..height
        }

        override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
            if (isWithin) {
                isWithin = false
                onClickBlock()
            }
        }
    }
}