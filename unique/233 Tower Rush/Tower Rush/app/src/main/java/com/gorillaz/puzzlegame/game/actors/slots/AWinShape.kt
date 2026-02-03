package com.gorillaz.puzzlegame.game.actors.slots

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.gorillaz.puzzlegame.game.utils.Acts
import com.gorillaz.puzzlegame.game.utils.actor.PosSize
import com.gorillaz.puzzlegame.game.utils.actor.animHide
import com.gorillaz.puzzlegame.game.utils.actor.animShow
import com.gorillaz.puzzlegame.game.utils.actor.setBounds
import com.gorillaz.puzzlegame.game.utils.actor.setOrigin
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedGroup
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedScreen
import com.gorillaz.puzzlegame.game.utils.gdxGame

class AWinShape(override val screen: AdvancedScreen): AdvancedGroup() {

    private val listPosSiz = listOf(
        PosSize(-6f, 587f, 1091f, 54f),
        PosSize(-6f, 372f, 1091f, 54f),
        PosSize(-6f, 157f, 1091f, 54f),
        PosSize(16f, 114f, 1048f, 570f),
        PosSize(16f, 114f, 1048f, 570f),
        PosSize(-1f, 150f, 1082f, 499f),
        PosSize(-1f, 150f, 1082f, 499f),
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