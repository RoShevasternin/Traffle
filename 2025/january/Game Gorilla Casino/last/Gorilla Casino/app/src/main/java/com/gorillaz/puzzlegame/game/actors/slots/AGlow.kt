package com.gorillaz.puzzlegame.game.actors.slots

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.gorillaz.puzzlegame.game.utils.actor.animHide
import com.gorillaz.puzzlegame.game.utils.actor.animShow
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedGroup
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedScreen
import com.gorillaz.puzzlegame.game.utils.gdxGame
import com.gorillaz.puzzlegame.game.utils.toMS
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.delay
import com.gorillaz.puzzlegame.game.utils.Layout.Glow as LG

class AGlow(override val screen: AdvancedScreen): AdvancedGroup() {

    companion object {
        const val GLOW_ITEM_COUNT = 3
    }

    private val glowItemImageList = List(GLOW_ITEM_COUNT) { Image(gdxGame.assetsAll.btn_def) }//screen.game.carnavalCatAssets.glow) }

    var listIndexWin: List<Int>? = null

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
        listIndexWin?.onEach { winIndex ->
            glowItemImageList[winIndex].animShow(time)
            //screen.game.soundUtil.apply { play(laser) }
            delay(timeBetween.toMS)
        }
        continuation.complete(true)
    }.await()

    suspend fun hide(time: Float = 0f, timeBetween: Float = 0f) = CompletableDeferred<Boolean>().also { continuation ->
        listIndexWin?.onEach { winIndex ->
            glowItemImageList[winIndex].animHide(time)
            delay(timeBetween.toMS)
        }
        listIndexWin = null
        continuation.complete(true)
    }.await()

}