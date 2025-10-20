package com.honeyhun.hunhoney.game.box2d.bodies

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.scenes.scene2d.ui.ParticleEffectActor
import com.honeyhun.hunhoney.game.actors.AImageAnim
import com.honeyhun.hunhoney.game.box2d.AbstractBody
import com.honeyhun.hunhoney.game.box2d.BodyId
import com.honeyhun.hunhoney.game.utils.actor.setPosition
import com.honeyhun.hunhoney.game.utils.advanced.AdvancedBox2dScreen
import com.honeyhun.hunhoney.game.utils.advanced.AdvancedGroup
import com.honeyhun.hunhoney.game.utils.toB2
import com.honeyhun.hunhoney.game.utils.toUI
import java.util.concurrent.atomic.AtomicBoolean

class BBee(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "circle"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
        fixedRotation = true
    }
    override val fixtureDef = FixtureDef().apply {
        density  = 1f
        friction = 0f
    }

    override var actor: AdvancedGroup? = AImageAnim(screenBox2d, 0.09f, screenBox2d.game.allAssets.bee, Animation.PlayMode.LOOP_PINGPONG)

    override var id            = BodyId.BEE
    override val collisionList = mutableListOf(BodyId.BORDERS, BodyId.DOWN, BodyId.HONEY)

    val isStart = AtomicBoolean(true)

    private val particleEffect = ParticleEffectActor(ParticleEffect(screenBox2d.game.particleEffectUtil.BEE),false)


    private fun addEffect() {
        screenBox2d.stageUI.addActor(particleEffect)
    }

    private fun startEffect() {
        particleEffect.start()
    }

    override fun onCreate() {
        addEffect()
        startEffect()

        val pos = Vector2(33f, 71f).toB2

        renderBlockArray.add(RenderBlock {
            particleEffect.setPosition(body!!.position.add(pos).sub(center).toUI)
        })
    }

}