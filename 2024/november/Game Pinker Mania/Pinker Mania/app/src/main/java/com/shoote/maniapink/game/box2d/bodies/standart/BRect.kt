package com.shoote.maniapink.game.box2d.bodies.standart

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.shoote.maniapink.game.actors.AImage
import com.shoote.maniapink.game.box2d.AbstractBody
import com.shoote.maniapink.game.utils.advanced.AdvancedBox2dScreen
import com.shoote.maniapink.game.utils.advanced.AdvancedGroup

class BRect(override val screenBox2d: AdvancedBox2dScreen): AbstractBody<AdvancedGroup>() {
    override val name       = "rect"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.StaticBody
    }
    override val fixtureDef = FixtureDef()

    override var actor: AdvancedGroup? = AImage(screenBox2d, screenBox2d.game.all.prog_back)

}