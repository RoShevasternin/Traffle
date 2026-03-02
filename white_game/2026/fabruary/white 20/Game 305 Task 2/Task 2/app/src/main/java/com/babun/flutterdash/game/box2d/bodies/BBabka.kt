package com.babun.flutterdash.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.babun.flutterdash.game.actors.AImage
import com.babun.flutterdash.game.box2d.AbstractBody
import com.babun.flutterdash.game.utils.advanced.AdvancedGroup
import com.babun.flutterdash.game.utils.advanced.box2d.AdvancedBox2dScreen
import com.babun.flutterdash.game.utils.gdxGame
import java.util.concurrent.atomic.AtomicBoolean

class BBabka(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "baba"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
        fixedRotation = true
    }
    override val fixtureDef = FixtureDef().apply {
        density     = 1f
        restitution = 0.1f
        friction    = 0.5f
    }

    override var actor: AdvancedGroup? = AImage(screenBox2d, gdxGame.assetsAll.GAME_BABA)

    val atomBool = AtomicBoolean(true)

}