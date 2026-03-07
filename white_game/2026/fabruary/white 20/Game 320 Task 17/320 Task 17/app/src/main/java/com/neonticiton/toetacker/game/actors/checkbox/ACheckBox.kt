package com.neonticiton.toetacker.game.actors.checkbox

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.neonticiton.toetacker.game.utils.actor.addAndFillActors
import com.neonticiton.toetacker.game.utils.advanced.AdvancedGroup
import com.neonticiton.toetacker.game.utils.advanced.AdvancedScreen
import com.neonticiton.toetacker.game.utils.gdxGame
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

open class ACheckBox(
    override val screen: AdvancedScreen,
    type: Type? = null,
) : AdvancedGroup() {

    private val defaultImage by lazy {  if (type != null) Image(getStyleByType(type).default) else Image() }
    private val checkImage   by lazy { (if (type != null) Image(getStyleByType(type).checked) else Image()).apply { isVisible = false } }

    private var onCheckBlock: (Boolean) -> Unit = { }

    private var isInvokeCheckBlock: Boolean = true
    var checkBoxGroup: ACheckBoxGroup? = null

    val checkFlow = MutableStateFlow(false)

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
            touchDragged(event, x, y, pointer)
            gdxGame.soundUtil.apply { play(click) }
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

    fun checkAndDisable() {
        check()
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

    fun setStyle(style: ACheckBoxStyle) {
        defaultImage.drawable = style.default
        checkImage.drawable   = style.checked
    }

    fun setOnCheckListener(block: (Boolean) -> Unit) {
        onCheckBlock = block
    }

    fun getStyleByType(type: Type) = when(type) {
        Type.SOUND -> ACheckBoxStyle(
            default = TextureRegionDrawable(gdxGame.assetsAll.SD),
            checked = TextureRegionDrawable(gdxGame.assetsAll.SP),
        )
        Type.MUSIC -> ACheckBoxStyle(
            default = TextureRegionDrawable(gdxGame.assetsAll.MD),
            checked = TextureRegionDrawable(gdxGame.assetsAll.MP),
        )
    }

    // ---------------------------------------------------
    // Style
    // ---------------------------------------------------

    data class ACheckBoxStyle(
        val default: Drawable,
        val checked: Drawable,
    )

    enum class Type {
        SOUND, MUSIC
    }


}