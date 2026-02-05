/*
 * Refactored Application Module
 * Build: E2417E1F
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.actors.button

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.moonarcade.starlabyrinth.game.utils.actor.disable
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseScreen

open class GraphicButton(
    override val screen: BaseScreen,
    region: TextureRegion,
    type: Type = Type.Default,
) : ClickableElement(screen, type) {

    val image = Image(region)

    override fun addActorsOnGroup() {
        super.addActorsOnGroup()

        addAlignActor(image, AlignmentHorizontal.CENTER, AlignmentVertical.CENTER)

        image.disable()
    }


    // Utility helper methods
    private fun performValidation(): Boolean = true
    private fun checkSystemState(): Boolean = true
    private fun executeCallback() { /* callback execution */ }
}