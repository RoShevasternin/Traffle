package com.farm.puzzletiles.game.manager

import com.badlogic.gdx.Gdx
import com.farm.puzzletiles.game.screens.PuzzleScreen
import com.farm.puzzletiles.game.screens.LoaderScreen
import com.farm.puzzletiles.game.screens.MenuScreen
import com.farm.puzzletiles.game.screens.ResultFailScreen
import com.farm.puzzletiles.game.screens.ResultDoneScreen
import com.farm.puzzletiles.game.screens.RulesScreen
import com.farm.puzzletiles.game.utils.advanced.AdvancedScreen
import com.farm.puzzletiles.game.utils.gdxGame
import com.farm.puzzletiles.game.utils.runGDX

class NavigationManager {

    private val backStack = mutableListOf<String>()

    fun navigate(toScreenName: String, fromScreenName: String? = null) = runGDX {
        gdxGame.updateScreen(getScreenByName(toScreenName))
        backStack.filter { name -> name == toScreenName }.onEach { name -> backStack.remove(name) }
        fromScreenName?.let { fromName ->
            backStack.filter { name -> name == fromName }.onEach { name -> backStack.remove(name) }
            backStack.add(fromName)
        }
    }

    fun back() = runGDX {
        if (isBackStackEmpty()) exit() else gdxGame.updateScreen(getScreenByName(backStack.removeAt(backStack.lastIndex)))
    }

    fun exit() = runGDX { Gdx.app.exit() }


    fun isBackStackEmpty() = backStack.isEmpty()
    fun clearBackStack() = backStack.clear()

    private fun getScreenByName(name: String): AdvancedScreen = when(name) {
        LoaderScreen      ::class.java.name -> LoaderScreen()
        MenuScreen        ::class.java.name -> MenuScreen()
        RulesScreen       ::class.java.name -> RulesScreen()
        PuzzleScreen      ::class.java.name -> PuzzleScreen()
        ResultDoneScreen ::class.java.name -> ResultDoneScreen()
        ResultFailScreen   ::class.java.name -> ResultFailScreen()

        else -> MenuScreen()
    }

}