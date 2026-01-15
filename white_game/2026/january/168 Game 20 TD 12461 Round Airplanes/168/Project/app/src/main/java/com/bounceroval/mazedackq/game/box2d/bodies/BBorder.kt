package com.bounceroval.mazedackq.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.bounceroval.mazedackq.game.box2d.AbstractBody
import com.bounceroval.mazedackq.game.utils.advanced.AdvancedBox2dScreen

class BBorder(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "border"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.StaticBody
    }
    override val fixtureDef = FixtureDef().apply {
        restitution = 0.7f
    }

}