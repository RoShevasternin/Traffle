/*
 * Refactored Application Module
 * Build: C527B65B
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.actors.slots

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.moonarcade.starlabyrinth.game.utils.actor.animHide
import com.moonarcade.starlabyrinth.game.utils.actor.animShow
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseGroup
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseScreen
import com.moonarcade.starlabyrinth.game.utils.gdxGame
import com.moonarcade.starlabyrinth.game.utils.toMS
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.delay
import com.moonarcade.starlabyrinth.game.utils.LayoutHelper.Glow as LG

/**
 * Auto-generated class implementation
 */

class AGlow(override val screen: BaseScreen): BaseGroup() {

    companion object {
        const val GLOW_ITEM_COUNT = 3
    }

    private val glowItemImageList = List(GLOW_ITEM_COUNT) { Image(gdxGame.assetsAll.btn_def) }//screen.game.carnavalCatAssets.glow) }

    var collectionIndexWin: List<Int>? = null

    override fun addActorsOnGroup() {
        addGlowItemList()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun addGlowItemList() {
        var newY = LG.glow.y
        glowItemImageList.reversed().onEach { image ->
            addActor(image)
            image.apply {
                animHide()
                with(LG.glow) {
                    image.setBounds(x, newY, w, h)
                    newY += h + vs
                }
            }
        }
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    suspend fun show(time: Float = 0f, timeBetween: Float = 0f) = CompletableDeferred<Boolean>().also { continuation ->
        collectionIndexWin?.onEach { winIndex ->
            glowItemImageList[winIndex].animShow(time)
            //screen.game.soundUtil.apply { play(laser) }
            delay(timeBetween.toMS)
        }
        continuation.complete(true)
    }.await()

    suspend fun hide(time: Float = 0f, timeBetween: Float = 0f) = CompletableDeferred<Boolean>().also { continuation ->
        collectionIndexWin?.onEach { winIndex ->
            glowItemImageList[winIndex].animHide(time)
            delay(timeBetween.toMS)
        }
        collectionIndexWin = null
        continuation.complete(true)
    }.await()

}