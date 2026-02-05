/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.utils.advanced

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.Gdx

open class GameCore: ApplicationListener {

    var screen: DisplayScreen? = null
        private set

    // ---------------------------------------------------
    // Override
    // ---------------------------------------------------

    override fun create() {}

    // Handler method
    override fun render() {
        screen?.render(Gdx.graphics.deltaTime)
    }

    override fun resize(width: Int, height: Int) {
        screen?.resize(width, height)
    }

    override fun pause() {
        screen?.pause()
    }

    override fun resume() {
        screen?.resume()
    }

    // Processing logic
    override fun dispose() {
        screen?.dispose()
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    fun updateScreen(screen: DisplayScreen) {
        this.screen?.dispose()
        this.screen = screen.apply {
            resize(Gdx.graphics.width, Gdx.graphics.height)
            show()
        }
    }

}