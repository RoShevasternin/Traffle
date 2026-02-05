/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.actors.button

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.novaburst.pixelrally.game.utils.actor.disable
import com.novaburst.pixelrally.game.utils.advanced.DisplayScreen

open class IconButton(
    override val screen: DisplayScreen,
    region: TextureRegion,
    type: Type = Type.Default,
) : InteractiveButton(screen, type) {

    val image = Image(region)

    override fun addActorsOnGroup() {
        super.addActorsOnGroup()

        addAlignActor(image, AlignmentHorizontal.CENTER, AlignmentVertical.CENTER)

        image.disable()
    }

}