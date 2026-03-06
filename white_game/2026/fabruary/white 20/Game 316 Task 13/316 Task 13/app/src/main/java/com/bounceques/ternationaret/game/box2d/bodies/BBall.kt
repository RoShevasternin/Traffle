package com.bounceques.ternationaret.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.bounceques.ternationaret.game.actors.image.AImage
import com.bounceques.ternationaret.game.box2d.AbstractBody
import com.bounceques.ternationaret.game.box2d.BodyId
import com.bounceques.ternationaret.game.utils.advanced.AdvancedBox2dScreen
import com.bounceques.ternationaret.game.utils.advanced.AdvancedGroup

class BBall(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "circle"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
    }
    override val fixtureDef = FixtureDef().apply {
        density     = 3f
        restitution = 0.3f
        friction    = 0.4f
    }

    override var actor: AdvancedGroup? = AImage(screenBox2d, screenBox2d.game.assetsAll.BALL)

    override var id = BodyId.BALL
    override val collisionList = mutableListOf(BodyId.BORDERS, BodyId.COIN, BodyId.ENEMY)
}