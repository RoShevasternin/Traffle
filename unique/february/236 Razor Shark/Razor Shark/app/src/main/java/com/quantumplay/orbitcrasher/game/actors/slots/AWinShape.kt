package com.quantumplay.orbitcrasher.game.actors.slots

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.quantumplay.orbitcrasher.game.utils.Acts
import com.quantumplay.orbitcrasher.game.utils.actor.PosSize
import com.quantumplay.orbitcrasher.game.utils.actor.animHide
import com.quantumplay.orbitcrasher.game.utils.actor.animShow
import com.quantumplay.orbitcrasher.game.utils.actor.setBounds
import com.quantumplay.orbitcrasher.game.utils.advanced.AdvancedGroup
import com.quantumplay.orbitcrasher.game.utils.advanced.AdvancedScreen
import com.quantumplay.orbitcrasher.game.utils.gdxGame

class AWinShape(override val screen: AdvancedScreen): AdvancedGroup() {

    private val listPosSiz = listOf(
        PosSize(23f, 541f - 65f, 1091f, 54f),
        PosSize(23f, 383f - 65f, 1091f, 54f),
        PosSize(23f, 219f - 65f, 1091f, 54f),
        PosSize(49f, 172f - 65f, 1048f, 570f),
        PosSize(41f, 124f - 65f, 1048f, 570f),
        PosSize(17f, 164f - 65f, 1082f, 499f),
        PosSize(10f, 164f - 65f, 1082f, 499f),
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
                imgWinShape.setBounds(listPosSiz[0])
                gdxGame.assetsAll.SHAPE_1
            }
            ResultShape5x3.Line_Center -> {
                imgWinShape.setBounds(listPosSiz[1])
                gdxGame.assetsAll.SHAPE_1
            }
            ResultShape5x3.Line_Bottom -> {
                imgWinShape.setBounds(listPosSiz[2])
                gdxGame.assetsAll.SHAPE_1
            }
            ResultShape5x3.V_Up        -> {
                imgWinShape.setBounds(listPosSiz[3])
                gdxGame.assetsAll.SHAPE_2
            }
            ResultShape5x3.V_Down      -> {
                imgWinShape.setBounds(listPosSiz[4])
                gdxGame.assetsAll.SHAPE_3
            }
            ResultShape5x3.Z_Start_End -> {
                imgWinShape.setBounds(listPosSiz[5])
                gdxGame.assetsAll.SHAPE_4
            }
            ResultShape5x3.Z_End_Start -> {
                imgWinShape.setBounds(listPosSiz[6])
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