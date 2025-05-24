package com.slotscity.official.game.actors.checkbox

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.slotscity.official.game.actors.button.AButton
import com.slotscity.official.game.utils.TextureEmpty
import com.slotscity.official.game.utils.advanced.AdvancedGroup
import com.slotscity.official.game.utils.advanced.AdvancedScreen
import com.slotscity.official.game.utils.region
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

open class ACheckBox(
    override val screen: AdvancedScreen,
    type: Static.IType? = null,
) : AdvancedGroup() {

    private val defaultImage by lazy {  if (type != null) Image(getStyleByType(type).default) else Image() }
    private val checkImage   by lazy { (if (type != null) Image(getStyleByType(type).checked) else Image()).apply { isVisible = false } }

    private var onCheckBlock: (Boolean) -> Unit = { }

    private var isInvokeCheckBlock: Boolean = true
    var checkBoxGroup: ACheckBoxGroup? = null

    val checkFlow = MutableStateFlow(false)

    private val assets                 by lazy { screen.game.allAssets }
    private val spriteUtil_CarnavalCat by lazy { screen.game.carnavalCatAssets }
    private val spriteUtil_SweetBonanza by lazy { screen.game.sweetBonanzaAssets }


    override fun addActorsOnGroup() {
        addAndFillActors(getActors())
        addListener(getListener())
        asyncCollectCheckFlow()
    }

    private fun getActors() = listOf<Actor>(
        defaultImage,
        checkImage,
    )

    private fun asyncCollectCheckFlow() {
        coroutine?.launch { checkFlow.collect { isCheck -> if (isInvokeCheckBlock) onCheckBlock(isCheck) } }
    }

    open fun getListener() = object : InputListener() {
        var isWithin = false

        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
            screen.game.soundUtil.apply { play(click) }
            touchDragged(event, x, y, pointer)
            return true
        }

        override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
            isWithin = x in 0f..width && y in 0f..height
        }

        override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
            if (isWithin) {
                if (checkBoxGroup != null) {
                    if (checkFlow.value.not()) check()
                } else {
                    if (checkFlow.value) uncheck() else check()
                }
            }
        }
    }



    fun check(isInvokeCheckBlock: Boolean = true) {
        this.isInvokeCheckBlock = isInvokeCheckBlock

        checkBoxGroup?.let {
            it.currentCheckedCheckBox?.uncheck()
            it.currentCheckedCheckBox = this
        }

        defaultImage.isVisible = false
        checkImage.isVisible   = true

        checkFlow.value = true
    }

    fun uncheck(isInvokeCheckBlock: Boolean = true) {
        this.isInvokeCheckBlock = isInvokeCheckBlock

        defaultImage.isVisible = true
        checkImage.isVisible   = false

        checkFlow.value = false
    }

    fun checkAndDisable(isInvokeCheckBlock: Boolean = true) {
        check(isInvokeCheckBlock)
        disable()
    }

    fun uncheckAndEnabled() {
        uncheck()
        enable()
    }

    fun enable() {
        touchable = Touchable.enabled
    }

    fun disable() {
        touchable = Touchable.disabled
    }

    fun setStyle(style: Static.ACheckBoxStyle) {
        defaultImage.drawable = TextureRegionDrawable(style.default)
        checkImage.drawable   = TextureRegionDrawable(style.checked)
    }

    fun setOnCheckListener(block: (Boolean) -> Unit) {
        onCheckBlock = block
    }

    fun getStyleByType(type: Static.IType) = when(type) {
        Static.Type.ON_OFF -> Static.ACheckBoxStyle(
            default = assets.off,
            checked = assets.on,
        )
        Static.Type.SPIN_BTN -> Static.ACheckBoxStyle(
            default = assets.spin_btn_deff,
            checked = assets.spin_btn_press,
        )
        Static.Type.VALUE -> Static.ACheckBoxStyle(
            default = assets.v_off,
            checked = assets.v_on,
        )
        Static.Type.LAUNCHER_SVET -> Static.ACheckBoxStyle(
            default = TextureEmpty.region,
            checked = assets.launcher_svecheni.region,
        )
        Static.Type.BONUS -> Static.ACheckBoxStyle(
            default = assets.bonus_def.region,
            checked = assets.bonus_dis.region,
        )

        Static.CarnavalCatType.SPEED -> Static.ACheckBoxStyle(
            default = spriteUtil_CarnavalCat.speed_def,
            checked = spriteUtil_CarnavalCat.speed_press,
        )

        Static.SweetBonanzaType.ON_OFF -> Static.ACheckBoxStyle(
            default = spriteUtil_SweetBonanza.vikl,
            checked = spriteUtil_SweetBonanza.vkl,
        )

        else -> Static.ACheckBoxStyle(
            default = TextureEmpty.region,
            checked = TextureEmpty.region,
        )
    }

    // ---------------------------------------------------
    // Style
    // ---------------------------------------------------

    object Static {
        data class ACheckBoxStyle(
            val default: TextureRegion,
            val checked: TextureRegion,
        )

        interface IType

        enum class Type: IType {
            ON_OFF, SPIN_BTN, VALUE, LAUNCHER_SVET, BONUS
        }

        enum class CarnavalCatType: IType {
            SPEED
        }

        enum class SweetBonanzaType: IType {
            ON_OFF
        }
    }

}