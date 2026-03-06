package com.bounceques.ternationaret.game.box2d.bodies

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.bounceques.ternationaret.game.actors.image.AImage
import com.bounceques.ternationaret.game.box2d.AbstractBody
import com.bounceques.ternationaret.game.box2d.BodyId
import com.bounceques.ternationaret.game.utils.advanced.AdvancedBox2dScreen
import com.bounceques.ternationaret.game.utils.advanced.AdvancedGroup

class BTri(override val screenBox2d: AdvancedBox2dScreen, region: Texture): AbstractBody() {
    override val name       = "tri"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.StaticBody
    }
    override val fixtureDef = FixtureDef()

    override var actor: AdvancedGroup? = AImage(screenBox2d, region)

    override var id = BodyId.ENEMY
    override val collisionList = mutableListOf(BodyId.BALL)
}