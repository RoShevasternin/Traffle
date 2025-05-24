package com.viade.bepuzzle.game.utils.actor

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Widget
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup
import com.viade.bepuzzle.game.actors.button.AButton
import com.viade.bepuzzle.game.manager.util.SoundUtil
import com.viade.bepuzzle.game.utils.runGDX
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

fun Actor.setOnClickListener(soundUtil: SoundUtil? = null, block: (Actor) -> Unit) {
    addListener(object : InputListener() {
        var isWithin = false

        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
            touchDragged(event, x, y, pointer)
            soundUtil?.apply { play(click) }
            return true
        }

        override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
            isWithin = x in 0f..width && y in 0f..height
        }

        override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
            if (isWithin) {
                isWithin = false
                block(this@setOnClickListener)
            }
        }
    })
}

fun Actor.disable() = when(this) {
    is AButton -> disable()
    else       -> touchable = Touchable.disabled
}

fun Actor.enable() = when(this) {
    is AButton -> enable()
    else       -> touchable = Touchable.enabled
}

fun List<Actor>.setFillParent() {
    onEach { actor ->
        when (actor) {
            is Widget      -> actor.setFillParent(true)
            is WidgetGroup -> actor.setFillParent(true)
        }
    }
}

fun Actor.setBounds(position: Vector2, size: Vector2) {
    setBounds(position.x, position.y, size.x, size.y)
}

fun Actor.setBounds(vPosSize: PosSize) {
    setBounds(vPosSize.x, vPosSize.y, vPosSize.w, vPosSize.h)
}

fun Actor.setPosition(position: Vector2) {
    setPosition(position.x, position.y)
}

fun Actor.setOrigin(vector: Vector2) {
    setOrigin(vector.x, vector.y)
}

fun Actor.animShow(time: Float=0f, block: () -> Unit = {}) {
    addAction(Actions.sequence(
        Actions.fadeIn(time),
        Actions.run(block)
    ))
}
fun Actor.animHide(time: Float=0f, block: () -> Unit = {}) {
    addAction(Actions.sequence(
        Actions.fadeOut(time),
        Actions.run(block)
    ))
}

// Anim Suspend
suspend fun Actor.animShowSuspend(time: Float = 0f, block: () -> Unit = {}) = suspendCoroutine<Unit> { continuation ->
    runGDX {
        animShow(time) {
            block()
            continuation.resume(Unit)
        }
    }
}

suspend fun Actor.animHideSuspend(time: Float = 0f, block: () -> Unit = {}) = suspendCoroutine<Unit> { continuation ->
    runGDX {
        animHide(time) {
            block()
            continuation.resume(Unit)
        }
    }
}

suspend fun Actor.animMoveToSuspend(x: Float, y: Float, time: Float = 0f, interpolation: Interpolation = Interpolation.linear, block: () -> Unit = {}) = suspendCoroutine<Unit> { continuation ->
    runGDX {
        addAction(Actions.sequence(
            Actions.moveTo(x, y, time, interpolation),
            Actions.run {
                block()
                continuation.resume(Unit)
            }
        ))
    }
}

data class PosSize(val x: Float, val y: Float, val w: Float, val h: Float)