package com.mythquiz.greekgods.game.actors

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.mythquiz.greekgods.game.utils.advanced.AdvancedGroup
import com.mythquiz.greekgods.game.utils.advanced.AdvancedScreen

class AGameCard(
    override val screen: AdvancedScreen,
    val textureCard: Texture,
): AdvancedGroup() {

    val image = Image(textureCard)

    override fun addActorsOnGroup() {
        addAndFillActor(image)
    }

}