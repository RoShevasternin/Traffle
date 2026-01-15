package com.toyscatcher.factoryatoy.advanced

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Widget
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup
import com.badlogic.gdx.utils.Disposable

open class AdvancedGroup : Group(), Disposable {

    private val chainList = mutableListOf<Actor>()



    override fun dispose() {
        children.onEach { if (it is Disposable) it.dispose() }
    }



    private fun List<Actor>.setFillParent() {
        onEach { actor ->
            when (actor) {
                is Widget      -> actor.setFillParent(true)
                is WidgetGroup -> actor.setFillParent(true)
            }
        }
    }

    private fun styleStartTopEndBottom(actor: Actor, limit: Int, spaceX: Float, spaceY: Float) {
        val x: Float
        val y: Float

        if (chainList.isEmpty()) {
            x = 0f
            y = this.height - actor.height
        } else {
            val firstActor = chainList.first()
            val lastActor = chainList.last()

            if (chainList.size % limit == 0) {
                x = firstActor.x
                y = lastActor.y - spaceY - actor.height
            } else {
                x = lastActor.x + lastActor.width + spaceX
                y = lastActor.y
            }
        }
        actor.setPosition(x, y)
        chainList.add(actor)
        addActor(actor)
    }



    fun addAndFillActor(actor: Actor) {
        when (actor) {
            is Widget      -> actor.setFillParent(true)
            is WidgetGroup -> actor.setFillParent(true)
        }
        addActor(actor)
    }

    fun addAndFillActors(actors: List<Actor>) {
        actors.setFillParent()
        actors.forEach { addActor(it) }
    }

    fun addActors(vararg actors: Actor) {
        actors.forEach { addActor(it) }
    }

    fun addActors(actors: List<Actor>) {
        actors.forEach { addActor(it) }
    }

    fun addActorChain(actor: Actor, style: ChainStyle, limit: Int, spaceX: Float, spaceY: Float) {
        if (limit == 0) throw Exception("Limit must be > 0.")

        when (style) {
            ChainStyle.StartTop_EndBottom -> styleStartTopEndBottom(actor, limit, spaceX, spaceY)
        }
    }



    enum class ChainStyle {
        StartTop_EndBottom
    }

}