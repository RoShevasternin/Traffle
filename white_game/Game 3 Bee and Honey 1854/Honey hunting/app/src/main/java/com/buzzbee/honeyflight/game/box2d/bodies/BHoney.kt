package com.buzzbee.honeyflight.game.box2d.bodies

import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.scenes.scene2d.ui.ParticleEffectActor
import com.buzzbee.honeyflight.game.actors.AImage
import com.buzzbee.honeyflight.game.box2d.AbstractBody
import com.buzzbee.honeyflight.game.box2d.BodyId
import com.buzzbee.honeyflight.game.utils.actor.setPosition
import com.buzzbee.honeyflight.game.utils.advanced.AdvancedBox2dScreen
import com.buzzbee.honeyflight.game.utils.advanced.AdvancedGroup
import com.buzzbee.honeyflight.game.utils.currentTimeMinus
import com.buzzbee.honeyflight.game.utils.toUI
import java.util.concurrent.atomic.AtomicBoolean

class BHoney(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "circle"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
        fixedRotation = true
        gravityScale  = 0f
    }
    override val fixtureDef = FixtureDef().apply {
        density  = 1f
        isSensor = true
    }

    override var actor: AdvancedGroup? = AImage(screenBox2d, screenBox2d.game.allAssets.honey)

    override val collisionList = mutableListOf(BodyId.BEE)

    val isStart = AtomicBoolean(true)

    private val particleEffect = ParticleEffectActor(ParticleEffect(screenBox2d.game.particleEffectUtil.MED),false)


    private fun addEffect() {
        screenBox2d.stageUI.addActor(particleEffect)
    }

    fun startEffect() {
        particleEffect.apply {
            setPosition(body!!.position.toUI)
            start()
        }
    }

    init {
        addEffect()
    }

}