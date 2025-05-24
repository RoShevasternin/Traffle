package com.pinlq.esst.bloo.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.pinlq.esst.bloo.game.box2d.AbstractBody
import com.pinlq.esst.bloo.game.utils.advanced.AdvancedBox2dScreen

class BBorder(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "border"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.StaticBody
    }
    override val fixtureDef = FixtureDef().apply {
        restitution = 0.2f
    }

}