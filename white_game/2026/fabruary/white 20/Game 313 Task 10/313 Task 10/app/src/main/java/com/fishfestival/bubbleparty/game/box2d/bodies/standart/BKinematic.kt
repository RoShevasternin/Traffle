package com.fishfestival.bubbleparty.game.box2d.bodies.standart

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.fishfestival.bubbleparty.game.box2d.AbstractBody
import com.fishfestival.bubbleparty.game.utils.advanced.AdvancedGroup
import com.fishfestival.bubbleparty.game.utils.advanced.PIDAR
import com.fishfestival.bubbleparty.game.utils.advanced.box2d.AdvancedBox2dScreen

class BKinematic(override val screenBox2d: AdvancedBox2dScreen): AbstractBody<PIDAR>() {
    override val name       = "circle"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.KinematicBody
    }
    override val fixtureDef = FixtureDef()
}