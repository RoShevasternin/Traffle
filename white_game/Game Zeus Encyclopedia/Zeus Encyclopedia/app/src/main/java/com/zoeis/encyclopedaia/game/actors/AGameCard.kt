package com.zoeis.encyclopedaia.game.actors

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.zoeis.encyclopedaia.game.utils.advanced.AdvancedGroup
import com.zoeis.encyclopedaia.game.utils.advanced.AdvancedScreen

class AGameCard(
    override val screen: AdvancedScreen,
    val textureCard: Texture,
): AdvancedGroup() {

    val image = Image(textureCard)

    override fun addActorsOnGroup() {
        addAndFillActor(image)
    }

}