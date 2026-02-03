package com.gorillaz.puzzlegame.game.actors

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.gorillaz.puzzlegame.game.utils.Acts
import com.gorillaz.puzzlegame.game.utils.actor.PosSize
import com.gorillaz.puzzlegame.game.utils.actor.animDelay
import com.gorillaz.puzzlegame.game.utils.actor.disable
import com.gorillaz.puzzlegame.game.utils.actor.setBounds
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedGroup
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedScreen
import com.gorillaz.puzzlegame.game.utils.gdxGame
import kotlin.random.Random

class AGems(override val screen: AdvancedScreen): AdvancedGroup() {

    private val listImgGem = List(8) { Image(gdxGame.assetsAll.gem) }

    private val listPosSize = listOf(
        PosSize(762f, 1748f, 62f, 60f),
        PosSize(672f, 1694f, 92f, 88f),
        PosSize(797f, 1629f, 128f, 124f),
        PosSize(27f, 1467f, 151f, 145f),
        PosSize(196f, 800f, 105f, 102f),
        PosSize(74f, 679f, 150f, 145f),
        PosSize(815f, 55f, 179f, 172f),
        PosSize(952f, 2f, 105f, 101f),
    )
    private val listAngle = listOf(-7f, 12f, -12f, -13f, 3f, 32f, -31f, 18f)

    override fun addActorsOnGroup() {
        addListImgGems()
        disable()
    }

    // Actors ------------------------------------------------------------------------

    private fun addListImgGems() {
        addActors(listImgGem)

        listImgGem.onEachIndexed { index, img ->
            img.rotation = listAngle[index]

            val random_1    = if (Random.nextBoolean()) 1f else -1f
            val randomScale = (10..40).random() / 100f

            img.setBounds(listPosSize[index])
            img.setOrigin(Align.center)

            img.animDelay((0..150).random() / 100f) {
                img.addAction(Actions.forever(Acts.sequence(
                    Acts.scaleBy(randomScale * random_1, randomScale * random_1, 0.5f, Interpolation.sine),
                    Acts.scaleTo(1f, 1f, 0.5f, Interpolation.sine),
                )))

                img.addAction(Actions.forever(Acts.sequence(
                    Acts.rotateBy(listAngle[index] * random_1, 0.5f, Interpolation.sine),
                    Acts.rotateTo(listAngle[index], 0.5f, Interpolation.sine),
                )))
            }

        }
    }

}