package com.monkeystreet.roadracejungle.game.manager

import com.badlogic.gdx.Gdx
import com.monkeystreet.roadracejungle.game.GDXGame
import com.monkeystreet.roadracejungle.game.screens.GameScreen
import com.monkeystreet.roadracejungle.game.screens.LeaderboardScreen
import com.monkeystreet.roadracejungle.game.screens.LoaderScreen
import com.monkeystreet.roadracejungle.game.screens.MenuScreen
import com.monkeystreet.roadracejungle.game.screens.ResultScreen
import com.monkeystreet.roadracejungle.game.screens.RulesScreen
import com.monkeystreet.roadracejungle.game.utils.advanced.AdvancedScreen
import com.monkeystreet.roadracejungle.game.utils.runGDX

class NavigationManager(val game: GDXGame) {

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
        LoaderScreen     ::class.java.name -> LoaderScreen()
        MenuScreen       ::class.java.name -> MenuScreen()
        RulesScreen      ::class.java.name -> RulesScreen()
        GameScreen       ::class.java.name -> GameScreen()
        ResultScreen     ::class.java.name -> ResultScreen()
        LeaderboardScreen::class.java.name -> LeaderboardScreen()

        else -> MenuScreen()
    }

}