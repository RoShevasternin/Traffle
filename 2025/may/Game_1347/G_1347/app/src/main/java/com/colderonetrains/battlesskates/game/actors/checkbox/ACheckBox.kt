package com.colderonetrains.battlesskates.game.actors.checkbox

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.colderonetrains.battlesskates.game.manager.util.SoundUtil
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedGroup
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedScreen
import com.colderonetrains.battlesskates.game.utils.gdxGame
import com.colderonetrains.battlesskates.game.utils.region
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.collections.set
import kotlin.math.round

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

    private var radius = 0

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

        val touchPointDown = Vector2()
        val touchPointUp   = Vector2()

        var isWithin = false

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

            if (isWithin) {
                if (touchPointDown.x in (touchPointUp.x - radius..touchPointUp.x + radius) &&
                    touchPointDown.y in (touchPointUp.y - radius..touchPointUp.y + radius)) {
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
        defaultImage.drawable = TextureRegionDrawable(style.default)
        checkImage.drawable   = TextureRegionDrawable(style.checked)
    }

    fun setOnCheckListener(radius: Int = 100, block: (Boolean) -> Unit) {
        this.radius = radius
        onCheckBlock = block
    }

    fun getStyleByType(type: Type) = when(type) {
        Type.BEGINNER -> ACheckBoxStyle(
            default = gdxGame.assetsAll.BEGINNER_DEF.region,
            checked = gdxGame.assetsAll.BEGINNER_CHECK.region,
        )
        Type.INTERMEDIATE -> ACheckBoxStyle(
            default = gdxGame.assetsAll.INTER_DEF.region,
            checked = gdxGame.assetsAll.INTER_CHECK.region,
        )
        Type.PRO -> ACheckBoxStyle(
            default = gdxGame.assetsAll.PRO_DEF.region,
            checked = gdxGame.assetsAll.PRO_CHECK.region,
        )

        // ----------------------------------------------------------

        Type.ALL -> ACheckBoxStyle(
            default = gdxGame.assetsAll.ALL_DEF.region,
            checked = gdxGame.assetsAll.ALL_CHECK.region,
        )
        Type.BEGINNER_2 -> ACheckBoxStyle(
            default = gdxGame.assetsAll.BEGINNER_DEF_2.region,
            checked = gdxGame.assetsAll.BEGINNER_CHECK_2.region,
        )
        Type.INTERMEDIATE_2 -> ACheckBoxStyle(
            default = gdxGame.assetsAll.INTER_DEF_2.region,
            checked = gdxGame.assetsAll.INTER_CHECK_2.region,
        )
        Type.PRO_2 -> ACheckBoxStyle(
            default = gdxGame.assetsAll.PRO_DEF_2.region,
            checked = gdxGame.assetsAll.PRO_CHECK_2.region,
        )

        // ----------------------------------------------------------

        Type.TRACK_CATALOG -> ACheckBoxStyle(
            default = gdxGame.assetsAll.TRACK_CATALOG_DEF.region,
            checked = gdxGame.assetsAll.TRACK_CATALOG_CHECK.region,
        )
        Type.RANDOM -> ACheckBoxStyle(
            default = gdxGame.assetsAll.RANDOM_DEF.region,
            checked = gdxGame.assetsAll.RANDOM_CHECK.region,
        )
        Type.GAME -> ACheckBoxStyle(
            default = gdxGame.assetsAll.GAME_DEF.region,
            checked = gdxGame.assetsAll.GAME_CHECK.region,
        )
        Type.ACHIVE -> ACheckBoxStyle(
            default = gdxGame.assetsAll.ACHIVE_DEF.region,
            checked = gdxGame.assetsAll.ACHIVE_CHECK.region,
        )

        // ----------------------------------------------------------

        Type.S2 -> ACheckBoxStyle(
            default = gdxGame.assetsAll.S2_DEF.region,
            checked = gdxGame.assetsAll.S2_CHECK.region,
        )
        Type.S3 -> ACheckBoxStyle(
            default = gdxGame.assetsAll.S3_DEF.region,
            checked = gdxGame.assetsAll.S3_CHECK.region,
        )
        Type.S4 -> ACheckBoxStyle(
            default = gdxGame.assetsAll.S4_DEF.region,
            checked = gdxGame.assetsAll.S4_CHECK.region,
        )

        // ----------------------------------------------------------

        Type.UseCustomTricks -> ACheckBoxStyle(
            default = gdxGame.assetsAll.CUSTOME_TRICKS_DEF.region,
            checked = gdxGame.assetsAll.CUSTOME_TRICKS_CHECK.region,
        )
        Type.CustomTrickItem -> ACheckBoxStyle(
            default = gdxGame.assetsAll.CUSTOME_TRICK_DEF.region,
            checked = gdxGame.assetsAll.CUSTOME_TRICK_CHECK.region,
        )

        // ----------------------------------------------------------

        Type.SKATE_1 -> ACheckBoxStyle(
            default = gdxGame.assetsAll.SKATE_1_DEF.region,
            checked = gdxGame.assetsAll.SKATE_1_PRESS.region,
        )
        Type.SKATE_2 -> ACheckBoxStyle(
            default = gdxGame.assetsAll.SKATE_2_DEF.region,
            checked = gdxGame.assetsAll.SKATE_2_PRESS.region,
        )
        Type.SKATE_3 -> ACheckBoxStyle(
            default = gdxGame.assetsAll.SKATE_3_DEF.region,
            checked = gdxGame.assetsAll.SKATE_3_PRESS.region,
        )
        Type.SKATE_4 -> ACheckBoxStyle(
            default = gdxGame.assetsAll.SKATE_4_DEF.region,
            checked = gdxGame.assetsAll.SKATE_4_PRESS.region,
        )
        Type.SKATE_5 -> ACheckBoxStyle(
            default = gdxGame.assetsAll.SKATE_5_DEF.region,
            checked = gdxGame.assetsAll.SKATE_5_PRESS.region,
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
        BEGINNER, INTERMEDIATE, PRO,
        TRACK_CATALOG, RANDOM, GAME, ACHIVE,
        ALL, BEGINNER_2, INTERMEDIATE_2, PRO_2,
        S2, S3, S4,
        UseCustomTricks, CustomTrickItem,
        SKATE_1, SKATE_2, SKATE_3, SKATE_4, SKATE_5,
    }

}