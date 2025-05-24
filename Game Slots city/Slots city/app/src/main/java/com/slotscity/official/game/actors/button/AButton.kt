package com.slotscity.official.game.actors.button

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.slotscity.official.game.utils.TextureEmpty
import com.slotscity.official.game.utils.advanced.AdvancedGroup
import com.slotscity.official.game.utils.advanced.AdvancedScreen
import com.slotscity.official.game.utils.region

open class AButton(
    override val screen: AdvancedScreen,
    type: Static.IType? = null
) : AdvancedGroup() {

    private val defaultImage  by lazy {  if (type != null) Image(getStyleByType(type).default)  else Image() }
    private val pressedImage  by lazy { (if (type != null) Image(getStyleByType(type).pressed)  else Image()).apply { isVisible = false } }
    private val disabledImage by lazy { (if (type != null) Image(getStyleByType(type).disabled) else Image()).apply { isVisible = false } }

    private var onClickBlock    : () -> Unit = { }

    var touchDownBlock   : AButton.(x: Float, y: Float) -> Unit = { x, y -> }
    var touchDraggedBlock: AButton.(x: Float, y: Float) -> Unit = { x, y -> }
    var touchUpBlock     : AButton.(x: Float, y: Float) -> Unit = { x, y -> }

    private var area: Actor? = null

    private val spriteUtil                by lazy { screen.game.allAssets }
    private val spriteUtil_CarnavalCat    by lazy { screen.game.carnavalCatAssets }
    private val spriteUtil_TreasureSnipes by lazy { screen.game.treasureSnipesAssets }


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
            screen.game.soundUtil.apply { play(sound ?: click) }
            touchDownBlock(x, y)
            touchDragged(event, x, y, pointer)

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
        defaultImage.drawable  = TextureRegionDrawable(style.default)
        pressedImage.drawable  = TextureRegionDrawable(style.pressed)
        disabledImage.drawable = TextureRegionDrawable(style.disabled)
    }


    private var sound: Sound? = null

    fun setOnClickListener(sound: Sound? = null, block: () -> Unit) {
        onClickBlock = block
        this.sound = sound
    }

    fun addArea(actor: Actor) {
        area = actor
        actor.addListener(getListener())
    }

    private fun getStyleByType(type: Static.IType) = when(type) {
        Static.Type.PLUS -> Static.AButtonStyle(
            default  = spriteUtil.plus_def,
            pressed  = spriteUtil.plus_press,
            disabled = spriteUtil.plus_press,
        )
        Static.Type.MINUS -> Static.AButtonStyle(
            default  = spriteUtil.minus_dis,
            pressed  = spriteUtil.minus_press,
            disabled = spriteUtil.minus_press,
        )
        Static.Type.BACK -> Static.AButtonStyle(
            default  = spriteUtil.menu_def,
            pressed  = spriteUtil.menu_press,
            disabled = spriteUtil.menu_press,
        )
        Static.Type.INFO -> Static.AButtonStyle(
            default  = spriteUtil.info_deff,
            pressed  = spriteUtil.info_press,
            disabled = spriteUtil.info_press,
        )
        Static.Type.SPIN -> Static.AButtonStyle(
            default  = spriteUtil.spin_def,
            pressed  = spriteUtil.spin_press,
            disabled = spriteUtil.spin_press,
        )
        Static.Type.PLAY -> Static.AButtonStyle(
            default  = spriteUtil.play_deff,
            pressed  = spriteUtil.play_press,
            disabled = spriteUtil.play_press,
        )
        Static.CarnavalCatType.SPIN -> Static.AButtonStyle(
            default  = spriteUtil_CarnavalCat.spin_def,
            pressed  = spriteUtil_CarnavalCat.spin_dis,
            disabled = spriteUtil_CarnavalCat.spin_dis,
        )
        Static.CarnavalCatType.AUTO_SPIN -> Static.AButtonStyle(
            default  = spriteUtil_CarnavalCat.autospin_def,
            pressed  = spriteUtil_CarnavalCat.autospin_press,
            disabled = spriteUtil_CarnavalCat.autospin_dis,
        )
        Static.TreasureSnipesType.SPIN -> Static.AButtonStyle(
            default  = spriteUtil_TreasureSnipes.spin_def,
            pressed  = spriteUtil_TreasureSnipes.spin_dis,
            disabled = spriteUtil_TreasureSnipes.spin_dis,
        )

        else -> Static.AButtonStyle(
            default  = TextureEmpty.region,
            pressed  = TextureEmpty.region,
            disabled = TextureEmpty.region,
        )
    }

    // ---------------------------------------------------
    // Style
    // ---------------------------------------------------

    object Static {
        data class AButtonStyle(
            val default: TextureRegion,
            val pressed: TextureRegion,
            val disabled: TextureRegion? = null,
        )

        interface IType

        enum class Type : IType {
            PLUS, MINUS, BACK, INFO, SPIN, PLAY
        }

        enum class CarnavalCatType : IType {
            SPIN, AUTO_SPIN
        }

        enum class TreasureSnipesType : IType {
            SPIN
        }
    }

}