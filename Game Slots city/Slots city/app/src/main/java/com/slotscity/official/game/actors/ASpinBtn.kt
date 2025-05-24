package com.slotscity.official.game.actors

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.utils.Align
import com.slotscity.official.game.actors.button.AButton
import com.slotscity.official.game.actors.checkbox.ACheckBox
import com.slotscity.official.game.utils.advanced.AdvancedGroup
import com.slotscity.official.game.utils.advanced.AdvancedScreen

class ASpinBtn(override val screen: AdvancedScreen): AdvancedGroup() {

    private val assets = screen.game.allAssets

    // Actor
    private val spinCBox = ACheckBox(screen, ACheckBox.Static.Type.SPIN_BTN).apply { touchable = Touchable.disabled }
    private val spinBtn  = AButton(screen, AButton.Static.Type.SPIN)

    // Field
    var spinBlock: () -> Unit = {}

    override fun addActorsOnGroup() {
        addAndFillActor(spinCBox)
        addSpinBtn()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun addSpinBtn() {
        addActor(spinBtn)
        spinBtn.apply {
            setBounds(40f, 37f, 202f, 163f)
            setOrigin(Align.center)

            setOnClickListener { handlerSpin() }
        }
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun AButton.handlerSpin() {
        screen.game.soundUtil.apply { play(start) }

        disable()
        spinBlock()
        spinCBox.check()
        addAction(getSpinAction())
    }

    private fun getSpinAction() = Actions.forever(Actions.rotateBy(-360f, 1f, Interpolation.smoother))

    fun stopSpin() {
        spinBtn.apply {
            clearActions()
            addAction(Actions.rotateTo(0f, 0.25f))
            enable()
        }
        spinCBox.uncheck()
    }

}