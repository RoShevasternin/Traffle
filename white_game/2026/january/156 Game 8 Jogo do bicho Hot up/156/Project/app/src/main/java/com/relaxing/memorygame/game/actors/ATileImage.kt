package com.relaxing.memorygame.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.relaxing.memorygame.game.utils.actor.animHide
import com.relaxing.memorygame.game.utils.actor.animShow
import com.relaxing.memorygame.game.utils.actor.disable
import com.relaxing.memorygame.game.utils.actor.enable
import com.relaxing.memorygame.game.utils.actor.setOnClickListener
import com.relaxing.memorygame.game.utils.advanced.AdvancedGroup
import com.relaxing.memorygame.game.utils.advanced.AdvancedScreen

class ATileImage(override val screen: AdvancedScreen, var tile: ATileGroup.Obj.Tile): AdvancedGroup() {

    private val TIME_ANIM = 0.25f
    private val default = screen.game.gameAssets.TILE

    private val img = Image(default)

    override fun addActorsOnGroup() {
        addAndFillActor(img)
    }

    fun animShowTile() {
        disable()
        animHide(TIME_ANIM) {
            img.drawable = TextureRegionDrawable(tile.region)
            animShow(TIME_ANIM)
        }
    }

    fun animDefault() {
        animHide(TIME_ANIM) {
            img.drawable = TextureRegionDrawable(default)
            animShow(TIME_ANIM) {
                enable()
            }
        }
    }

}