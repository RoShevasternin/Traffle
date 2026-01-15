package com.relaxing.memorygame.game.actors.label

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.relaxing.memorygame.game.utils.advanced.AdvancedGroup
import com.relaxing.memorygame.game.utils.advanced.AdvancedScreen

class ALabel constructor(
    override val screen: AdvancedScreen,
    text      : CharSequence,
    labelStyle: LabelStyle,
): AdvancedGroup() {

    val label = Label(text, labelStyle)


    override fun addActorsOnGroup() {
        addAndFillActor(label)
    }

}