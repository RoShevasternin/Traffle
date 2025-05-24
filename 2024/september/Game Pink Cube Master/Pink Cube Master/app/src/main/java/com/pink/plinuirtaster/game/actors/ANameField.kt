package com.pink.plinuirtaster.game.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.TextInputListener
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.pink.plinuirtaster.game.actors.checkbox.ACheckBox
import com.pink.plinuirtaster.game.utils.actor.setOnClickListener
import com.pink.plinuirtaster.game.utils.advanced.AdvancedGroup
import com.pink.plinuirtaster.game.utils.advanced.AdvancedScreen

class ANameField(
    override val screen: AdvancedScreen,
    number: Int,
    ls65  : LabelStyle,
    ls74  : LabelStyle,
) : AdvancedGroup() {

    private val boxGrayRed = ACheckBox(screen, ACheckBox.Static.Type.GRAY_RED)
    private val lblNumber  = Label("${number}.", ls74)
    private val lblName    = Label("", ls65)

    var nameUser: String = ""

    init {
        setOnClickListener(screen.game.soundUtil) { clickHandler() }
    }

    override fun addActorsOnGroup() {
        addActors(boxGrayRed, lblNumber, lblName)

        boxGrayRed.apply {
            disable()
            setBounds(76f, 0f, 860f, 140f)
        }
        lblNumber.apply {
            setAlignment(Align.right)
            setBounds(0f, 26f, 65f, 88f)
        }
        lblName.apply {
            setAlignment(Align.center)
            setBounds(312f, 32f, 388f, 77f)
        }
    }

    // Logic --------------------------------------------------------------------

    fun showError() {
        boxGrayRed.apply {
            check()
            clearActions()
            addAction(Actions.sequence(
                Actions.delay(1.1f),
                Actions.run { uncheck() }
            ))
        }
    }

    private fun clickHandler() {
        Gdx.input.getTextInput(object : TextInputListener {
            override fun input(text: String?) {
                nameUser = text?.take(15) ?: ""
                lblName.setText(nameUser)
            }

            override fun canceled() {}
        }, "Enter your Name", "", "Name")
    }

}