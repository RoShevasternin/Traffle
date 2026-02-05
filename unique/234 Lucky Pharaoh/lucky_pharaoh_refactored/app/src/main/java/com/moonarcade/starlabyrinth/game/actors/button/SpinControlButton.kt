/*
 * Refactored Application Module
 * Build: 5A2B792F
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.actors.button

import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.moonarcade.starlabyrinth.game.actors.AParticleEffectActor
import com.moonarcade.starlabyrinth.game.utils.ScaleCalculator
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseScreen
import com.moonarcade.starlabyrinth.game.utils.gdxGame

open class SpinControlButton(
    override val screen: BaseScreen,
) : ClickableElement(screen, Type.Spin) {

    override val sizeScaler = ScaleCalculator(ScaleCalculator.Axis.X, 420f)

    private val effectStarsDef = AParticleEffectActor(ParticleEffect(gdxGame.particleEffectUtil.StarsDef), false)
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

    fun resetEffect() {
        effectStarsDef.resume()
    }


}