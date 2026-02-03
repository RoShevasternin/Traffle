package com.dasholy.olympusdash.game.box2d.bodies

import com.dasholy.olympusdash.game.actors.image.AImage
import com.dasholy.olympusdash.game.box2d.AbstractBody
import com.dasholy.olympusdash.game.box2d.BodyId
import com.dasholy.olympusdash.game.utils.advanced.AdvancedBox2dScreen
import com.dasholy.olympusdash.game.utils.advanced.AdvancedGroup
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import java.util.concurrent.atomic.AtomicBoolean

class BEnemy(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "circle"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
    }
    override val fixtureDef = FixtureDef().apply {
        density = 1f
    }

    override var actor: AdvancedGroup? = AImage(screenBox2d, screenBox2d.game.gameAssets.ballon)

    override var id            = BodyId.Game.ENEMY
    override val collisionList = mutableListOf(BodyId.Game.AVIA)

    // Field
    val atomicBoolean = AtomicBoolean(true)

}