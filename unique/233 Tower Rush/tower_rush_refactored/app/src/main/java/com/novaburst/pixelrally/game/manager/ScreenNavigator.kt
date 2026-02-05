/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.manager

import com.badlogic.gdx.Gdx
import com.novaburst.pixelrally.game.screens.GalleryScreen
import com.novaburst.pixelrally.game.screens.PlayDisplay
import com.novaburst.pixelrally.game.screens.MainMenu
import com.novaburst.pixelrally.game.screens.LoaderScreen
import com.novaburst.pixelrally.game.screens.PlayScreen
import com.novaburst.pixelrally.game.screens.ProfileScreen
import com.novaburst.pixelrally.game.screens.ConfigDisplay
import com.novaburst.pixelrally.game.screens.StoreDisplay
import com.novaburst.pixelrally.game.screens.Wheel_of_FortuneScreen
import com.novaburst.pixelrally.game.utils.advanced.DisplayScreen
import com.novaburst.pixelrally.game.utils.gdxGame
import com.novaburst.pixelrally.game.utils.runGDX

class ScreenNavigator {

    private val backStack = mutableListOf<String>()

    fun navigate(toScreenName: String, fromScreenName: String? = null) = runGDX {
        gdxGame.updateScreen(getScreenByName(toScreenName))
        backStack.filter { name -> name == toScreenName }.onEach { name -> backStack.remove(name) }
        fromScreenName?.let { fromName ->
            backStack.filter { name -> name == fromName }.onEach { name -> backStack.remove(name) }
            backStack.add(fromName)
        }
    }

    // Core functionality
    fun back() = runGDX {
        if (isBackStackEmpty()) exit() else gdxGame.updateScreen(getScreenByName(backStack.removeAt(backStack.lastIndex)))
    }

    fun exit() = runGDX { Gdx.app.exit() }


    fun isBackStackEmpty() = backStack.isEmpty()

    // Handler method
    private fun getScreenByName(name: String): DisplayScreen = when(name) {
        LoaderScreen          ::class.java.name -> LoaderScreen()
        MainMenu            ::class.java.name -> MainMenu()
        Wheel_of_FortuneScreen::class.java.name -> Wheel_of_FortuneScreen()
        ProfileScreen         ::class.java.name -> ProfileScreen()
        StoreDisplay            ::class.java.name -> StoreDisplay()
        GalleryScreen         ::class.java.name -> GalleryScreen()
        ConfigDisplay        ::class.java.name -> ConfigDisplay()
        PlayScreen            ::class.java.name -> PlayScreen()
        PlayDisplay            ::class.java.name -> PlayDisplay()

        else -> MainMenu()
    }

}