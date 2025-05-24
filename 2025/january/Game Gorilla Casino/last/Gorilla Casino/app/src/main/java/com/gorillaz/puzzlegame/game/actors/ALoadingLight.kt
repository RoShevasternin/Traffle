package com.gorillaz.puzzlegame.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.gorillaz.puzzlegame.game.utils.actor.PosSize
import com.gorillaz.puzzlegame.game.utils.actor.setBounds
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedGroup
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedScreen
import com.gorillaz.puzzlegame.game.utils.gdxGame

class ALoadingLight(override val screen: AdvancedScreen): AdvancedGroup() {

    private val imgLight     = Image(gdxGame.assetsLoader.light)
    private val listImgFruit = List(3) { Image(gdxGame.assetsLoader.fruit) }

    override fun addActorsOnGroup() {
        addListImgFruit()
        addAndFillActor(imgLight)
    }

    // Actors ------------------------------------------------------------------------

    private fun addListImgFruit() {
        addActors(listImgFruit)

        val listPosSize = listOf(
            PosSize(60f, 26f, 304f, 291f),
            PosSize(281f, 128f, 345f, 328f),
            PosSize(507f, 291f, 399f, 377f),
        )
        listImgFruit.onEachIndexed { index, img -> img.setBounds(listPosSize[index]) }
    }

}