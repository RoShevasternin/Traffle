package com.swee.ttrio.comb.game.manager

import com.badlogic.gdx.Gdx
import com.swee.ttrio.comb.game.LibGDXGame
import com.swee.ttrio.comb.game.screens.*
import com.swee.ttrio.comb.game.utils.advanced.AdvancedScreen
import com.swee.ttrio.comb.game.utils.runGDX

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
        AchievementsScreen::class.java.name -> AchievementsScreen(game)
        StartScreen       ::class.java.name -> StartScreen(game)
        GameScreen        ::class.java.name -> GameScreen(game)
        WinsScreen        ::class.java.name -> WinsScreen(game)

        else -> MenuScreen(game)
    }

}