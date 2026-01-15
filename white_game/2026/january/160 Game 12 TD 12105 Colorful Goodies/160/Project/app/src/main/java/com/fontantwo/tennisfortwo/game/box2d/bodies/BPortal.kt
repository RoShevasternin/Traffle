package com.fontantwo.tennisfortwo.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.fontantwo.tennisfortwo.game.box2d.AbstractBody
import com.fontantwo.tennisfortwo.game.utils.advanced.AdvancedBox2dScreen

class BPortal(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "vrota"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.StaticBody
    }
    override val fixtureDef = FixtureDef()

}