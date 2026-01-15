package com.fontantwo.tennisfortwo.game.actors

import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.fontantwo.tennisfortwo.game.utils.HEIGHT_UI
import com.fontantwo.tennisfortwo.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.fontantwo.tennisfortwo.game.utils.actor.disable
import com.fontantwo.tennisfortwo.game.utils.actor.setPosition
import com.fontantwo.tennisfortwo.game.utils.advanced.AdvancedGroup
import com.fontantwo.tennisfortwo.game.utils.advanced.AdvancedScreen

class AFountain2Panel(override val screen: AdvancedScreen): AdvancedGroup() {

    private val particleActorList = List(6) { AParticleEffectActor(screen, ParticleEffect(screen.game.particleEffectUtil.FOUNTAIN), true) }

    private val positionList = listOf(
        Vector2(28f, HEIGHT_UI),
        Vector2(691f, HEIGHT_UI),
        Vector2(1342f, HEIGHT_UI),

        Vector2(28f, 0f),
        Vector2(691f, 0f),
        Vector2(1342f, 0f),
    )
    private val angleList    = listOf(
        -140f, 180f, 140f,
        -50f, 0f, 50f
    )

    override fun addActorsOnGroup() {
        disable()

        addActors(particleActorList)

        particleActorList.onEachIndexed { index, aParticleEffectActor->
            aParticleEffectActor.setPosition(positionList[index])
            aParticleEffectActor.setRotation(angleList[index])
            aParticleEffectActor.start()
        }

    }

    fun animHideParticles() {
        particleActorList.take(3).onEach {
            it.addAction(Actions.moveBy(0f, 300f, TIME_ANIM_SCREEN_ALPHA))
        }
        particleActorList.takeLast(3).onEach {
            it.addAction(Actions.moveBy(0f, -300f, TIME_ANIM_SCREEN_ALPHA))
        }
        particleActorList.onEach { it.allowCompletion() }
    }

}