package com.colderonetrains.battlesskates.game.actors.checkbox

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedScreen

class ATextCheckBox(
    override val screen: AdvancedScreen,
    nName       : String,
    lsRegular43 : Label.LabelStyle,
): ACheckBox(screen, Type.CustomTrickItem) {

    override fun getPrefWidth(): Float { return width }
    override fun getPrefHeight(): Float { return height }

    private val lblTitle = Label(nName, lsRegular43)

    override fun addActorsOnGroup() {
        super.addActorsOnGroup()
        addAndFillActor(lblTitle)
        lblTitle.setAlignment(Align.center)
    }

}