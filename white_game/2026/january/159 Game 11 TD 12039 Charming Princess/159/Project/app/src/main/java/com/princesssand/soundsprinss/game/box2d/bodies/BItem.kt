package com.princesssand.soundsprinss.game.box2d.bodies

import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.scenes.scene2d.ui.ParticleEffectActor
import com.princesssand.soundsprinss.game.actors.AImage
import com.princesssand.soundsprinss.game.box2d.AbstractBody
import com.princesssand.soundsprinss.game.box2d.BodyId
import com.princesssand.soundsprinss.game.utils.actor.setPosition
import com.princesssand.soundsprinss.game.utils.advanced.AdvancedBox2dScreen
import com.princesssand.soundsprinss.game.utils.advanced.AdvancedGroup

class BItem(override val screenBox2d: AdvancedBox2dScreen, index: Int): AbstractBody() {
    override val name       = "circle"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
        gravityScale = 0f
    }
    override val fixtureDef = FixtureDef().apply {
        density     = 0.5f
        restitution = 0.5f
        friction    = 0.3f
    }

    override var actor: AdvancedGroup? = AImage(screenBox2d, screenBox2d.game.assetsAll.items[if (index > 5) index%6 else index ])

    override var id = BodyId.DYNAMIC
    override val collisionList = mutableListOf(BodyId.BORDERS, BodyId.DYNAMIC, BodyId.BALL)

    private val particleEffect = ParticleEffectActor(ParticleEffect(screenBox2d.game.particleEffectUtil.Boom),true)

    fun addEffect() {
        screenBox2d.stageUI.addActor(particleEffect)
    }

    fun startEffect(position: Vector2) {
        particleEffect.apply {
            setPosition(position)
            start()
        }
    }

}