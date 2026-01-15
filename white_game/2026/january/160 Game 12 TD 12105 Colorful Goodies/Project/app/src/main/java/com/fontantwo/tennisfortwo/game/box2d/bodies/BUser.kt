package com.fontantwo.tennisfortwo.game.box2d.bodies

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.fontantwo.tennisfortwo.game.actors.AImage
import com.fontantwo.tennisfortwo.game.box2d.AbstractBody
import com.fontantwo.tennisfortwo.game.utils.advanced.AdvancedBox2dScreen
import com.fontantwo.tennisfortwo.game.utils.advanced.AdvancedGroup

class BUser(override val screenBox2d: AdvancedBox2dScreen, region: TextureRegion): AbstractBody() {
    override val name       = "circle"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
    }
    override val fixtureDef = FixtureDef().apply {
        density     = 1f
        restitution = 0.5f
        friction    = 0.5f
    }

    override var actor: AdvancedGroup? = AImage(screenBox2d, region)

}