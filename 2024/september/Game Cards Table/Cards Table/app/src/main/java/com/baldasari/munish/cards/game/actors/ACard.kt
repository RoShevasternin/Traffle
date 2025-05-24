package com.baldasari.munish.cards.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.baldasari.munish.cards.R
import com.baldasari.munish.cards.appContext
import com.baldasari.munish.cards.game.utils.advanced.AdvancedGroup
import com.baldasari.munish.cards.game.utils.advanced.AdvancedScreen
import com.baldasari.munish.cards.game.utils.font.FontParameter

class ACard(
    override val screen: AdvancedScreen,
    val type: Static.Type,
    val lsX   : LabelStyle,
    val lsName: LabelStyle,
    val lsDesc: LabelStyle,
) : AdvancedGroup() {

    private val randomIndex = (0..22).random()
    private val randomValue get() = (1..10).random()

    val valueX = (1..10).random()

    private val imgCard   = Image(screen.game.all.cardList[type.ordinal])
    private val imgItem   = Image(screen.game.all.itemList[randomIndex])
    private val lblX      = Label("X$valueX", lsX)
    private val lblName   = Label(appContext.resources.getStringArray(R.array.names)[randomIndex], lsName)
    private val lblDesc   = Label("The potion gives $randomValue additional points of strength for $randomValue turns", lsDesc)

    init {
        setSize(292f, 475f)
    }

    override fun addActorsOnGroup() {
        setOrigin(Align.center)
        addAndFillActor(imgCard)
        addImgItem()
        addLbls()
    }

    private fun addImgItem() {
        addActor(imgItem)
        imgItem.setBounds(25f,152f,242f,242f)
    }

    private fun addLbls() {
        addActors(lblX,lblName,lblDesc)
        lblX.apply {
            setAlignment(Align.right)
            setBounds(195f,423f,29f,18f)
        }
        lblName.apply {
            setAlignment(Align.center)
            setBounds(41f,120f,209f,18f)
        }
        lblDesc.apply {
            setAlignment(Align.center)
            wrap = true
            setBounds(16f,70f,259f,34f)
        }
    }

    object Static {
        enum class Type {
            Green, Blue, Red, Yellow
        }
    }

}