package com.shoote.maniapink.game.manager

import com.badlogic.gdx.Gdx
import com.shoote.maniapink.game.LibGDXGame
import com.shoote.maniapink.game.screens.*
import com.shoote.maniapink.game.utils.advanced.AdvancedScreen
import com.shoote.maniapink.game.utils.runGDX

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

    fun clearBackStack() {
        backStack.clear()
    }


    fun exit() = runGDX { Gdx.app.exit() }


    fun isBackStackEmpty() = backStack.isEmpty()

    private fun getScreenByName(name: String): AdvancedScreen = when (name) {
        LoaderScreen     ::class.java.name -> LoaderScreen(game)
        MenuScreen       ::class.java.name -> MenuScreen(game)
        ModeScreen       ::class.java.name -> ModeScreen(game)
        SettingsScreen   ::class.java.name -> SettingsScreen(game)
        GameScreen       ::class.java.name -> GameScreen(game)
        LoseScreen       ::class.java.name -> LoseScreen(game)
        CompleteScreen   ::class.java.name -> CompleteScreen(game)
        ShopScreen       ::class.java.name -> ShopScreen(game)
        ShopStarScreen   ::class.java.name -> ShopStarScreen(game)

        else -> MenuScreen(game)
    }

}