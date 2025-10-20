package com.destroyer.catapulter.game.manager

import com.badlogic.gdx.Gdx
import com.destroyer.catapulter.game.LibGDXGame
import com.destroyer.catapulter.game.screens.CatapultaGameScreen
import com.destroyer.catapulter.game.screens.CatapultaLoaderScreen
import com.destroyer.catapulter.game.screens.CatapultaMenuScreen
import com.destroyer.catapulter.game.screens.FirstScreen
import com.destroyer.catapulter.game.screens.RulesScreen
import com.destroyer.catapulter.game.utils.advanced.AdvancedScreen
import com.destroyer.catapulter.game.utils.runGDX

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
        CatapultaLoaderScreen::class.java.name -> CatapultaLoaderScreen(game)
        CatapultaMenuScreen  ::class.java.name -> CatapultaMenuScreen(game)
        CatapultaGameScreen  ::class.java.name -> CatapultaGameScreen(game)
        FirstScreen::class.java.name -> FirstScreen(game)
        RulesScreen::class.java.name -> RulesScreen(game)

        else                                   -> CatapultaGameScreen(game)
    }

}


