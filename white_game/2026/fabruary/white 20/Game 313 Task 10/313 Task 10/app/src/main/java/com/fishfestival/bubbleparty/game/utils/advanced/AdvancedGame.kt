package com.fishfestival.bubbleparty.game.utils.advanced

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.Gdx
import com.fishfestival.bubbleparty.game.screens.GameScreen

open class AdvancedGame: ApplicationListener {

    private var screen: AdvancedScreen? = null
    private var parname: PARANAMA? = null

    // ---------------------------------------------------
    // Override
    // ---------------------------------------------------

    override fun create() {}

    override fun render() {
        screen?.render(Gdx.graphics.deltaTime)
        parname?.render(Gdx.graphics.deltaTime)
    }

    override fun resize(width: Int, height: Int) {
        screen?.resize(width, height)
        parname?.resize(width, height)
    }

    override fun pause() {
        screen?.pause()
        parname?.pause()
    }

    override fun resume() {
        screen?.resume()
        parname?.resume()
    }

    override fun dispose() {
        screen?.dispose()
        parname?.dispose()
    }

    fun goToGame() {
        this.screen?.dispose()
        this.screen = null
        parname = GameScreen()
        parname?.resize(Gdx.graphics.width, Gdx.graphics.height)
        parname?.show()
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    fun updateScreen(screen: AdvancedScreen) {
        this.parname?.dispose()
        this.parname = null

        this.screen?.dispose()
        this.screen = screen.apply {
            resize(Gdx.graphics.width, Gdx.graphics.height)
            show()
        }
    }

}