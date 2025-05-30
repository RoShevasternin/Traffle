package com.swee.ttrio.comb.game.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.swee.ttrio.comb.game.utils.advanced.AdvancedGroup
import com.swee.ttrio.comb.game.utils.advanced.AdvancedScreen

class Mask(
    override val screen    : AdvancedScreen,
    private val mask       : Texture? = null,
    private var alphaWidth : Int = 50,
    private var alphaHeight: Int = 50,
) : AdvancedGroup() {

    private var alphaPixmap: Pixmap?  = null
    private var blackPixmap: Pixmap?  = null
    private var maskTexture: Texture? = null

    override fun addActorsOnGroup() {
        alphaPixmap = Pixmap(width.toInt()+alphaWidth, height.toInt()+alphaHeight, Pixmap.Format.Alpha).apply {
            setColor(0f, 0f, 0f, 0f)
            fill()
        }

        maskTexture = if (mask != null) Texture(alphaPixmap).combineByCenter(mask) else {
            blackPixmap = Pixmap(width.toInt(), height.toInt(), Pixmap.Format.Alpha).apply {
                setColor(0f, 0f, 0f, 1f)
                fill()
            }
            Texture(alphaPixmap).combineByCenter(Texture(blackPixmap))
        }
    }

    override fun dispose() {
        alphaPixmap?.let { if (it.isDisposed.not()) it.dispose() }
        blackPixmap?.let { if (it.isDisposed.not()) it.dispose() }

        maskTexture?.dispose()
        mask?.dispose()

        super.dispose()
    }

    // ---------------------------------------------------
    // Mask
    // ---------------------------------------------------

    override fun draw(batch: Batch?, parentAlpha: Float) {
        if (stage != null) {
            batch?.flush()
            batch?.drawMask(parentAlpha)
        }
    }

    private fun drawSuper(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
    }

    private fun Batch.drawMask(parentAlpha: Float) {
       Gdx.gl.glColorMask(false, false, false, true)

       setBlendFunction(GL20.GL_ONE, GL20.GL_ZERO)

        setColor(0f, 0f, 0f, parentAlpha)
        maskTexture?.let { draw(it,
            x + (width / 2) - (it.width / 2),
            y + (height /2) - (it.height / 2),
            it.width.toFloat(), it.height.toFloat()
        ) }

        drawMasked(parentAlpha)
    }

    private fun Batch.drawMasked(parentAlpha: Float) {
        setBlendFunction(GL20.GL_ZERO, GL20.GL_SRC_ALPHA)
        drawSuper(this, parentAlpha)
        flush()

        Gdx.gl.glColorMask(true, true, true, true)
        setBlendFunction(GL20.GL_DST_ALPHA, GL20.GL_ONE_MINUS_DST_ALPHA)
        drawSuper(this, parentAlpha)
        flush()

        setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
    }

    fun Texture.combineByCenter(texture: Texture): Texture {
        if (textureData.isPrepared.not()) textureData.prepare()
        val pixmap1 = textureData.consumePixmap()

        if (texture.textureData.isPrepared.not()) texture.textureData.prepare()
        val pixmap2 = texture.textureData.consumePixmap()

        pixmap1.drawPixmap(pixmap2,
            (width / 2) - (texture.width / 2),
            (height / 2) - (texture.height / 2),
        )
        val textureResult = Texture(pixmap1)

        if (pixmap1.isDisposed.not()) pixmap1.dispose()
        if (pixmap2.isDisposed.not()) pixmap2.dispose()

        dispose()
        texture.dispose()

        return textureResult
    }

}