package com.candybostony.bonceria.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.candybostony.bonceria.game.actors.AImage
import com.candybostony.bonceria.game.box2d.AbstractBody
import com.candybostony.bonceria.game.utils.advanced.AdvancedGroup
import com.candybostony.bonceria.game.utils.advanced.box2d.AdvancedBox2dScreen
import com.candybostony.bonceria.game.utils.gdxGame

class BBall(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "circle"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
    }
    override val fixtureDef = FixtureDef().apply {
        density     = 2f
        restitution = 0.55f
        friction    = 0.7f
    }

    override var actor: AdvancedGroup? = AImage(screenBox2d, gdxGame.assetsAll.BALL)

}