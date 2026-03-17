package com.quantumplay.orbitcrasher.game.actors.button

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.quantumplay.orbitcrasher.game.utils.actor.disable
import com.quantumplay.orbitcrasher.game.utils.advanced.AdvancedScreen

open class AImageButton(
    override val screen: AdvancedScreen,
    region: TextureRegion,
    type: Type = Type.Default,
) : AButton(screen, type) {

    val image = Image(region)

    override fun addActorsOnGroup() {
        super.addActorsOnGroup()

        addAlignActor(image, AlignmentHorizontal.CENTER, AlignmentVertical.CENTER)

        image.disable()
    }

}