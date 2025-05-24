package com.swee.ttrio.comb.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.swee.ttrio.comb.game.utils.advanced.AdvancedGroup
import com.swee.ttrio.comb.game.utils.advanced.AdvancedScreen

class ALabel(
    override val screen: AdvancedScreen,
    text: String, ls: LabelStyle
) : AdvancedGroup() {

    val label = Label(text, ls)

    override fun addActorsOnGroup() {
        addAndFillActor(label)
    }

}