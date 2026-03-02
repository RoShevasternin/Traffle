package com.shoote.maniapink.game.actors

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.shoote.maniapink.game.utils.SizeScaler
import com.shoote.maniapink.game.utils.advanced.AdvancedGroup
import com.shoote.maniapink.game.utils.advanced.AdvancedScreen

class ABall(
    override val screen: AdvancedScreen,
    val region: TextureRegion
): AdvancedGroup() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, 141f)

    private val image = Image(region)

    override fun addActorsOnGroup() {
        addActor(image)
        image.setBoundsScaled(8f, 8f, 124f, 124f)
    }

}