package com.fontantwo.tennisfortwo.game.actors

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.utils.Disposable
import com.fontantwo.tennisfortwo.game.utils.advanced.AdvancedGroup
import com.fontantwo.tennisfortwo.game.utils.advanced.AdvancedScreen
import com.fontantwo.tennisfortwo.util.OneTime

class AParticleEffectActor(
    override val screen: AdvancedScreen,
    private val particleEffect: ParticleEffect,
    private val resetOnStart  : Boolean,
): AdvancedGroup(), Disposable {

    private val originalAngles: List<Static.AngleRange> = particleEffect.emitters.map { emitter ->
        Static.AngleRange(
            emitter.angle.lowMin,
            emitter.angle.lowMax,
            emitter.angle.highMin,
            emitter.angle.highMax
        )
    }
    private val oneTimeComplete = OneTime()

    var onComplete: (() -> Unit)? = null

    override fun addActorsOnGroup() {}

    private var lastDelta: Float = 0f

    override fun act(delta: Float) {
        super.act(delta)
        lastDelta = delta
        if (particleEffect.isComplete) oneTimeComplete.use { onComplete?.invoke() }
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)

        particleEffect.setPosition(x, y)
        particleEffect.update(lastDelta)
        particleEffect.draw(batch)
    }

    fun start() {
        if (resetOnStart) {
            particleEffect.reset(false)
        }
        particleEffect.start()
    }

    override fun scaleChanged() {
        super.scaleChanged()
        particleEffect.scaleEffect(scaleX, scaleY, scaleY)
    }

    override fun positionChanged() {
        super.positionChanged()
        particleEffect.setPosition(x, y)
    }

    override fun rotationChanged() {
        super.rotationChanged()

        particleEffect.emitters.forEachIndexed { index, emitter ->
            originalAngles[index].apply {
                emitter.angle.setLow(lowMin + rotation, lowMax + rotation)
                emitter.angle.setHigh(highMin + rotation, highMax + rotation)
            }
        }
    }

    override fun setPosition(x: Float, y: Float) {
        super.setPosition(x, y)
        particleEffect.setPosition(x, y)
    }

    override fun setRotation(degrees: Float) {
        super.setRotation(degrees)
        rotationChanged()
    }

    override fun dispose() {
        particleEffect.dispose()
    }

    fun allowCompletion() {
        particleEffect.allowCompletion()
    }

    object Static {
        data class AngleRange(
            val lowMin: Float,
            val lowMax: Float,
            val highMin: Float,
            val highMax: Float
        )
    }
}