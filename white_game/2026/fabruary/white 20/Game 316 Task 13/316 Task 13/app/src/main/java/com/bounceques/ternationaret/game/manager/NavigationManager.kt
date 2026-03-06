package com.bounceques.ternationaret.game.manager

import com.badlogic.gdx.Gdx
import com.bounceques.ternationaret.game.LibGDXGame
import com.bounceques.ternationaret.game.screens.LoseScreen
import com.bounceques.ternationaret.game.screens.PinkLoaderScreen
import com.bounceques.ternationaret.game.screens.PinkMenuScreen
import com.bounceques.ternationaret.game.screens.PinkRulesScreen
import com.bounceques.ternationaret.game.screens.PinkYrowniScreen
import com.bounceques.ternationaret.game.screens.WinScreen
import com.bounceques.ternationaret.game.screens.levels._1_Screen
import com.bounceques.ternationaret.game.screens.levels._2_Screen
import com.bounceques.ternationaret.game.screens.levels._3_Screen
import com.bounceques.ternationaret.game.screens.levels._4_Screen
import com.bounceques.ternationaret.game.utils.advanced.AdvancedScreen
import com.bounceques.ternationaret.game.utils.runGDX

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
        PinkLoaderScreen   ::class.java.name -> PinkLoaderScreen(game)
        PinkMenuScreen     ::class.java.name -> PinkMenuScreen(game)
        PinkRulesScreen    ::class.java.name -> PinkRulesScreen(game)
        PinkYrowniScreen   ::class.java.name -> PinkYrowniScreen(game)
        WinScreen          ::class.java.name -> WinScreen(game)
        LoseScreen         ::class.java.name -> LoseScreen(game)

        // ilevel
        _1_Screen::class.java.name -> _1_Screen(game)
        _2_Screen::class.java.name -> _2_Screen(game)
        _3_Screen::class.java.name -> _3_Screen(game)
        _4_Screen::class.java.name -> _4_Screen(game)

        else -> PinkMenuScreen(game)
    }

}


