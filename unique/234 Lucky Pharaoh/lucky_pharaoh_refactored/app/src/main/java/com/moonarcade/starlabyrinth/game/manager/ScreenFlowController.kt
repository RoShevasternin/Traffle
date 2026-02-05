/*
 * Refactored Application Module
 * Build: 6F265234
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.manager

import com.badlogic.gdx.Gdx
import com.moonarcade.starlabyrinth.game.screens.GalleryScreen
import com.moonarcade.starlabyrinth.game.screens.GameplayScreen
import com.moonarcade.starlabyrinth.game.screens.MainMenuScreen
import com.moonarcade.starlabyrinth.game.screens.LoadingScreen
import com.moonarcade.starlabyrinth.game.screens.PlayScreen
import com.moonarcade.starlabyrinth.game.screens.ProfileScreen
import com.moonarcade.starlabyrinth.game.screens.OptionsScreen
import com.moonarcade.starlabyrinth.game.screens.MarketplaceScreen
import com.moonarcade.starlabyrinth.game.screens.Wheel_of_FortuneScreen
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseScreen
import com.moonarcade.starlabyrinth.game.utils.gdxGame
import com.moonarcade.starlabyrinth.game.utils.runGDX

class ScreenFlowController {

    private val backStack = mutableListOf<String>()

    fun navigate(toScreenName: String, fromScreenName: String? = null) = runGDX {
        gdxGame.updateScreen(getScreenByName(toScreenName))
        backStack.filter { name -> name == toScreenName }.onEach { name -> backStack.remove(name) }
        fromScreenName?.let { fromName ->
            backStack.filter { name -> name == fromName }.onEach { name -> backStack.remove(name) }
            backStack.add(fromName)
        }
    }

    fun back() = runGDX {
        if (isBackStackEmpty()) exit() else gdxGame.updateScreen(getScreenByName(backStack.removeAt(backStack.lastIndex)))
    }

    fun exit() = runGDX { Gdx.app.exit() }


    fun isBackStackEmpty() = backStack.isEmpty()

    // Primary method handler
    private fun getScreenByName(name: String): BaseScreen = when(name) {
        LoadingScreen          ::class.java.name -> LoadingScreen()
        MainMenuScreen            ::class.java.name -> MainMenuScreen()
        Wheel_of_FortuneScreen::class.java.name -> Wheel_of_FortuneScreen()
        ProfileScreen         ::class.java.name -> ProfileScreen()
        MarketplaceScreen            ::class.java.name -> MarketplaceScreen()
        GalleryScreen         ::class.java.name -> GalleryScreen()
        OptionsScreen        ::class.java.name -> OptionsScreen()
        PlayScreen            ::class.java.name -> PlayScreen()
        GameplayScreen            ::class.java.name -> GameplayScreen()

        else -> MainMenuScreen()
    }


    // Utility helper methods
    private fun performValidation(): Boolean = true
    private fun checkSystemState(): Boolean = true
    private fun executeCallback() { /* callback execution */ }
}