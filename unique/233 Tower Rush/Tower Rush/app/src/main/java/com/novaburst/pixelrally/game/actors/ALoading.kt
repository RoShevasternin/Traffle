package com.novaburst.pixelrally.game.actors

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.novaburst.pixelrally.game.utils.advanced.AdvancedGroup
import com.novaburst.pixelrally.game.utils.advanced.AdvancedScreen
import com.novaburst.pixelrally.game.utils.gdxGame

class ALoading(override val screen: AdvancedScreen): AdvancedGroup() {

    private val imgLoading    = Image(gdxGame.assetsLoader.loading)
    private val listImgCircle = List(3) { Image(gdxGame.assetsLoader.circle) }

    override fun addActorsOnGroup() {
        addAndFillActor(imgLoading)
        addListImgCircle()
    }

    // Actors ------------------------------------------------------------------------

    private fun addListImgCircle() {
        addActors(listImgCircle)

        var nx = 256f
        listImgCircle.onEachIndexed { index, img ->
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