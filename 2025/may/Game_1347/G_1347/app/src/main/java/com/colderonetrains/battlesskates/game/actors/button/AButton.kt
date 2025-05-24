package com.colderonetrains.battlesskates.game.actors.button

import com.colderonetrains.battlesskates.game.manager.util.SoundUtil
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.colderonetrains.battlesskates.game.utils.TextureEmpty
import com.colderonetrains.battlesskates.game.utils.actor.animHide
import com.colderonetrains.battlesskates.game.utils.actor.animShow
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedGroup
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedScreen
import com.colderonetrains.battlesskates.game.utils.gdxGame
import com.colderonetrains.battlesskates.game.utils.region

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

    private var sound: SoundUtil.AdvancedSound? = null

    private val animShowTime = 0.050f
    private val animHideTime = 0.075f


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
        var isEnter     = false
        var isEnterArea = false

        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
            touchDownBlock(x, y)

            touchDragged(event, x, y, pointer)

            event?.stop()
            return true
        }

        override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
            touchDraggedBlock(x, y)

            isEnter = x in 0f..width && y in 0f..height
            area?.let { isEnterArea = x in 0f..it.width && y in 0f..it.height }

            if (isEnter || isEnterArea) press() else unpress()
        }

        override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
            touchUpBlock(x, y)

            if (isEnter || isEnterArea) {
                unpress()
                sound?.let { gdxGame.soundUtil.apply { play(it) } }
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

    fun setOnClickListener(sound: SoundUtil.AdvancedSound? = gdxGame.soundUtil.click, block: () -> Unit) {
        this.sound   = sound
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
        Type.GET_STARTED -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.GET_STARTED_DEF),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.GET_STARTED_PRESS),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.GET_STARTED_PRESS),
        )
        Type.Back -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.BACK_DEF),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.BACK_PRESS),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.BACK_PRESS),
        )
        Type.Learn -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.LEARN_DEF),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.LEARN_PRESS),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.LEARN_PRESS),
        )
        Type.Home -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.HOME_DEF),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.HOME_PRESS),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.HOME_PRESS),
        )
        Type.DidIt -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.DID_IT_DEF),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.DID_IT_PRESS),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.DID_IT_PRESS),
        )
        Type.BtnBack -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.BTN_BACK_DEF),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.BTN_BACK_PRESS),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.BTN_BACK_PRESS),
        )

        // ----------------------------------------------------

        Type.EnterCustom -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.ENTER_CUSTOM_TRICK_DEF),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.ENTER_CUSTOM_TRICK_PRESS),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.ENTER_CUSTOM_TRICK_PRESS),
        )
        Type.StartButtle -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.START_BATTLE_DEF),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.START_BATTLE_CHECK),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.START_BATTLE_CHECK),
        )

        // ----------------------------------------------------

        Type.LANDED -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.LANDED_DEF),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.LANDED_PRESS),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.LANDED_PRESS),
        )
        Type.FAILED -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.FAILED_DEF),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.FAILED_PRESS),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.FAILED_PRESS),
        )

        // ----------------------------------------------------

        Type.NextPlayer -> AButtonStyle(
            default  = TextureRegionDrawable(gdxGame.assetsAll.NEXT_PLAYER_DEF),
            pressed  = TextureRegionDrawable(gdxGame.assetsAll.NEXT_PLAYER_PRESS),
            disabled = TextureRegionDrawable(gdxGame.assetsAll.NEXT_PLAYER_PRESS),
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
        None, GET_STARTED, Back, Learn, Home, DidIt, BtnBack,
        EnterCustom, StartButtle,
        LANDED, FAILED,
        NextPlayer,
    }

}