package com.smallmini.races.gonca.game.manager

import com.badlogic.gdx.Gdx
import com.smallmini.races.gonca.game.LibGDXGame
import com.smallmini.races.gonca.game.screens.MiniGameScreen
import com.smallmini.races.gonca.game.screens.MiniLoaderScreen
import com.smallmini.races.gonca.game.screens.MiniMenuScreen
import com.smallmini.races.gonca.game.screens.MiniRulesScreen
import com.smallmini.races.gonca.game.screens.MiniSettScreen
import com.smallmini.races.gonca.game.utils.advanced.AdvancedScreen
import com.smallmini.races.gonca.game.utils.runGDX

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
        MiniLoaderScreen::class.java.name -> MiniLoaderScreen(game)
        MiniGameScreen  ::class.java.name -> MiniGameScreen(game)
        MiniMenuScreen  ::class.java.name -> MiniMenuScreen(game)
        MiniRulesScreen ::class.java.name -> MiniRulesScreen(game)
        MiniSettScreen  ::class.java.name -> MiniSettScreen(game)

        else                                -> MiniLoaderScreen(game)
    }

}


