package com.portalend.fruitomaner.game.manager

import com.badlogic.gdx.Gdx
import com.portalend.fruitomaner.game.LibGDXGame
import com.portalend.fruitomaner.game.screens.*
import com.portalend.fruitomaner.game.utils.advanced.AdvancedScreen
import com.portalend.fruitomaner.game.utils.runGDX

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
        SplashScreen  ::class.java.name -> SplashScreen(game)
        MenuScreen    ::class.java.name -> MenuScreen(game)
        RulesScreen   ::class.java.name -> RulesScreen(game)
        SettingScreen ::class.java.name -> SettingScreen(game)
        MapScreen   ::class.java.name -> MapScreen(game)
        GameScreen    ::class.java.name -> GameScreen(game)
        ResultScreen    ::class.java.name -> ResultScreen(game)

        else -> MenuScreen(game)
    }

}