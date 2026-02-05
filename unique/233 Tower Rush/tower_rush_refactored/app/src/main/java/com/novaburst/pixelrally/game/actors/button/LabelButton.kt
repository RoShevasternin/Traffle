/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.actors.button

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.novaburst.pixelrally.game.utils.actor.disable
import com.novaburst.pixelrally.game.utils.advanced.DisplayScreen

open class LabelButton(
    override val screen: DisplayScreen,
    text: String,
    labelStyle: Label.LabelStyle,
    type: InteractiveButton.Type = InteractiveButton.Type.Default,
) : InteractiveButton(screen, type) {

    val label = Label(text, labelStyle)

    override fun addActorsOnGroup() {
        super.addActorsOnGroup()

        addAndFillActor(label)

        label.disable()
        label.setAlignment(Align.center)
    }

}