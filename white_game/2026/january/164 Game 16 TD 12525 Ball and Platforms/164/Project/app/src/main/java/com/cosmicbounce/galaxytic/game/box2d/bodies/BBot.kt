package com.cosmicbounce.galaxytic.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.cosmicbounce.galaxytic.game.box2d.AbstractBody
import com.cosmicbounce.galaxytic.game.utils.advanced.AdvancedBox2dScreen

class BBot(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "bot"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.StaticBody
    }
    override val fixtureDef = FixtureDef().apply {
        friction    = 0.5f
        restitution = 0.5f
    }

}