package com.skynebowow.runnerblue.game.manager

import com.badlogic.gdx.Gdx
import com.skynebowow.runnerblue.game.GDXGame
import com.skynebowow.runnerblue.game.screens.GameScreen
import com.skynebowow.runnerblue.game.screens.LoseScreen
import com.skynebowow.runnerblue.game.screens.MenuScreen
import com.skynebowow.runnerblue.game.screens.RulesScreen
import com.skynebowow.runnerblue.game.screens.ShopScreen
import com.skynebowow.runnerblue.game.screens.SplashcScreen
import com.skynebowow.runnerblue.game.screens.WinScreen
import com.skynebowow.runnerblue.game.utils.advanced.AdvancedScreen
import com.skynebowow.runnerblue.game.utils.runGDX

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
        SplashcScreen ::class.java.name -> SplashcScreen(game)
        MenuScreen    ::class.java.name -> MenuScreen(game)
        RulesScreen   ::class.java.name -> RulesScreen(game)
        ShopScreen    ::class.java.name -> ShopScreen(game)
        GameScreen    ::class.java.name -> GameScreen(game)
        WinScreen     ::class.java.name -> WinScreen(game)
        LoseScreen    ::class.java.name -> LoseScreen(game)

        else -> MenuScreen(game)
    }

}