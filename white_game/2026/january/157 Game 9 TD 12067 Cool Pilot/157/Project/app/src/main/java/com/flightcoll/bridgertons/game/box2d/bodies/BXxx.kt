package com.flightcoll.bridgertons.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.flightcoll.bridgertons.game.actors.AImage
import com.flightcoll.bridgertons.game.box2d.AbstractBody
import com.flightcoll.bridgertons.game.utils.advanced.AdvancedBox2dScreen
import com.flightcoll.bridgertons.game.utils.advanced.AdvancedGroup
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.math.truncate

class BXxx(override val screenBox2d: AdvancedBox2dScreen, val tipe: Static.Type): AbstractBody() {
    override val name       = tipe.names
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
    }
    override val fixtureDef = FixtureDef().apply {
        density     = 1f
        restitution = 0.45f
    }
    override var actor: AdvancedGroup? = AImage(screenBox2d, when(tipe) {
        Static.Type.x15 -> screenBox2d.game.assets._15
        Static.Type.x25 -> screenBox2d.game.assets._25
        Static.Type.x60 -> screenBox2d.game.assets._60
        Static.Type.x105 -> screenBox2d.game.assets._105
    })

    var isOnStart = AtomicBoolean(true)
        private set

    object Static {
        enum class Type(val names: String, val viluet: Int) {
            x15("15", 15),
            x25("25", 25),
            x60("60", 60),
            x105("105", 105),
        }
    }

}