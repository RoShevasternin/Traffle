package com.bounceroval.mazedackq.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.bounceroval.mazedackq.game.actors.AImage
import com.bounceroval.mazedackq.game.box2d.AbstractBody
import com.bounceroval.mazedackq.game.utils.advanced.AdvancedBox2dScreen
import com.bounceroval.mazedackq.game.utils.advanced.AdvancedGroup

class BBorderBall(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "circle"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.StaticBody
    }
    override val fixtureDef = FixtureDef().apply {
        restitution = (10..55).random() / 100f
        friction    = (10..55).random() / 100f
    }
    override var actor: AdvancedGroup? = AImage(screenBox2d, screenBox2d.game.all.ball)

}