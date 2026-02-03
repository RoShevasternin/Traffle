package com.gorillaz.puzzlegame.game.actors.button

import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.gorillaz.puzzlegame.game.actors.AParticleEffectActor
import com.gorillaz.puzzlegame.game.utils.SizeScaler
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedScreen
import com.gorillaz.puzzlegame.game.utils.gdxGame

open class AButtonSpin(
    override val screen: AdvancedScreen,
) : AButton(screen, Type.Spin) {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, 350f)

    private val effectStarsDef  = AParticleEffectActor(ParticleEffect(gdxGame.particleEffectUtil.StarsDef), false)
    private val effectStarsBoom = AParticleEffectActor(ParticleEffect(gdxGame.particleEffectUtil.StarsBoom), false)

    override fun addActorsOnGroup() {
        super.addActorsOnGroup()

        addActors(effectStarsDef, effectStarsBoom)
        effectStarsDef.setPosition(62f.scaled, 75f.scaled)

        val scale = (width / 350f)
        effectStarsDef.particleEffect.scaleEffect(scale)
        effectStarsBoom.particleEffect.scaleEffect(scale)

        effectStarsDef.start()

        touchUpBlock = { x, y -> boom(x, y) }
    }

    fun boom(x: Float, y: Float) {
        effectStarsDef.pause()

        effectStarsBoom.setPosition(x, y)
        effectStarsBoom.start()
    }

    fun resetEffect() {
        effectStarsDef.resume()
    }


}