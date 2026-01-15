package com.toyscatcher.factoryatoy.advanced

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.Viewport

open class AdvancedStage : Stage {

    constructor() : super()
    constructor(viewport: Viewport) : super(viewport)
    constructor(viewport: Viewport, batch: Batch) : super(viewport, batch)

    fun addActors(vararg actors: Actor) {
        actors.forEach { addActor(it) }
    }

    fun addActors(actors: List<Actor>) {
        actors.forEach { addActor(it) }
    }

    fun render() {
        act()
        draw()
    }

    override fun dispose() {
        actors.forEach { actor ->
            if (actor is Disposable) actor.dispose()
            super.dispose()
        }
    }

}