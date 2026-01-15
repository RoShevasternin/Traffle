package com.royaltombsecrets.miniquizler.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.royaltombsecrets.miniquizler.game.box2d.AbstractBody
import com.royaltombsecrets.miniquizler.game.utils.advanced.AdvancedBox2dScreen

class BStatic(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "circle"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.StaticBody
    }
    override val fixtureDef = FixtureDef()

}