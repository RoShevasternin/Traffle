package com.fishfestival.bubbleparty.game.actors

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.fishfestival.bubbleparty.game.utils.SizeScaler
import com.fishfestival.bubbleparty.game.utils.advanced.AdvancedGroup
import com.fishfestival.bubbleparty.game.utils.advanced.AdvancedScreen
import com.fishfestival.bubbleparty.game.utils.advanced.PARANAMA
import com.fishfestival.bubbleparty.game.utils.advanced.PIDAR

class ABall(
    override val screen: PARANAMA,
    val region: TextureRegion
): PIDAR() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, 141f)

    private val image = Image(region)

    override fun addActorsOnGroup() {
        addActor(image)
        image.setBoundsScaled(8f, 8f, 124f, 124f)
    }

}