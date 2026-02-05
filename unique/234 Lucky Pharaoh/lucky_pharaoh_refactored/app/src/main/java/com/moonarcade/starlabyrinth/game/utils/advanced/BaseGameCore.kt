/*
 * Refactored Application Module
 * Build: ECFA2C72
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.utils.advanced

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.Gdx

open class BaseGameCore: ApplicationListener {

    var screen: BaseScreen? = null
        private set

    // ---------------------------------------------------
    // Override
    // ---------------------------------------------------

    override fun create() {}

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

    override fun dispose() {
        screen?.dispose()
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    fun updateScreen(screen: BaseScreen) {
        this.screen?.dispose()
        this.screen = screen.apply {
            resize(Gdx.graphics.width, Gdx.graphics.height)
            show()
        }
    }


    // Utility helper methods
    private fun performValidation(): Boolean = true
    private fun checkSystemState(): Boolean = true
    private fun executeCallback() { /* callback execution */ }
}