package com.portalend.fruitomaner.game.box2d.bodies

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.portalend.fruitomaner.game.actors.ARadialGradientGroup
import com.portalend.fruitomaner.game.box2d.AbstractBody
import com.portalend.fruitomaner.game.utils.advanced.AdvancedBox2dScreen
import com.portalend.fruitomaner.game.utils.advanced.AdvancedGroup

class BWheel(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "circle"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
    }
    override val fixtureDef = FixtureDef().apply {
        density     = 1f
        friction    = 1f
        restitution = 1f
    }

    override var actor: AdvancedGroup? = ARadialGradientGroup(screenBox2d, Color.BLACK, Color.WHITE, 3f, ARadialGradientGroup.Static.Direction.END_START)

}