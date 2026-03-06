package com.parrotrun.skydrink.game.manager

import com.badlogic.gdx.Gdx
import com.parrotrun.skydrink.game.LibGDXGame
import com.parrotrun.skydrink.game.screens.GameScreen
import com.parrotrun.skydrink.game.screens.MenuScreen
import com.parrotrun.skydrink.game.screens.RulesScreen
import com.parrotrun.skydrink.game.screens.SettingsScreen
import com.parrotrun.skydrink.game.screens.WinScreen
import com.parrotrun.skydrink.game.screens.LoaderScreen
import com.parrotrun.skydrink.game.screens.LoseScreen
import com.parrotrun.skydrink.game.utils.advanced.AdvancedScreen
import com.parrotrun.skydrink.game.utils.runGDX

class NavigationManager(val game: LibGDXGame) {

    private val backStack = mutableListOf<String>()
    var key: Int? = null
        private set

    fun navigate(toScreenName: String, fromScreenName: String? = null, key: Int? = null) = runGDX {
        this.key = key

        game.updateScreen(getScreenByName(toScreenName))
        backStack.filter { name -> name == toScreenName }.onEach { name -> backStack.remove(name) }
        fromScreenName?.let { fromName ->
            backStack.filter { name -> name == fromName }.onEach { name -> backStack.remove(name) }
            backStack.add(fromName)
        }
    }

    fun back(key: Int? = null) = runGDX {
        this.key = key

        if (isBackStackEmpty()) exit() else game.updateScreen(getScreenByName(backStack.removeAt(backStack.lastIndex)))
    }


    fun exit() = runGDX { Gdx.app.exit() }


    fun isBackStackEmpty() = backStack.isEmpty()

    private fun getScreenByName(name: String): AdvancedScreen = when(name) {
        LoaderScreen  ::class.java.name  -> LoaderScreen(game)
        MenuScreen    ::class.java.name -> MenuScreen(game)
        RulesScreen   ::class.java.name -> RulesScreen(game)
        SettingsScreen::class.java.name -> SettingsScreen(game)
        GameScreen    ::class.java.name -> GameScreen(game)
        WinScreen     ::class.java.name  -> WinScreen(game)
        LoseScreen    ::class.java.name  -> LoseScreen(game)

        else -> MenuScreen(game)
    }

}