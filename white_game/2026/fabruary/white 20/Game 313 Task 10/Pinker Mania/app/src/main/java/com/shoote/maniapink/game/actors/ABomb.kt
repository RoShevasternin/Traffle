package com.shoote.maniapink.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.shoote.maniapink.game.utils.SizeScaler
import com.shoote.maniapink.game.utils.advanced.AdvancedGroup
import com.shoote.maniapink.game.utils.advanced.AdvancedScreen

class ABomb(override val screen: AdvancedScreen): AdvancedGroup() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, 141f)

    private val imgBomb = Image(screen.game.all.bomb)

    override fun addActorsOnGroup() {
        addActor(imgBomb)
        imgBomb.setBoundsScaled(2f, 0f, 136f, 175f)
    }

}