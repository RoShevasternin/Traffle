package com.sugaraxplosion.candysmoy.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.sugaraxplosion.candysmoy.game.actors.AImage
import com.sugaraxplosion.candysmoy.game.box2d.AbstractBody
import com.sugaraxplosion.candysmoy.game.utils.advanced.AdvancedBox2dScreen
import com.sugaraxplosion.candysmoy.game.utils.advanced.AdvancedGroup
import java.util.concurrent.atomic.AtomicBoolean

class BItem(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "circle"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
    }
    override val fixtureDef = FixtureDef().apply {
        density     = 1f
        restitution = 0.5f
    }

    val randomId = (0..14).random()

    override var actor: AdvancedGroup? = AImage(screenBox2d, screenBox2d.game.all.sweets[randomId])

    var column = 0

}