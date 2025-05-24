package com.shoote.maniapink.game.actors

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.shoote.maniapink.game.utils.SizeScaler
import com.shoote.maniapink.game.utils.advanced.AdvancedGroup
import com.shoote.maniapink.game.utils.advanced.AdvancedScreen

class AGun(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, 267f)

    private val imgGun  = Image(screen.game.all.gun)
    private val imgBall = Image()

    override fun addActorsOnGroup() {
        addAndFillActor(imgGun)

        addActor(imgBall)
        imgBall.setBoundsScaled(70.5f, 16.5f, 123f, 123f)
    }

    fun updateBall(region: TextureRegion) {
        imgBall.drawable = TextureRegionDrawable(region)
    }

}