/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.actors.button

import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.novaburst.pixelrally.game.actors.AParticleEffectActor
import com.novaburst.pixelrally.game.utils.DimensionCalculator
import com.novaburst.pixelrally.game.utils.advanced.DisplayScreen
import com.novaburst.pixelrally.game.utils.gdxGame

open class RotatingButton(
    override val screen: DisplayScreen,
) : InteractiveButton(screen, Type.Spin) {

    override val sizeScaler = DimensionCalculator(DimensionCalculator.Axis.X, 420f)

    private val effectStarsDef  = AParticleEffectActor(ParticleEffect(gdxGame.particleEffectUtil.StarsDef), false)
    private val effectStarsBoom = AParticleEffectActor(ParticleEffect(gdxGame.particleEffectUtil.StarsBoom), false)

    override fun addActorsOnGroup() {
        super.addActorsOnGroup()

        addActors(effectStarsDef, effectStarsBoom)
        effectStarsDef.setPosition(62f.scaled, 75f.scaled)

        val scale = (width / 420f)
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

    // Core functionality
    fun resetEffect() {
        effectStarsDef.resume()
    }


}