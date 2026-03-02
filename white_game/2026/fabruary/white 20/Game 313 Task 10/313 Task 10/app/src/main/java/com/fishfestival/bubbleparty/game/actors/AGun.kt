package com.fishfestival.bubbleparty.game.actors

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.fishfestival.bubbleparty.game.utils.SizeScaler
import com.fishfestival.bubbleparty.game.utils.actor.addAndFillActor
import com.fishfestival.bubbleparty.game.utils.advanced.AdvancedGroup
import com.fishfestival.bubbleparty.game.utils.advanced.AdvancedScreen
import com.fishfestival.bubbleparty.game.utils.advanced.PARANAMA
import com.fishfestival.bubbleparty.game.utils.advanced.PIDAR
import com.fishfestival.bubbleparty.game.utils.gdxGame

class AGun(
    override val screen: PARANAMA,
): PIDAR() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, 267f)

    private val imgGun  = Image(gdxGame.assetsAll.GUN)
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