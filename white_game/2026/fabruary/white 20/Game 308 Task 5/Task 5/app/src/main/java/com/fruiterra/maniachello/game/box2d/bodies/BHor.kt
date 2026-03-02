package com.fruiterra.maniachello.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.fruiterra.maniachello.game.box2d.AbstractBody
import com.fruiterra.maniachello.game.utils.advanced.box2d.AdvancedBox2dScreen

class BHor(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "h"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.StaticBody
    }
    override val fixtureDef = FixtureDef()

}