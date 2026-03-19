package com.veldan.cosmolot.game.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.opengl.GLES20
import android.opengl.GLUtils
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Widget
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.Viewport
import com.veldan.cosmolot.game.game
import com.veldan.cosmolot.util.length

val Texture.region: TextureRegion get() = TextureRegion(this)

val Viewport.zeroScreenVector: Vector2 get() = project(Vector2(0f, 0f))

val FUN = " FUN"

val TextureEmpty = TextureRegion(Texture(1, 1, Pixmap.Format.RGBA8888))


fun disposeAll(vararg disposable: Disposable) {
    disposable.forEach { it.dispose() }
}

fun Batch.beginend(block: Batch.() -> Unit = { }) {
    begin()
    block()
    end()
}

fun Batch.endbegin(block: Batch.() -> Unit = { }) {
    end()
    block()
    begin()
}

fun Bitmap.texture() = Texture(width, height, Pixmap.Format.RGBA8888).also { tex ->
    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, tex.textureObjectHandle)
    GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, this, 0)
    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)
    recycle()
}

fun Uri.bitmap(): Bitmap = game.activity.contentResolver.openInputStream(this).use { data -> BitmapFactory.decodeStream(data) }


fun runGDX(block: () -> Unit) {
    Gdx.app.postRunnable { block() }
}

fun InputMultiplexer.addProcessors(vararg processor: InputProcessor) {
    processor.onEach { addProcessor(it) }
}

fun Actor.disable() {
    touchable = Touchable.disabled
}

fun Actor.enable() {
    touchable = Touchable.enabled
}

fun List<Actor>.setFillParent() {
    onEach { actor ->
        when (actor) {
            is Widget      -> actor.setFillParent(true)
            is WidgetGroup -> actor.setFillParent(true)
        }
    }
}

fun Actor.setBounds(layoutData: Layout.LayoutData) {
    with(layoutData) { setBounds(x, y, w, h) }
}


fun Long.transformToBalanceFormat(): String {
    val balance = toString().toMutableList()

    when (length) {
        4    -> balance.add(1, ' ')
        5    -> balance.add(2, ' ')
        6    -> balance.add(3, ' ')
        7    -> {
            balance.add(1, ' ')
            balance.add(5, ' ')
        }
        8    -> {
            balance.add(2, ' ')
            balance.add(6, ' ')
        }
        9    -> {
            balance.add(3, ' ')
            balance.add(7, ' ')
        }
        10   -> {
            balance.add(1, ' ')
            balance.add(5, ' ')
            balance.add(9, ' ')
        }
        11   -> {
            balance.add(2, ' ')
            balance.add(6, ' ')
            balance.add(10, ' ')
        }
        12   -> {
            balance.add(3, ' ')
            balance.add(7, ' ')
            balance.add(11, ' ')
        }
        else -> toString()
    }

    return balance.joinToString("")
}

data class Size(
    var width: Float = 0f,
    var height: Float = 0f,
) {
    fun set(width: Float, height: Float) {
        this.width = width
        this.height = height
    }

    fun set(size: Size) {
        width = size.width
        height = size.height
    }

}

enum class AutospinState {
    DEFAULT, GO
}