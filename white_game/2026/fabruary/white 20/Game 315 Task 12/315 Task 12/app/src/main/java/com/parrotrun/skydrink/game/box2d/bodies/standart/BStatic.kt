package com.parrotrun.skydrink.game.box2d.bodies.standart

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.parrotrun.skydrink.game.actors.image.AImage
import com.parrotrun.skydrink.game.box2d.AbstractBody
import com.parrotrun.skydrink.game.utils.advanced.AdvancedBox2dScreen
import com.parrotrun.skydrink.game.utils.advanced.AdvancedGroup

class BStatic(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "circle"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.StaticBody
    }
    override val fixtureDef = FixtureDef()
    override var actor: AdvancedGroup? = AImage(screenBox2d)
}