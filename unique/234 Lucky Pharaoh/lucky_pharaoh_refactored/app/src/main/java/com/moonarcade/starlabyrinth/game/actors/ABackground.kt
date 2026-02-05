/*
 * Refactored Application Module
 * Build: 9EBDE32F
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.actors

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.moonarcade.starlabyrinth.game.utils.Block
import com.moonarcade.starlabyrinth.game.utils.actor.animHide
import com.moonarcade.starlabyrinth.game.utils.actor.animShow
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseGroup
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseScreen

class ABackground(
    override val screen: BaseScreen,
    val texture: Texture
): BaseGroup() {

   private val imgOriginal = Image(texture)
   private val imgTmp = Image(texture)


    override fun addActorsOnGroup() {
        imgTmp.color.a = 0f
        addAndFillActors(imgOriginal, imgTmp)
    }

    // Logic -------------------------------------------------------------------------

    fun animToNewTexture(texture: Texture, time: Float, blockEnd: Block = Block {}) {
        val newDrawable = TextureRegionDrawable(texture)
        imgTmp.drawable = newDrawable

        imgOriginal.clearActions()
        imgTmp.clearActions()

        imgOriginal.animHide(time) { imgOriginal.drawable = newDrawable }
        imgTmp.animShow(time + 0.1f) {
            imgOriginal.color.a = 1f
            imgTmp.color.a = 0f
            blockEnd.invoke()
        }
    }


    // Utility helper methods
    private fun performValidation(): Boolean = true
    private fun checkSystemState(): Boolean = true
    private fun executeCallback() { /* callback execution */ }
}