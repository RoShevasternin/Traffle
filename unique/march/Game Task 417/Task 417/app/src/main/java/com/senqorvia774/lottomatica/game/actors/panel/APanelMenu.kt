package com.senqorvia774.lottomatica.game.actors.panel

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.senqorvia774.lottomatica.game.utils.advanced.AdvancedGroup
import com.senqorvia774.lottomatica.game.utils.advanced.AdvancedScreen
import com.senqorvia774.lottomatica.game.utils.gdxGame

class APanelMenu(override val screen: AdvancedScreen): AdvancedGroup() {

    companion object {
        var SELECTED_INDEX = 1 // центр
            private set
    }

    private val listRegion = gdxGame.assetsAll.listMenuItem

    private val listMenuItemImg = List(listRegion.size) { Image(listRegion[it]) }

    override fun addActorsOnGroup() {
        addListMenuItemImg()
        updatePositions(animated = false)
    }

    // ------------------------------------------------------------------------
    // Positions
    // ------------------------------------------------------------------------

    private data class ItemState(
        val x: Float,
        val y: Float,
        val w: Float,
        val h: Float,
        val alpha: Float
    )

    private val leftState = ItemState(12f, 95f, 530f, 337f, 0.5f)
    private val centerState = ItemState(583f, 0f, 828f, 526f, 1f)
    private val rightState = ItemState(1454f, 95f, 530f, 337f, 0.5f)

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun addListMenuItemImg() {
        listMenuItemImg.forEachIndexed { index, img ->
            addActor(img)
            img.setOrigin(Align.center)

            img.addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    gdxGame.soundUtil.apply { play(click) }
                    if (index != SELECTED_INDEX) {
                        SELECTED_INDEX = index
                        updatePositions(animated = true)
                    }
                }
            })
        }
    }

    // ------------------------------------------------------------------------
    // Update
    // ------------------------------------------------------------------------

    private fun updatePositions(animated: Boolean) {
        listMenuItemImg.forEachIndexed { index, actor ->

            val state = when (index) {
                SELECTED_INDEX -> centerState
                (SELECTED_INDEX - 1 + listMenuItemImg.size) % listMenuItemImg.size -> leftState
                (SELECTED_INDEX + 1) % listMenuItemImg.size -> rightState
                else -> centerState
            }

            if (animated) {
                actor.addAction(Actions.parallel(
                    Actions.moveTo(state.x, state.y, 0.3f, Interpolation.swingOut),
                    Actions.sizeTo(state.w, state.h, 0.3f),
                    Actions.alpha(state.alpha, 0.3f)
                ))
            } else {
                actor.setBounds(state.x, state.y, state.w, state.h)
                actor.color.a = state.alpha
            }

            // центр поверх
            if (index == SELECTED_INDEX) {
                actor.toFront()
            }
        }
    }

}