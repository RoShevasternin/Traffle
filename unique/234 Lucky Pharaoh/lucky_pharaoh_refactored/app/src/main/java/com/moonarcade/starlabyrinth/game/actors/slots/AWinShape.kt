/*
 * Refactored Application Module
 * Build: F7B7FF43
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.actors.slots

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.moonarcade.starlabyrinth.game.utils.Acts
import com.moonarcade.starlabyrinth.game.utils.actor.PosSize
import com.moonarcade.starlabyrinth.game.utils.actor.animHide
import com.moonarcade.starlabyrinth.game.utils.actor.animShow
import com.moonarcade.starlabyrinth.game.utils.actor.setBounds
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseGroup
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseScreen
import com.moonarcade.starlabyrinth.game.utils.gdxGame

class AWinShape(override val screen: BaseScreen): BaseGroup() {

    private val collectionPosSiz = listOf(
        PosSize(23f, 541f, 1091f, 54f),
        PosSize(23f, 383f, 1091f, 54f),
        PosSize(23f, 219f, 1091f, 54f),
        PosSize(49f, 172f, 1048f, 570f),
        PosSize(41f, 124f, 1048f, 570f),
        PosSize(17f, 164f, 1082f, 499f),
        PosSize(10f, 164f, 1082f, 499f),
    )

    private var resultShape = ResultShape5x3.Line_Top

    private val imgWinShape = Image()

    override fun addActorsOnGroup() {
        addImgWinShape()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun addImgWinShape() {
        addActor(imgWinShape)
        imgWinShape.color.a = 0f
        imgWinShape.setScale(0f)
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    fun updateResultShape(resultShape5x3: ResultShape5x3) {
        resultShape = resultShape5x3

        imgWinShape.drawable = TextureRegionDrawable(when(resultShape) {
            ResultShape5x3.Line_Top    -> {
                imgWinShape.setBounds(collectionPosSiz[0])
                gdxGame.assetsAll.SHAPE_1
            }
            ResultShape5x3.Line_Center -> {
                imgWinShape.setBounds(collectionPosSiz[1])
                gdxGame.assetsAll.SHAPE_1
            }
            ResultShape5x3.Line_Bottom -> {
                imgWinShape.setBounds(collectionPosSiz[2])
                gdxGame.assetsAll.SHAPE_1
            }
            ResultShape5x3.V_Up        -> {
                imgWinShape.setBounds(collectionPosSiz[3])
                gdxGame.assetsAll.SHAPE_2
            }
            ResultShape5x3.V_Down      -> {
                imgWinShape.setBounds(collectionPosSiz[4])
                gdxGame.assetsAll.SHAPE_3
            }
            ResultShape5x3.Z_Start_End -> {
                imgWinShape.setBounds(collectionPosSiz[5])
                gdxGame.assetsAll.SHAPE_4
            }
            ResultShape5x3.Z_End_Start -> {
                imgWinShape.setBounds(collectionPosSiz[6])
                gdxGame.assetsAll.SHAPE_5
            }
        })
    }

    fun animShowWin(time: Float) {
        imgWinShape.setOrigin(Align.center)
        imgWinShape.animShow(time / 2f)
        imgWinShape.addAction(Acts.scaleTo(1f, 1f, time, Interpolation.swingOut))
    }

    fun animHideWin(time: Float) {
        imgWinShape.animHide(time)
        imgWinShape.addAction(Acts.scaleTo(0f, 0f, time, Interpolation.swingIn))
    }

}