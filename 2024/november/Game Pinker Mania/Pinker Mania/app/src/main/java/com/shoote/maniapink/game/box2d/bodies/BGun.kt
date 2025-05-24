package com.shoote.maniapink.game.box2d.bodies

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.shoote.maniapink.game.actors.AGun
import com.shoote.maniapink.game.box2d.AbstractBody
import com.shoote.maniapink.game.utils.advanced.AdvancedBox2dScreen

class BGun(override val screenBox2d: AdvancedBox2dScreen): AbstractBody<AGun>() {
    override val name       = "gun"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
    }
    override val fixtureDef = FixtureDef().apply {
        density = 1f
    }

    override var actor: AGun? = AGun(screenBox2d)

    // Logic --------------------------------------------------------------

    fun preparation(regionBall: TextureRegion) {
         actor?.updateBall(regionBall)
    }

}