package com.samartachokitse.endelgase.game.actors

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.samartachokitse.endelgase.game.utils.advanced.AdvancedGroup
import com.samartachokitse.endelgase.game.utils.advanced.AdvancedScreen

open class AButton(
    override val screen: AdvancedScreen,
    type: Static.Type? = null
) : AdvancedGroup() {

    private val defaultImage  by lazy {  if (type != null) Image(getStyleByType(type).default)  else Image() }
    private val pressedImage  by lazy { (if (type != null) Image(getStyleByType(type).pressed)  else Image()).apply { isVisible = false } }
    private val disabledImage by lazy { (if (type != null) Image(getStyleByType(type).disabled) else Image()).apply { isVisible = false } }

    private var onClickBlock    : () -> Unit = { }

    var touchDownBlock   : AButton.(x: Float, y: Float) -> Unit = { _, _ -> }
    var touchDraggedBlock: AButton.(x: Float, y: Float) -> Unit = { _, _ -> }
    var touchUpBlock     : AButton.(x: Float, y: Float) -> Unit = { _, _ -> }

    private var area: Actor? = null


    override fun addActorsOnGroup() {
        addAndFillActors(getActors())
        addListener(getListener())
    }


    private fun getActors() = listOf<Actor>(
        defaultImage,
        pressedImage,
        disabledImage,
    )



    private fun getListener() = object : InputListener() {
        var isWithin     = false
        var isWithinArea = false

        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
            touchDownBlock(x, y)
            touchDragged(event, x, y, pointer)

            screen.game.soundUtil.apply { play(click, 1f) }

            return true
        }

        override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
            touchDraggedBlock(x, y)

            isWithin = x in 0f..width && y in 0f..height
            area?.let { isWithinArea = x in 0f..it.width && y in 0f..it.height }


            if (isWithin || isWithinArea) press() else unpress()

        }

        override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
            touchUpBlock(x, y)

            if (isWithin || isWithinArea) {
                unpress()
                onClickBlock()
            }

        }
    }

    fun press() {
        defaultImage.isVisible = false
        pressedImage.isVisible = true
    }

    fun unpress() {
        defaultImage.isVisible = true
        pressedImage.isVisible = false
    }

    fun disable(useDisabledStyle: Boolean = true) {
        touchable = Touchable.disabled

        if (useDisabledStyle) {
            defaultImage.isVisible  = false
            pressedImage.isVisible  = false
            disabledImage.isVisible = true
        }

    }

    fun enable() {
        touchable = Touchable.enabled

        defaultImage.isVisible  = true
        pressedImage.isVisible  = false
        disabledImage.isVisible = false

    }

    fun pressAndDisable(useDisabledStyle: Boolean = false) {
        press()
        disable(useDisabledStyle)
    }

    fun unpressAndEnable() {
        unpress()
        enable()
    }

    fun setStyle(style: Static.AButtonStyle) {
        defaultImage.drawable  = style.default
        pressedImage.drawable  = style.pressed
        disabledImage.drawable = style.disabled
    }

    fun setOnClickListener(block: () -> Unit) {
        onClickBlock = block
    }

    fun addArea(actor: Actor) {
        area = actor
        actor.addListener(getListener())
    }

    private fun getStyleByType(type: Static.Type) = when(type) {
        Static.Type.Menu -> Static.AButtonStyle(
            default  = TextureRegionDrawable(screen.game.all.menu_def),
            pressed  = TextureRegionDrawable(screen.game.all.menu_press),
            disabled = TextureRegionDrawable(screen.game.all.menu_press),
        )
        Static.Type.Exit -> Static.AButtonStyle(
            default  = TextureRegionDrawable(screen.game.all.exit_def),
            pressed  = TextureRegionDrawable(screen.game.all.exit_press),
            disabled = TextureRegionDrawable(screen.game.all.exit_press),
        )
        Static.Type.Start -> Static.AButtonStyle(
            default  = TextureRegionDrawable(screen.game.all.start_def),
            pressed  = TextureRegionDrawable(screen.game.all.start_press),
            disabled = TextureRegionDrawable(screen.game.all.start_press),
        )
        Static.Type.Sett -> Static.AButtonStyle(
            default  = TextureRegionDrawable(screen.game.all.sett_def),
            pressed  = TextureRegionDrawable(screen.game.all.sett_press),
            disabled = TextureRegionDrawable(screen.game.all.sett_press),
        )
        Static.Type.Info -> Static.AButtonStyle(
            default  = TextureRegionDrawable(screen.game.all.info_def),
            pressed  = TextureRegionDrawable(screen.game.all.info_press),
            disabled = TextureRegionDrawable(screen.game.all.info_press),
        )
        Static.Type.Agree -> Static.AButtonStyle(
            default  = TextureRegionDrawable(screen.game.all.iag_def),
            pressed  = TextureRegionDrawable(screen.game.all.iag_press),
            disabled = TextureRegionDrawable(screen.game.all.iag_press),
        )
    }

    // ---------------------------------------------------
    // Style
    // ---------------------------------------------------

    object Static {
        data class AButtonStyle(
            val default: Drawable,
            val pressed: Drawable,
            val disabled: Drawable? = null,
        )

        enum class Type {
            Exit, Menu, Start, Sett, Info, Agree
        }
    }

}