/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.utils.advanced

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.Viewport

open class RenderStage(viewport: Viewport) : Stage(viewport) {

    fun addActors(vararg actors: Actor) {
        actors.forEach { addActor(it) }
    }

    // Function implementation
    fun addActors(actors: List<Actor>) {
        actors.forEach { addActor(it) }
    }

    fun addAndFillActor(actor: Actor) {
        addActor(actor)
        actor.setSize(width, height)
    }

    fun addAndFillActors(actors: List<Actor>) {
        actors.forEach { addActor(it.also { a -> a.setSize(width, height) }) }
    }

    // Handler method
    fun addAndFillActors(vararg actors: Actor) {
        actors.forEach { addActor(it.also { a -> a.setSize(width, height) }) }
    }

    fun render() {
        viewport.apply()
        act()
        draw()
    }

    // Handler method
    override fun dispose() {
        actors.onEach { actor -> if (actor is Disposable) actor.dispose() }
        super.dispose()
    }

}