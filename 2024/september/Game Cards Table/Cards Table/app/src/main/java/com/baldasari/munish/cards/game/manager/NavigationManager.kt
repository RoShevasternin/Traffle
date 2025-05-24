package com.baldasari.munish.cards.game.manager

import com.badlogic.gdx.Gdx
import com.baldasari.munish.cards.game.LibGDXGame
import com.baldasari.munish.cards.game.screens.*
import com.baldasari.munish.cards.game.utils.advanced.AdvancedScreen
import com.baldasari.munish.cards.game.utils.runGDX

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

    fun clearBStack() {
        backStack.clear()
    }


    fun exit() = runGDX { Gdx.app.exit() }


    fun isBackStackEmpty() = backStack.isEmpty()

    private fun getScreenByName(name: String): AdvancedScreen = when (name) {
        SplashScreen   ::class.java.name -> SplashScreen(game)
        MenuScreen     ::class.java.name -> MenuScreen(game)
        RulesScreen    ::class.java.name -> RulesScreen(game)
        SettingScreen  ::class.java.name -> SettingScreen(game)
        SelectScreen   ::class.java.name -> SelectScreen(game)
        PlayerScreen   ::class.java.name -> PlayerScreen(game)
        GameScreen     ::class.java.name -> GameScreen(game)
        SuperMagScreen ::class.java.name -> SuperMagScreen(game)
        CubikScreen    ::class.java.name -> CubikScreen(game)

        else -> MenuScreen(game)
    }

}