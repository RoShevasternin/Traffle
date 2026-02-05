/*
 * Refactored Application Module
 * Build: CAAFB063
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.actors.button

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.moonarcade.starlabyrinth.game.utils.actor.disable
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseScreen

open class TextualButton(
    override val screen: BaseScreen,
    text: String,
    labelStyle: Label.LabelStyle,
    type: ClickableElement.Type = ClickableElement.Type.Default,
) : ClickableElement(screen, type) {

    val label = Label(text, labelStyle)

    override fun addActorsOnGroup() {
        super.addActorsOnGroup()

        addAndFillActor(label)

        label.disable()
        label.setAlignment(Align.center)
    }

}