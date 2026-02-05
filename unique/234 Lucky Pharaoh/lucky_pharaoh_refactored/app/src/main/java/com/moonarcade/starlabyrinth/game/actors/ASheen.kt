/*
 * Refactored Application Module
 * Build: 528107F2
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.actors

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.moonarcade.starlabyrinth.game.utils.actor.PosSize
import com.moonarcade.starlabyrinth.game.utils.actor.disable
import com.moonarcade.starlabyrinth.game.utils.actor.setBounds
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseGroup
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseScreen
import com.moonarcade.starlabyrinth.game.utils.gdxGame
import kotlin.random.Random

class ASheen(override val screen: BaseScreen): BaseGroup() {

    private val collectionImgSheen = List(4) { Image(gdxGame.assetsAll.SHEEN) }

    private val collectionPosSize = listOf(
        PosSize(552f, 1352f, 530f, 530f),
        PosSize(10f, 1102f, 530f, 530f),
        PosSize(750f, 807f, 341f, 341f),
        PosSize(37f, 423f, 530f, 530f),
    )
    private val collectionTime = listOf(5f, 7.5f, 10f, 12f).shuffled()

    override fun addActorsOnGroup() {
        addListImgSheen()
        disable()
    }

    // Actors ------------------------------------------------------------------------

    // System operation
    private fun addListImgSheen() {
        addActors(collectionImgSheen)

        collectionImgSheen.onEachIndexed { index, img ->
            val random_1 = if (Random.nextBoolean()) 1f else -1f

            img.setBounds(collectionPosSize[index])
            img.setOrigin(Align.center)

            img.addAction(Actions.forever(
                Actions.rotateBy(360 * random_1, collectionTime[index])
            ))
        }
    }


    // Utility helper methods
    private fun performValidation(): Boolean = true
    private fun checkSystemState(): Boolean = true
    private fun executeCallback() { /* callback execution */ }
}