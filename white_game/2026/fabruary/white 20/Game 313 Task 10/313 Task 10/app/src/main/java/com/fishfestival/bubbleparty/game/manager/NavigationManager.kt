package com.fishfestival.bubbleparty.game.manager

import com.badlogic.gdx.Gdx
import com.fishfestival.bubbleparty.game.GDXGame
import com.fishfestival.bubbleparty.game.screens.GameScreen
import com.fishfestival.bubbleparty.game.screens.LoaderScreen
import com.fishfestival.bubbleparty.game.screens.MenuScreen
import com.fishfestival.bubbleparty.game.screens.ResultScreen
import com.fishfestival.bubbleparty.game.screens.RulesScreen
import com.fishfestival.bubbleparty.game.screens.LiaderScreen
import com.fishfestival.bubbleparty.game.utils.advanced.AdvancedScreen
import com.fishfestival.bubbleparty.game.utils.runGDX

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
        LoaderScreen::class.java.name -> LoaderScreen()
        MenuScreen  ::class.java.name -> MenuScreen()
        RulesScreen ::class.java.name -> RulesScreen()
        ResultScreen::class.java.name -> ResultScreen()
        LiaderScreen::class.java.name -> LiaderScreen()

        else -> MenuScreen()
    }

}