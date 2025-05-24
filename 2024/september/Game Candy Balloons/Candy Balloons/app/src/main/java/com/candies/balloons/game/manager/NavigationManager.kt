package com.candies.balloons.game.manager

import com.badlogic.gdx.Gdx
import com.candies.balloons.game.LibGDXGame
import com.candies.balloons.game.screens.*
import com.candies.balloons.game.utils.advanced.AdvancedScreen
import com.candies.balloons.game.utils.runGDX

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
        SplashScreen      ::class.java.name -> SplashScreen(game)
        MenuScreen        ::class.java.name -> MenuScreen(game)
        HowToPlayScreen   ::class.java.name -> HowToPlayScreen(game)
        SettingScreen     ::class.java.name -> SettingScreen(game)
        LeaderboardScreen ::class.java.name -> LeaderboardScreen(game)
        AchivmentsScreen  ::class.java.name -> AchivmentsScreen(game)
        PlayScreen        ::class.java.name -> PlayScreen(game)
        GameScreen        ::class.java.name -> GameScreen(game)
        WonScreen         ::class.java.name -> WonScreen(game)

        else -> MenuScreen(game)
    }

}