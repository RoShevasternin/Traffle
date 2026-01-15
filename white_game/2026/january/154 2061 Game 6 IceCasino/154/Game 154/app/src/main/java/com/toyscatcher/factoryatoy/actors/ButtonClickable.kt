package com.toyscatcher.factoryatoy.actors

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.toyscatcher.factoryatoy.advanced.AdvancedGroup

open class ButtonClickable(
    style: Style? = null
) : AdvancedGroup() {

    private val defaultImage = if (style != null) Image(style.default) else Image()
    private val pressedImage = (if (style != null) Image(style.pressed) else Image()).apply { isVisible = false }

    private var onClickBlock: () -> Unit = { }

    var sound: Sound? = null

    init {
        addListener(getListener())
        addAndFillActors(getActors())
    }

    private fun getActors() = listOf<Actor>(
        defaultImage,
        pressedImage,
    )

    private fun getListener() = object : InputListener() {
        var isWithin = false

        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
            //sound?.play(SOUND_VOLUME)
            touchDragged(event, x, y, pointer)
            return true
        }

        override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
            isWithin = x in 0f..width && y in 0f..height
            if (isWithin) pressed() else unpressed()
        }

        override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
            if (isWithin) {
                unpressed()
                isWithin = false
                onClickBlock()
            }
        }
    }

    fun pressed() {
        defaultImage.isVisible = false
        pressedImage.isVisible = true
    }

    fun unpressed() {
        defaultImage.isVisible = true
        pressedImage.isVisible = false
    }

    fun enable() {
        touchable = Touchable.enabled
    }

    fun disable() {
        touchable = Touchable.disabled
    }

    fun setStyle(style: Style) {
        defaultImage.drawable = TextureRegionDrawable(style.default)
        pressedImage.drawable = TextureRegionDrawable(style.pressed)
    }

    fun setOnClickListener(block: () -> Unit) {
        onClickBlock = block
    }

    data class Style(
        val default: TextureRegion,
        val pressed: TextureRegion,
    )

}