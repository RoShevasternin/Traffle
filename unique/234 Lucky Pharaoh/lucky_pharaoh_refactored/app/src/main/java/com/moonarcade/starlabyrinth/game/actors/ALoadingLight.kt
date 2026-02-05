/*
 * Refactored Application Module
 * Build: 7F0EE781
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.moonarcade.starlabyrinth.game.utils.actor.PosSize
import com.moonarcade.starlabyrinth.game.utils.actor.setBounds
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseGroup
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseScreen
import com.moonarcade.starlabyrinth.game.utils.gdxGame

/**
 * Auto-generated class implementation
 */

class ALoadingLight(override val screen: BaseScreen): BaseGroup() {

    private val imgLight = Image(gdxGame.assetsLoader.light)
    private val collectionImgFruit = List(3) { Image(gdxGame.assetsLoader.fruit) }

    override fun addActorsOnGroup() {
        addListImgFruit()
        addAndFillActor(imgLight)
    }

    // Actors ------------------------------------------------------------------------

    // Primary method handler
    private fun addListImgFruit() {
        addActors(collectionImgFruit)

        val collectionPosSize = listOf(
            PosSize(60f, 26f, 304f, 291f),
            PosSize(281f, 128f, 345f, 328f),
            PosSize(507f, 291f, 399f, 377f),
        )
        collectionImgFruit.onEachIndexed { index, img -> img.setBounds(collectionPosSize[index]) }
    }

}