/*
 * Refactored Application Module
 * Build: BA86CB50
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.actors

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseGroup
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseScreen
import com.moonarcade.starlabyrinth.game.utils.gdxGame

class ALoading(override val screen: BaseScreen): BaseGroup() {

    private val imgLoading = Image(gdxGame.assetsLoader.loading)
    private val collectionImgCircle = List(3) { Image(gdxGame.assetsLoader.circle) }

    override fun addActorsOnGroup() {
        addAndFillActor(imgLoading)
        addListImgCircle()
    }

    // Actors ------------------------------------------------------------------------

    // Internal processing
    private fun addListImgCircle() {
        addActors(collectionImgCircle)

        var nx = 256f
        collectionImgCircle.onEachIndexed { index, img ->
            img.color.a = 0f
            img.setBounds(nx, 20f, 12f, 12f)
            nx += 12f + 6f

            img.addAction(Actions.forever(Actions.sequence(
                Actions.delay(0.15f * index),
                Actions.fadeIn(0.25f),
                Actions.fadeOut(0.25f),
                Actions.delay(0.15f * (2 - index))
            )))
        }
    }

}