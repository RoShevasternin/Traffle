package com.dasholy.olympusdash.game.manager

import com.badlogic.gdx.Gdx
import com.dasholy.olympusdash.game.LibGDXGame
import com.dasholy.olympusdash.game.screens.GameScreen
import com.dasholy.olympusdash.game.screens.MenuScreen
import com.dasholy.olympusdash.game.screens.RulesScreen
import com.dasholy.olympusdash.game.screens.SettingsScreen
import com.dasholy.olympusdash.game.screens.ShopScreen
import com.dasholy.olympusdash.game.screens.SplashcScreen
import com.dasholy.olympusdash.game.utils.advanced.AdvancedScreen
import com.dasholy.olympusdash.game.utils.runGDX

class NavigationManager(val game: LibGDXGame) {

    val backStack = mutableListOf<String>()
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