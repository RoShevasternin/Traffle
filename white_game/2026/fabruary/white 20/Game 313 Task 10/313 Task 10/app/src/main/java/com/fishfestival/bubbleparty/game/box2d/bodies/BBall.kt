package com.fishfestival.bubbleparty.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.fishfestival.bubbleparty.game.actors.ABall
import com.fishfestival.bubbleparty.game.box2d.AbstractBody
import com.fishfestival.bubbleparty.game.utils.advanced.box2d.AdvancedBox2dScreen
import com.fishfestival.bubbleparty.game.utils.gdxGame
import com.fishfestival.bubbleparty.game.utils.region

class BBall(override val screenBox2d: AdvancedBox2dScreen, val typeBall: Type): AbstractBody<ABall>() {
    override val name       = "circle"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
    }
    override val fixtureDef = FixtureDef().apply {
        density     = 1f
        restitution = 0.8f
        friction    = 0.5f
    }

    override var actor: ABall? = ABall(screenBox2d, gdxGame.assetsAll.listBall[typeBall.ordinal].region)

    enum class Type {
        Red, Yellow, Green, Blue, Purple, Rainbow
    }

    var isTarget = false

    val joinedBallUnique = mutableSetOf<BBall>(this)

    var currentTimeFinish = 0L

    init {
        renderBlockArray.add(RenderBlock {
            if ((body?.position?.y ?: 0f) < 0f) destroy()
        })
    }

    override fun destroy() {
        super.destroy()
        gdxGame.soundUtil.apply { play(boom, 0.05f) }
    }

}