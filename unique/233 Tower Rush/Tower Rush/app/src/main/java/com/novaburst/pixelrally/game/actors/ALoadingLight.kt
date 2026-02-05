package com.novaburst.pixelrally.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.novaburst.pixelrally.game.utils.actor.PosSize
import com.novaburst.pixelrally.game.utils.actor.setBounds
import com.novaburst.pixelrally.game.utils.advanced.AdvancedGroup
import com.novaburst.pixelrally.game.utils.advanced.AdvancedScreen
import com.novaburst.pixelrally.game.utils.gdxGame

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