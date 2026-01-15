package com.sugaraxplosion.candysmoy.game.actors

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.sugaraxplosion.candysmoy.game.utils.advanced.AdvancedGroup
import com.sugaraxplosion.candysmoy.game.utils.advanced.AdvancedScreen

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
        Static.Type.Sweet -> Static.AButtonStyle(
            default  = TextureRegionDrawable(screen.game.all.sweet_def),
            pressed  = TextureRegionDrawable(screen.game.all.sweet_prs),
            disabled = TextureRegionDrawable(screen.game.all.sweet_prs),
        )
        Static.Type.Exit -> Static.AButtonStyle(
            default  = TextureRegionDrawable(screen.game.all.x_def),
            pressed  = TextureRegionDrawable(screen.game.all.x_prrs),
            disabled = TextureRegionDrawable(screen.game.all.x_prrs),
        )
        Static.Type.Star -> Static.AButtonStyle(
            default  = TextureRegionDrawable(screen.game.all.star_def),
            pressed  = TextureRegionDrawable(screen.game.all.star_prs),
            disabled = TextureRegionDrawable(screen.game.all.star_prs),
        )
        Static.Type.Oclic -> Static.AButtonStyle(
            default  = TextureRegionDrawable(screen.game.all.oclik_def),
            pressed  = TextureRegionDrawable(screen.game.all.oclik_prs),
            disabled = TextureRegionDrawable(screen.game.all.oclik_prs),
        )
        Static.Type.Plus -> Static.AButtonStyle(
            default  = TextureRegionDrawable(screen.game.all.plus_def),
            pressed  = TextureRegionDrawable(screen.game.all.plus_prs),
            disabled = TextureRegionDrawable(screen.game.all.plus_prs),
        )
        Static.Type.Pitanie -> Static.AButtonStyle(
            default  = TextureRegionDrawable(screen.game.all.pitann_def),
            pressed  = TextureRegionDrawable(screen.game.all.pitan_prs),
            disabled = TextureRegionDrawable(screen.game.all.pitan_prs),
        )
        Static.Type.Agree -> Static.AButtonStyle(
            default  = TextureRegionDrawable(screen.game.all.d),
            pressed  = TextureRegionDrawable(screen.game.all.p),
            disabled = TextureRegionDrawable(screen.game.all.p),
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
            Sweet, Star, Oclic, Plus, Exit, Pitanie, Agree
        }
    }

}