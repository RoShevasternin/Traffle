package com.fruithaven.juicydashx.game.actors

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.fruithaven.juicydashx.game.utils.Block
import com.fruithaven.juicydashx.game.utils.actor.animHide
import com.fruithaven.juicydashx.game.utils.actor.animShow
import com.fruithaven.juicydashx.game.utils.advanced.AdvancedGroup
import com.fruithaven.juicydashx.game.utils.advanced.AdvancedScreen

class ABackground(
    override val screen: AdvancedScreen,
    val texture: Texture
): AdvancedGroup() {

   private val imgOriginal = Image(texture)
   private val imgTmp      = Image(texture)


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

}