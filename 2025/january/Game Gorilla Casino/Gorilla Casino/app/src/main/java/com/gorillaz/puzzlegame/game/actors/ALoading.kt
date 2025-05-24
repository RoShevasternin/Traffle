package com.gorillaz.puzzlegame.game.actors

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedGroup
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedScreen
import com.gorillaz.puzzlegame.game.utils.gdxGame
import com.gorillaz.puzzlegame.util.log

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

        var nx = 466f
        listImgCircle.onEachIndexed { index, img ->
            img.color.a = 0f
            img.setBounds(nx, 98f, 29f, 29f)
            nx += 8 + 29

            img.addAction(Actions.forever(Actions.sequence(
                Actions.delay(0.15f * index),
                Actions.fadeIn(0.25f),
                Actions.fadeOut(0.25f),
                Actions.delay(0.15f * (2 - index))
            )))
        }
    }

}