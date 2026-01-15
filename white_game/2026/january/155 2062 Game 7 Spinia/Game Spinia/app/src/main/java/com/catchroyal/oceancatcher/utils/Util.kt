package com.catchroyal.oceancatcher.utils

import android.util.Log
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

val Any.region: TextureRegion get() = if (this is Texture) TextureRegion(this) else throw TypeCastException("Type is not Texture.")

val CharSequence.int: Int get() = toString().toInt()

fun log(message: String) {
    Log.i("VLAD", message)
}

fun disposeAll(vararg disposable: Disposable) {
    disposable.forEach { it.dispose() }
}

fun cancelCoroutinesAll(vararg coroutine: CoroutineScope) {
    coroutine.forEach { it.cancel() }
}

fun Batch.begend(block: Batch.() -> Unit) {
    begin()
    block()
    end()
}

fun InputMultiplexer.addProcessors(vararg processor: InputProcessor) {
    processor.onEach { addProcessor(it) }
}

fun <T> Iterable<T>.minusFew(vararg element: T): List<T> = minus(element.toSet())