package com.slotscity.official.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.slotscity.official.game.actors.button.AButton
import com.slotscity.official.game.utils.TIME_ANIM_ALPHA
import com.slotscity.official.game.utils.actor.animHide
import com.slotscity.official.game.utils.actor.disable
import com.slotscity.official.game.utils.actor.setOnClickListener
import com.slotscity.official.game.utils.advanced.AdvancedGroup
import com.slotscity.official.game.utils.advanced.AdvancedScreen
import com.slotscity.official.game.utils.font.FontParameter
import com.slotscity.official.game.utils.runGDX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AInfoPanel(override val screen: AdvancedScreen): AdvancedGroup() {

    private val assets = screen.game.allAssets

    private var infoIndex      = 0
    private val MAX_INFO_INDEX = assets.listInfo.lastIndex

    // Actor
    private val infoImg = Image(assets.listInfo[infoIndex])

    // Field
    var closeBlock: () -> Unit = {}

    override fun addActorsOnGroup() {
        addAndFillActor(Image(screen.drawerUtil.getRegion(Color.BLACK.apply { a = 0.74f })))
        addInfoImg()
        addBtns()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun addInfoImg() {
        addActor(infoImg)
        infoImg.setBounds(283f, 146f, 1356f, 785f)
    }

    private fun addBtns() {
        val leftActor    = Actor()
        val rightActor   = Actor()
        val xCenterActor = Actor()
        val xActor       = Actor()

        addActors(leftActor, rightActor, xCenterActor, xActor)

        leftActor.apply {
            setBounds(310f, 168f, 46f, 52f)
            setOnClickListener(screen.game.soundUtil) {
                if((infoIndex - 1) >= 0) infoIndex -= 1 else infoIndex = MAX_INFO_INDEX
                infoImg.drawable = TextureRegionDrawable(assets.listInfo[infoIndex])
            }
        }
        rightActor.apply {
            setBounds(440f, 168f, 46f, 52f)
            setOnClickListener(screen.game.soundUtil) {
                if((infoIndex + 1) <= MAX_INFO_INDEX) infoIndex += 1 else infoIndex = 0
                infoImg.drawable = TextureRegionDrawable(assets.listInfo[infoIndex])
            }
        }
        xCenterActor.apply {
            setBounds(376f, 174f, 40f, 38f)
            setOnClickListener(screen.game.soundUtil) { handlerClickX() }
        }
        xActor.apply {
            setBounds(1571f, 859f, 40f, 40f)
            setOnClickListener(screen.game.soundUtil) { handlerClickX() }
        }
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun handlerClickX() {
        disable()
        animHide(TIME_ANIM_ALPHA) { closeBlock() }
    }

}