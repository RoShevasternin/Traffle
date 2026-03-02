package com.fishfestival.bubbleparty.game.box2d.bodies.standart

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.fishfestival.bubbleparty.game.actors.AImage
import com.fishfestival.bubbleparty.game.box2d.AbstractBody
import com.fishfestival.bubbleparty.game.utils.advanced.AdvancedGroup
import com.fishfestival.bubbleparty.game.utils.advanced.PARANAMA
import com.fishfestival.bubbleparty.game.utils.advanced.PIDAR
import com.fishfestival.bubbleparty.game.utils.advanced.box2d.AdvancedBox2dScreen
import com.fishfestival.bubbleparty.game.utils.gdxGame

class BRect(override val screenBox2d: AdvancedBox2dScreen): AbstractBody<PIDAR>() {
    override val name       = "rect"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.StaticBody
    }
    override val fixtureDef = FixtureDef()

    override var actor: PIDAR? = AImage(screenBox2d, gdxGame.assetsAll.BROGRESS_BACK)

}