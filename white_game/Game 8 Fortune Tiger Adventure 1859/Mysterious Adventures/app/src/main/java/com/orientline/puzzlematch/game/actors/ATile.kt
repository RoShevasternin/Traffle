package com.orientline.puzzlematch.game.actors

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.orientline.puzzlematch.game.utils.actor.animHide
import com.orientline.puzzlematch.game.utils.actor.animShow
import com.orientline.puzzlematch.game.utils.actor.disable
import com.orientline.puzzlematch.game.utils.actor.enable
import com.orientline.puzzlematch.game.utils.advanced.AdvancedGroup
import com.orientline.puzzlematch.game.utils.advanced.AdvancedScreen

class ATile(
    override val screen: AdvancedScreen,
    val id: Int,
    val region: TextureRegion
): AdvancedGroup() {

    // Actor
    private val img = Image(region)

    // Field
    private val timeAnim = 0.25f

    override fun addActorsOnGroup() {
        color.a = 0f
        addAndFillActor(img)
    }

    fun animOpen() {
        disable()
        animShow(timeAnim)
    }

    fun animClose() {
        animHide(timeAnim) { enable() }
    }

}