package com.candybostony.bonceria.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.candybostony.bonceria.game.box2d.AbstractBody
import com.candybostony.bonceria.game.box2d.BodyId
import com.candybostony.bonceria.game.utils.advanced.box2d.AdvancedBox2dScreen

class BGreener(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "circle"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.StaticBody
    }
    override val fixtureDef = FixtureDef().apply {
        restitution = 0.85f
    }

    override var id: String = BodyId.BORDER
    override val collisionList = mutableListOf(BodyId.BALL)

}