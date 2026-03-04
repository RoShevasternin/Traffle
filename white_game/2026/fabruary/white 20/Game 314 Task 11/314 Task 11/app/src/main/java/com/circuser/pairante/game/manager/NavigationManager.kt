package com.circuser.pairante.game.manager

import com.badlogic.gdx.Gdx
import com.circuser.pairante.game.GDXGame
import com.circuser.pairante.game.screens.GameScreen
import com.circuser.pairante.game.screens.LoaderScreen
import com.circuser.pairante.game.screens.MenuScreen
import com.circuser.pairante.game.screens.ResultDoneScreen
import com.circuser.pairante.game.screens.ResultFailScreen
import com.circuser.pairante.game.screens.SelecteScreen
import com.circuser.pairante.game.screens.SettingsScreen
import com.circuser.pairante.game.utils.advanced.AdvancedScreen
import com.circuser.pairante.game.utils.runGDX

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
        SettingsScreen::class.java.name -> SettingsScreen()
        SelecteScreen ::class.java.name -> SelecteScreen()
        GameScreen    ::class.java.name -> GameScreen()
        ResultDoneScreen::class.java.name -> ResultDoneScreen()
        ResultFailScreen::class.java.name -> ResultFailScreen()

        else -> MenuScreen()
    }

}