package com.zentorix.nv.casino.game.actors

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.utils.Align
import com.zentorix.nv.casino.game.actors.button.AButton
import com.zentorix.nv.casino.game.actors.checkbox.ACheckBox
import com.zentorix.nv.casino.game.utils.actor.setOnClickListener
import com.zentorix.nv.casino.game.utils.advanced.AdvancedGroup
import com.zentorix.nv.casino.game.utils.advanced.AdvancedScreen

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
        spinBtn.setBounds(40f, 37f, 202f, 163f)
        spinBtn.setOrigin(Align.center)
        spinBtn.setOnClickListener { spinBtn.handlerSpin() }

    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun AButton.handlerSpin() {
        screen.game.soundUtil.apply { play(start) }

        disable()
        spinBlock()
        spinCBox.check()
        spinBtn.addAction(getSpinAction())
    }

    private fun getSpinAction() = Actions.rotateBy(-360f, 1f, Interpolation.smoother)

    fun stopSpin() {
        spinBtn.apply {
            this.clearActions()
            this.addAction(Actions.rotateTo(0f, 0.25f))
            this.enable()
        }
        spinCBox.uncheck()
    }

}