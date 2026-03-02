package com.pyramidconnect.sorting.game.actors.button

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.pyramidconnect.sorting.game.utils.actor.HAlign
import com.pyramidconnect.sorting.game.utils.actor.VAlign
import com.pyramidconnect.sorting.game.utils.actor.addActorAligned
import com.pyramidconnect.sorting.game.utils.actor.addAndFillActor
import com.pyramidconnect.sorting.game.utils.actor.disable
import com.pyramidconnect.sorting.game.utils.advanced.AdvancedScreen
import com.pyramidconnect.sorting.game.utils.gdxGame

open class AImageButton(
    override val screen: AdvancedScreen,
    type: Type,
) : AButton(screen, AButton.Type.DEF) {

    private val dataType = getDataType(type)
    private val img      = Image(dataType.region).apply { setSize(dataType.size.x, dataType.size.y) }

    override fun addActorsOnGroup() {
        super.addActorsOnGroup()

        img.disable()
        addActorAligned(img, HAlign.CENTER, VAlign.CENTER)
    }

    private fun getDataType(type: Type) = when(type) {
        Type.SETTINGS -> DataType(
            region = gdxGame.assetsAll.SETT,
            size   = Vector2(90f, 96f)
        )
        Type.RECORD -> DataType(
            region = gdxGame.assetsAll.RECORD,
            size   = Vector2(96f, 96f)
        )
        Type.BACK -> DataType(
            region = gdxGame.assetsAll.BACK,
            size   = Vector2(140f, 140f)
        )
    }

    data class DataType(
        val region: Texture,
        val size  : Vector2
    )

    enum class Type {
        SETTINGS, RECORD, BACK
    }

}