package com.shoote.maniapink.game.box2d.bodies.standart

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.utils.Null
import com.shoote.maniapink.game.box2d.AbstractBody
import com.shoote.maniapink.game.utils.advanced.AdvancedBox2dScreen
import com.shoote.maniapink.game.utils.advanced.AdvancedGroup

class BDynamic(override val screenBox2d: AdvancedBox2dScreen): AbstractBody<AdvancedGroup>() {
    override val name       = "circle"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
    }
    override val fixtureDef = FixtureDef().apply {
        density = 1f
    }
}