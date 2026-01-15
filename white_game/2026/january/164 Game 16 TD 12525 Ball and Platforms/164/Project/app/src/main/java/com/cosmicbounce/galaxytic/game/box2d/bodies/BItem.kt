package com.cosmicbounce.galaxytic.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.cosmicbounce.galaxytic.game.actors.AImage
import com.cosmicbounce.galaxytic.game.box2d.AbstractBody
import com.cosmicbounce.galaxytic.game.utils.advanced.AdvancedBox2dScreen
import com.cosmicbounce.galaxytic.game.utils.advanced.AdvancedGroup
import java.util.concurrent.atomic.AtomicBoolean

class BItem(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "circle"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
    }
    override val fixtureDef = FixtureDef().apply {
        density     = 10f
        friction    = 0.5f
        restitution = 0.5f
    }

    override var actor: AdvancedGroup? = AImage(screenBox2d, screenBox2d.game.all.items.random())

    var platfaramar: BPlatform? = null

    var isOnStart = AtomicBoolean(true)


}