package com.puzzlertron.dohistorical.game.manager

import com.badlogic.gdx.Gdx
import com.puzzlertron.dohistorical.game.GDXGame
import com.puzzlertron.dohistorical.game.screens.GameScreen
import com.puzzlertron.dohistorical.game.screens.LoaderScreen
import com.puzzlertron.dohistorical.game.screens.LoseScreen
import com.puzzlertron.dohistorical.game.screens.MenuScreen
import com.puzzlertron.dohistorical.game.screens.RulesScreen
import com.puzzlertron.dohistorical.game.screens.WinScreen
import com.puzzlertron.dohistorical.game.utils.advanced.AdvancedScreen
import com.puzzlertron.dohistorical.game.utils.runGDX

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

    fun clearBackStack() = backStack.clear()

    fun isBackStackEmpty() = backStack.isEmpty()

    private fun getScreenByName(name: String): AdvancedScreen = when(name) {
        LoaderScreen  ::class.java.name -> LoaderScreen()
        MenuScreen    ::class.java.name -> MenuScreen()
        RulesScreen ::class.java.name -> RulesScreen()
        GameScreen    ::class.java.name -> GameScreen()
        WinScreen     ::class.java.name -> WinScreen()
        LoseScreen    ::class.java.name -> LoseScreen()

        else -> MenuScreen()
    }

}