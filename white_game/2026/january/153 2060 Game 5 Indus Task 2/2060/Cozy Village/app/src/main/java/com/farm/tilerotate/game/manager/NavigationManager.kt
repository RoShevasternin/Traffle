package com.farm.tilerotate.game.manager

import com.badlogic.gdx.Gdx
import com.farm.tilerotate.game.screens.GameScreen
import com.farm.tilerotate.game.screens.LoaderScreen
import com.farm.tilerotate.game.screens.MenuScreen
import com.farm.tilerotate.game.screens.ResultFailScreen
import com.farm.tilerotate.game.screens.ResultDoneScreen
import com.farm.tilerotate.game.screens.SelecteScreen
import com.farm.tilerotate.game.screens.SettScreen
import com.farm.tilerotate.game.utils.advanced.AdvancedScreen
import com.farm.tilerotate.game.utils.gdxGame
import com.farm.tilerotate.game.utils.runGDX

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