package com.viade.bepuzzle.game.actors.button

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.viade.bepuzzle.game.utils.TextureEmpty
import com.viade.bepuzzle.game.utils.actor.animHide
import com.viade.bepuzzle.game.utils.actor.animShow
import com.viade.bepuzzle.game.utils.advanced.AdvancedGroup
import com.viade.bepuzzle.game.utils.advanced.AdvancedScreen
import com.viade.bepuzzle.game.utils.region

open class AButton(
    override val screen: AdvancedScreen,
    type: Type
) : AdvancedGroup() {

    private val defaultImage  = Image(getStyleByType(type).default)
    private val pressedImage  = Image(getStyleByType(type).pressed).also { it.color.a = 0f }
    private val disabledImage = Image(getStyleByType(type).disabled).also { it.color.a = 0f }

    private var onClickBlock: () -> Unit = { }

    var touchDownBlock   : AButton.(x: Float, y: Float) -> Unit = { _, _ -> }
    var touchDraggedBlock: AButton.(x: Float, y: Float) -> Unit = { _, _ -> }
    var touchUpBlock     : AButton.(x: Float, y: Float) -> Unit = { _, _ -> }

    private var area: Actor? = null

    private val animShowTime = 0.050f
    private val animHideTime = 0.400f


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

            screen.game.soundUtil.apply { play(click) }

            //event?.stop()
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
        defaultImage.clearActions()
        pressedImage.clearActions()

        defaultImage.animHide(animHideTime)
        pressedImage.animShow(animShowTime)
    }

    fun unpress() {
        defaultImage.clearActions()
        pressedImage.clearActions()

        defaultImage.animShow(animShowTime)
        pressedImage.animHide(animHideTime)
    }

    fun disable(useDisabledStyle: Boolean = true) {
        touchable = Touchable.disabled

        if (useDisabledStyle) {
            defaultImage.clearActions()
            pressedImage.clearActions()
            disabledImage.clearActions()

            defaultImage.animHide()
            pressedImage.animHide()
            disabledImage.animShow()
        }

    }

    fun enable() {
        touchable = Touchable.enabled

        defaultImage.clearActions()
        pressedImage.clearActions()
        disabledImage.clearActions()

        defaultImage.animShow()
        pressedImage.animHide()
        disabledImage.animHide()

    }

    fun pressAndDisable(useDisabledStyle: Boolean = false) {
        press()
        disable(useDisabledStyle)
    }

    fun unpressAndEnable() {
        unpress()
        enable()
    }

    fun setStyle(style: AButtonStyle) {
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

    private fun getStyleByType(type: Type) = when(type) {
        Type.None -> AButtonStyle(
            default = TextureRegionDrawable(TextureEmpty.region),
            pressed = TextureRegionDrawable(TextureEmpty.region),
            disabled = TextureRegionDrawable(TextureEmpty.region),
        )
        Type.Menu -> AButtonStyle(
            default  = TextureRegionDrawable(screen.game.assetsAll.menu_def),
            pressed  = TextureRegionDrawable(screen.game.assetsAll.menu_press),
            disabled = TextureRegionDrawable(screen.game.assetsAll.menu_press),
        )
        Type.PlusCoin -> AButtonStyle(
            default  = TextureRegionDrawable(screen.game.assetsAll.plus_coin_def),
            pressed  = TextureRegionDrawable(screen.game.assetsAll.plus_coin_press),
            disabled = TextureRegionDrawable(screen.game.assetsAll.plus_coin_press),
        )
        Type.Left -> AButtonStyle(
            default  = TextureRegionDrawable(screen.game.assetsAll.left_def),
            pressed  = TextureRegionDrawable(screen.game.assetsAll.left_press),
            disabled = TextureRegionDrawable(screen.game.assetsAll.left_press),
        )
        Type.Right -> AButtonStyle(
            default  = TextureRegionDrawable(screen.game.assetsAll.right_def),
            pressed  = TextureRegionDrawable(screen.game.assetsAll.right_press),
            disabled = TextureRegionDrawable(screen.game.assetsAll.right_press),
        )
        Type.Back -> AButtonStyle(
            default  = TextureRegionDrawable(screen.game.assetsAll.back_def),
            pressed  = TextureRegionDrawable(screen.game.assetsAll.back_press),
            disabled = TextureRegionDrawable(screen.game.assetsAll.back_press),
        )
        Type.Play -> AButtonStyle(
            default  = TextureRegionDrawable(screen.game.assetsAll.play_def),
            pressed  = TextureRegionDrawable(screen.game.assetsAll.play_press),
            disabled = TextureRegionDrawable(screen.game.assetsAll.play_press),
        )
        Type.Zero -> AButtonStyle(
            default  = TextureRegionDrawable(screen.game.assetsAll.zero_def),
            pressed  = TextureRegionDrawable(screen.game.assetsAll.zero_press),
            disabled = TextureRegionDrawable(screen.game.assetsAll.zero_press),
        )
        Type.One -> AButtonStyle(
            default  = TextureRegionDrawable(screen.game.assetsAll.one_def),
            pressed  = TextureRegionDrawable(screen.game.assetsAll.one_press),
            disabled = TextureRegionDrawable(screen.game.assetsAll.one_press),
        )
        Type.Two -> AButtonStyle(
            default  = TextureRegionDrawable(screen.game.assetsAll.two_def),
            pressed  = TextureRegionDrawable(screen.game.assetsAll.two_press),
            disabled = TextureRegionDrawable(screen.game.assetsAll.two_press),
        )
        Type.Privacy -> AButtonStyle(
            default  = TextureRegionDrawable(screen.game.assetsAll.privacy_def),
            pressed  = TextureRegionDrawable(screen.game.assetsAll.privacy_press),
            disabled = TextureRegionDrawable(screen.game.assetsAll.privacy_press),
        )
        Type.Web -> AButtonStyle(
            default  = TextureRegionDrawable(screen.game.assetsAll.web_def),
            pressed  = TextureRegionDrawable(screen.game.assetsAll.web_press),
            disabled = TextureRegionDrawable(screen.game.assetsAll.web_press),
        )
        Type.K1 -> AButtonStyle(
            default  = TextureRegionDrawable(screen.game.assetsAll.k1_def),
            pressed  = TextureRegionDrawable(screen.game.assetsAll.k1_press),
            disabled = TextureRegionDrawable(screen.game.assetsAll.k1_press),
        )
        Type.K2 -> AButtonStyle(
            default  = TextureRegionDrawable(screen.game.assetsAll.k2_def),
            pressed  = TextureRegionDrawable(screen.game.assetsAll.k2_press),
            disabled = TextureRegionDrawable(screen.game.assetsAll.k2_press),
        )
        Type.K5 -> AButtonStyle(
            default  = TextureRegionDrawable(screen.game.assetsAll.k5_def),
            pressed  = TextureRegionDrawable(screen.game.assetsAll.k5_press),
            disabled = TextureRegionDrawable(screen.game.assetsAll.k5_press),
        )
        Type.Home -> AButtonStyle(
            default  = TextureRegionDrawable(screen.game.assetsAll.home_def),
            pressed  = TextureRegionDrawable(screen.game.assetsAll.home_press),
            disabled = TextureRegionDrawable(screen.game.assetsAll.home_press),
        )
        Type.Use -> AButtonStyle(
            default  = TextureRegionDrawable(screen.game.assetsAll.use_def),
            pressed  = TextureRegionDrawable(screen.game.assetsAll.use_press),
            disabled = TextureRegionDrawable(screen.game.assetsAll.use_press),
        )
        Type.Get -> AButtonStyle(
            default  = TextureRegionDrawable(screen.game.assetsAll.get_def),
            pressed  = TextureRegionDrawable(screen.game.assetsAll.get_press),
            disabled = TextureRegionDrawable(screen.game.assetsAll.get_press),
        )
    }

    // ---------------------------------------------------
    // Style
    // ---------------------------------------------------

    data class AButtonStyle(
        var default: Drawable,
        var pressed: Drawable,
        var disabled: Drawable,
    )

    enum class Type {
        None, Menu, PlusCoin, Left, Right, Back, Play,
        Zero, One, Two,
        Privacy, Web,
        K1, K2, K5,
        Home, Use,
        Get,
    }

    companion object {
        val listTypeCoins = listOf(Type.Zero, Type.One, Type.Two)
        val listTypeShop  = listOf(Type.K2, Type.K5, Type.K1)
    }

}