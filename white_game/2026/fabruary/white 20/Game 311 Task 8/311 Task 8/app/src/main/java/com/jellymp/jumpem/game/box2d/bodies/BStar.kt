package com.jellymp.jumpem.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.jellymp.jumpem.game.actors.AImage
import com.jellymp.jumpem.game.box2d.AbstractBody
import com.jellymp.jumpem.game.box2d.BodyId
import com.jellymp.jumpem.game.utils.advanced.AdvancedGroup
import com.jellymp.jumpem.game.utils.advanced.box2d.AdvancedBox2dScreen
import com.jellymp.jumpem.game.utils.gdxGame

class BStar(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "circle"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.StaticBody
    }
    override val fixtureDef = FixtureDef().apply {
        restitution = 0.85f
    }

    override var actor: AdvancedGroup? = AImage(screenBox2d, gdxGame.assetsAll.STAR)

    override var id: String = BodyId.STAR
    override val collisionList = mutableListOf(BodyId.BALL)

}