package com.wintergroup.cupcakejump.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.wintergroup.cupcakejump.game.actors.AImage
import com.wintergroup.cupcakejump.game.box2d.AbstractBody
import com.wintergroup.cupcakejump.game.utils.advanced.AdvancedGroup
import com.wintergroup.cupcakejump.game.utils.advanced.box2d.AdvancedBox2dScreen
import com.wintergroup.cupcakejump.game.utils.gdxGame

class BPlatform(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "platform"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.StaticBody
    }
    override val fixtureDef = FixtureDef().apply {
        density     = 0.5f
        restitution = 0.6f
        friction    = 0.3f
    }

    override var actor: AdvancedGroup? = AImage(screenBox2d, gdxGame.assetsAll.platform)
}