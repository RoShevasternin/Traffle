package com.royaltombsecrets.miniquizler.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.royaltombsecrets.miniquizler.game.actors.AImage
import com.royaltombsecrets.miniquizler.game.box2d.AbstractBody
import com.royaltombsecrets.miniquizler.game.utils.advanced.AdvancedBox2dScreen
import com.royaltombsecrets.miniquizler.game.utils.advanced.AdvancedGroup
import java.util.concurrent.atomic.AtomicBoolean

class BItem(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "circle"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
    }
    override val fixtureDef = FixtureDef().apply {
        density = 1f
    }

    override var actor: AdvancedGroup? = AImage(screenBox2d, screenBox2d.game.all.brilliants.random())

    var isOnStart = AtomicBoolean(true)


}