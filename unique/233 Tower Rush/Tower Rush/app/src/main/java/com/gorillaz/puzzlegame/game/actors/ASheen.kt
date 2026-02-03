package com.gorillaz.puzzlegame.game.actors

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.gorillaz.puzzlegame.game.utils.actor.PosSize
import com.gorillaz.puzzlegame.game.utils.actor.disable
import com.gorillaz.puzzlegame.game.utils.actor.setBounds
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedGroup
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedScreen
import com.gorillaz.puzzlegame.game.utils.gdxGame
import kotlin.random.Random

class ASheen(override val screen: AdvancedScreen): AdvancedGroup() {

    private val listImgSheen = List(4) { Image(gdxGame.assetsAll.SHEEN) }

    private val listPosSize = listOf(
        PosSize(552f, 1352f, 530f, 530f),
        PosSize(10f, 1102f, 530f, 530f),
        PosSize(750f, 807f, 341f, 341f),
        PosSize(37f, 423f, 530f, 530f),
    )
    private val listTime = listOf(5f, 7.5f, 10f, 12f).shuffled()

    override fun addActorsOnGroup() {
        addListImgSheen()
        disable()
    }

    // Actors ------------------------------------------------------------------------

    private fun addListImgSheen() {
        addActors(listImgSheen)

        listImgSheen.onEachIndexed { index, img ->
            val random_1 = if (Random.nextBoolean()) 1f else -1f

            img.setBounds(listPosSize[index])
            img.setOrigin(Align.center)

            img.addAction(Actions.forever(
                Actions.rotateBy(360 * random_1, listTime[index])
            ))
        }
    }

}