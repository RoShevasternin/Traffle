package com.bigfish.pairtoper.game.manager

import com.badlogic.gdx.Gdx
import com.bigfish.pairtoper.game.screens.GameScreen
import com.bigfish.pairtoper.game.screens.LoaderScreen
import com.bigfish.pairtoper.game.screens.MenuScreen
import com.bigfish.pairtoper.game.screens.ResultFailScreen
import com.bigfish.pairtoper.game.screens.ResultDoneScreen
import com.bigfish.pairtoper.game.screens.SelecteScreen
import com.bigfish.pairtoper.game.screens.SettScreen
import com.bigfish.pairtoper.game.utils.advanced.AdvancedScreen
import com.bigfish.pairtoper.game.utils.gdxGame
import com.bigfish.pairtoper.game.utils.runGDX

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
        SettScreen       ::class.java.name -> SettScreen()
        GameScreen      ::class.java.name -> GameScreen()
        ResultDoneScreen ::class.java.name -> ResultDoneScreen()
        ResultFailScreen   ::class.java.name -> ResultFailScreen()
        SelecteScreen::class.java.name -> SelecteScreen()

        else -> MenuScreen()
    }

}