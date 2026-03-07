package com.oceanstar.ballduinstar.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.oceanstar.ballduinstar.game.actors.AImage
import com.oceanstar.ballduinstar.game.box2d.AbstractBody
import com.oceanstar.ballduinstar.game.utils.advanced.AdvancedGroup
import com.oceanstar.ballduinstar.game.utils.advanced.box2d.AdvancedBox2dScreen
import com.oceanstar.ballduinstar.game.utils.gdxGame
import java.util.concurrent.atomic.AtomicBoolean

class BShar(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "circle"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
    }
    override val fixtureDef = FixtureDef().apply {
        density     = 1f
        restitution = 0.1f
        friction    = 0.5f
    }

    override var actor: AdvancedGroup? = AImage(screenBox2d, gdxGame.assetsAll.BALL)

    val atomBool = AtomicBoolean(true)

}