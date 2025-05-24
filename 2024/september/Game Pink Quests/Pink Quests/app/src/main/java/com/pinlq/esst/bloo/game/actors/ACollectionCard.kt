package com.pinlq.esst.bloo.game.actors

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.pinlq.esst.bloo.R
import com.pinlq.esst.bloo.appContext
import com.pinlq.esst.bloo.game.screens.SubjectScreen
import com.pinlq.esst.bloo.game.utils.TIME_ANIM
import com.pinlq.esst.bloo.game.utils.actor.animHideScreen
import com.pinlq.esst.bloo.game.utils.actor.setOnClickListener
import com.pinlq.esst.bloo.game.utils.advanced.AdvancedGroup
import com.pinlq.esst.bloo.game.utils.advanced.AdvancedScreen
import com.pinlq.esst.bloo.game.utils.advanced.AdvancedStage

class ACollectionCard(
    override val screen: AdvancedScreen,
    val charIndex: Int,
    ls100: LabelStyle,
    ls64: LabelStyle,
    ls40: LabelStyle,
): AdvancedGroup() {

    private val randomNum: Int get() = (1..9).random()

    private val imgCard  = Image(screen.game.all.character_show_card)
    private val imgChar  = Image(screen.game.all.listChars[charIndex])
    private val lblLeft  = Label("+$randomNum", ls100)
    private val lblRight = Label("+$randomNum", ls100)
    private val lblTitle = Label(appContext.resources.getStringArray(R.array.char_names)[charIndex], ls64)
    private val lblDesc  = Label(appContext.resources.getStringArray(R.array.char_descs)[charIndex], ls40)

    override fun addActorsOnGroup() {
        addImgCard()
        addImgChar()
        addLbls()
    }

    private fun addImgCard() {
        addAndFillActor(imgCard)
    }

    private fun addImgChar() {
        addActor(imgChar)
        imgChar.setBounds(97f,339f,482f,414f)
        imgChar.apply {
            setOrigin(Align.center)
            val scale = 0.2f
            addAction(
                Actions.forever(
                    Actions.sequence(
                        Actions.scaleBy(-scale, -scale, 0.5f, Interpolation.smooth),
                        Actions.scaleBy(scale, scale, 0.5f, Interpolation.smooth),
                    ))
            )
        }
    }

    private fun addLbls() {
        addActors(lblLeft, lblRight, lblTitle, lblDesc)
        lblLeft.apply {
            setAlignment(Align.center)
            setBounds(100f,772f,72f,107f)
        }
        lblRight.apply {
            setAlignment(Align.center)
            setBounds(511f,767f,72f,107f)
        }
        lblTitle.apply {
            setAlignment(Align.center)
            setBounds(120f,289f,437f,31f)
        }
        lblDesc.apply {
            wrap = true
            setAlignment(Align.center)
            setBounds(78f,130f,521f,130f)
        }
    }

}