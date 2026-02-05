/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.novaburst.pixelrally.game.utils.actor.PosSize
import com.novaburst.pixelrally.game.utils.actor.setBounds
import com.novaburst.pixelrally.game.utils.advanced.ComponentGroup
import com.novaburst.pixelrally.game.utils.advanced.DisplayScreen
import com.novaburst.pixelrally.game.utils.gdxGame

class ALoadingLight(override val screen: DisplayScreen): ComponentGroup() {

    private val imgLight = Image(gdxGame.assetsLoader.light)
    private val listImgFruit = List(3) { Image(gdxGame.assetsLoader.fruit) }

    // Core functionality
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