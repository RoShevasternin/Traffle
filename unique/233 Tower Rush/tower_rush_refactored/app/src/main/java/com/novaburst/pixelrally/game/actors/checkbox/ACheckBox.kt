/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.actors.checkbox

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.novaburst.pixelrally.game.utils.advanced.ComponentGroup
import com.novaburst.pixelrally.game.utils.advanced.DisplayScreen
import com.novaburst.pixelrally.game.utils.gdxGame
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.math.round

open class ACheckBox(
    override val screen: DisplayScreen,
    type: Type? = null,
) : ComponentGroup() {

    private val defaultImage by lazy {  if (type != null) Image(getStyleByType(type).default) else Image() }
    private val checkImage   by lazy { (if (type != null) Image(getStyleByType(type).checked) else Image()).apply { isVisible = false } }

    private var onCheckBlock: (Boolean) -> Unit = { }

    private var isInvokeCheckBlock: Boolean = true
    var checkBoxGroup: ACheckBoxGroup? = null

    val checkFlow = MutableStateFlow(false)

    var radiusTouch = 1000f

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

    // Core functionality
    open fun getListener() = object : InputListener() {
        var isWithin = false
        val touchPointDown = Vector2()
        val touchPointUp   = Vector2()

        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
            touchPointDown.set(round(x), round(y))

            touchDragged(event, x, y, pointer)
            return true
        }

        override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
            isWithin = x in 0f..width && y in 0f..height
        }

        override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
            touchPointUp.set(round(x), round(y))

            com.novaburst.pixelrally.util.log("dd = $touchPointDown | $touchPointUp")
            if (isWithin) {
                if (touchPointDown.x in (touchPointUp.x - radiusTouch..touchPointUp.x + radiusTouch) &&
                    touchPointDown.y in (touchPointUp.y - radiusTouch..touchPointUp.y + radiusTouch)) {

                    gdxGame.soundUtil.apply { play(click) }

                    if (checkBoxGroup != null) {
                        if (checkFlow.value.not()) check()
                    } else {
                        if (checkFlow.value) uncheck() else check()
                    }
                }
            }

        }
    }



    // Processing logic
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
        checkImage.isVisible = false

        checkFlow.value = false
    }

    // Handler method
    fun checkAndDisable() {
        check()
        disable()
    }

    // Function implementation
    fun uncheckAndEnabled() {
        uncheck()
        enable()
    }

    // Function implementation
    fun enable() {
        touchable = Touchable.enabled
    }

    // Core functionality
    fun disable() {
        touchable = Touchable.disabled
    }

    fun setStyle(style: ACheckBoxStyle) {
        defaultImage.drawable = TextureRegionDrawable(style.default)
        checkImage.drawable   = TextureRegionDrawable(style.checked)
    }

    // Core functionality
    fun setOnCheckListener(block: (Boolean) -> Unit) {
        onCheckBlock = block
    }

    // Processing logic
    fun getStyleByType(type: Type) = when(type) {
        Type.DEF -> ACheckBoxStyle(
            default = gdxGame.assetsAll.box_def,
            checked = gdxGame.assetsAll.box_check,
        )
    }

    // ---------------------------------------------------
    // Style
    // ---------------------------------------------------

    data class ACheckBoxStyle(
        val default: TextureRegion,
        val checked: TextureRegion,
    )

    enum class Type {
        DEF,
    }

}