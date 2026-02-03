package com.gorillaz.puzzlegame.game.actors.button

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.gorillaz.puzzlegame.game.utils.actor.disable
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedScreen

open class ATextButton(
    override val screen: AdvancedScreen,
    text: String,
    labelStyle: Label.LabelStyle,
    type: AButton.Type = AButton.Type.Default,
) : AButton(screen, type) {

    val label = Label(text, labelStyle)

    override fun addActorsOnGroup() {
        super.addActorsOnGroup()

        addAndFillActor(label)

        label.disable()
        label.setAlignment(Align.center)
    }

}