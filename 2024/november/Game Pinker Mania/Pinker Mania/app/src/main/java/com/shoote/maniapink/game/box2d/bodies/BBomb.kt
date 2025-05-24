package com.shoote.maniapink.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.shoote.maniapink.game.actors.ABomb
import com.shoote.maniapink.game.box2d.AbstractBody
import com.shoote.maniapink.game.box2d.AbstractBody.RenderBlock
import com.shoote.maniapink.game.utils.advanced.AdvancedBox2dScreen

class BBomb(override val screenBox2d: AdvancedBox2dScreen): AbstractBody<ABomb>() {
    override val name       = "circle"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
    }
    override val fixtureDef = FixtureDef().apply {
        density     = 1f
        restitution = 0.8f
        friction    = 0.5f
    }

    override var actor: ABomb? = ABomb(screenBox2d)

    init {
        renderBlockArray.add(RenderBlock {
            if ((body?.position?.y ?: 0f) < 0f) destroy()
        })
    }

}