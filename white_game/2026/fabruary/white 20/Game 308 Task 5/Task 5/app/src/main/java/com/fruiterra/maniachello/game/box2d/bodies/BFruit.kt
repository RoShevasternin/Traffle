package com.fruiterra.maniachello.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.fruiterra.maniachello.game.actors.AImage
import com.fruiterra.maniachello.game.box2d.AbstractBody
import com.fruiterra.maniachello.game.utils.advanced.AdvancedGroup
import com.fruiterra.maniachello.game.utils.advanced.box2d.AdvancedBox2dScreen
import com.fruiterra.maniachello.game.utils.gdxGame
import java.util.concurrent.atomic.AtomicBoolean

class BFruit(
    override val screenBox2d: AdvancedBox2dScreen,
    val fruitType: Int,
): AbstractBody() {
    override val name       = "circle"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
        fixedRotation = true
    }
    override val fixtureDef = FixtureDef().apply {
        density     = 1f
        restitution = 0.5f
        friction    = 0.2f
    }

    override var actor: AdvancedGroup? = AImage(screenBox2d, gdxGame.assetsAll.listFruits[fruitType])

    val atomBool = AtomicBoolean(true)

    val connected = mutableSetOf<BFruit>()

}