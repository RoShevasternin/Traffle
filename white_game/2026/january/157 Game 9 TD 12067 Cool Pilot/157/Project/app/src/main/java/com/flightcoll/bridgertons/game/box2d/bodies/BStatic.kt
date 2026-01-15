package com.flightcoll.bridgertons.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.flightcoll.bridgertons.game.actors.AImage
import com.flightcoll.bridgertons.game.box2d.AbstractBody
import com.flightcoll.bridgertons.game.utils.advanced.AdvancedBox2dScreen
import com.flightcoll.bridgertons.game.utils.advanced.AdvancedGroup

class BStatic(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "circle"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.StaticBody
    }
    override val fixtureDef = FixtureDef()
}