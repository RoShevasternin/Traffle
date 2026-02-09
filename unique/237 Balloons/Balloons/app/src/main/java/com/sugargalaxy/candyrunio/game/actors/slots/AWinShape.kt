package com.sugargalaxy.candyrunio.game.actors.slots

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.sugargalaxy.candyrunio.game.utils.Acts
import com.sugargalaxy.candyrunio.game.utils.actor.PosSize
import com.sugargalaxy.candyrunio.game.utils.actor.animHide
import com.sugargalaxy.candyrunio.game.utils.actor.animShow
import com.sugargalaxy.candyrunio.game.utils.actor.setBounds
import com.sugargalaxy.candyrunio.game.utils.advanced.AdvancedGroup
import com.sugargalaxy.candyrunio.game.utils.advanced.AdvancedScreen
import com.sugargalaxy.candyrunio.game.utils.gdxGame

class AWinShape(override val screen: AdvancedScreen): AdvancedGroup() {

    private val listPosSiz = listOf(
        PosSize(44f, 639f, 1029f, 152f),
        PosSize(44f, 487f, 1029f, 152f),
        PosSize(44f, 327f, 1029f, 152f),
        PosSize(35f, 289f, 1048f, 570f),
        PosSize(35f, 226f, 1048f, 570f),
        PosSize(38f, 280f, 1082f, 499f),
        PosSize(31f, 295f, 1082f, 499f),
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