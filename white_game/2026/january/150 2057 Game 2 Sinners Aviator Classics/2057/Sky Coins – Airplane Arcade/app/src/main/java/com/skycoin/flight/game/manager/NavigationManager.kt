package com.skycoin.flight.game.manager

import com.badlogic.gdx.Gdx
import com.skycoin.flight.game.LibGDXGame
import com.skycoin.flight.game.screens.GameScreen
import com.skycoin.flight.game.screens.MenuScreen
import com.skycoin.flight.game.screens.RulesScreen
import com.skycoin.flight.game.screens.SettingsScreen
import com.skycoin.flight.game.screens.ShopScreen
import com.skycoin.flight.game.screens.SplashcScreen
import com.skycoin.flight.game.utils.advanced.AdvancedScreen
import com.skycoin.flight.game.utils.runGDX

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
        SplashcScreen ::class.java.name -> SplashcScreen(game)
        MenuScreen    ::class.java.name -> MenuScreen(game)
        RulesScreen   ::class.java.name -> RulesScreen(game)
        SettingsScreen::class.java.name -> SettingsScreen(game)
        ShopScreen    ::class.java.name -> ShopScreen(game)
        GameScreen    ::class.java.name -> GameScreen(game)

        else -> MenuScreen(game)
    }

}