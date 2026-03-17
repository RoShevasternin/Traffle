package com.vortemika208.lavrix.game.manager

import com.badlogic.gdx.Gdx
import com.vortemika208.lavrix.game.screens.SettingsScreen
import com.vortemika208.lavrix.game.screens.GameChampionsScreen
import com.vortemika208.lavrix.game.screens.LoaderScreen
import com.vortemika208.lavrix.game.screens.MenuScreen
import com.vortemika208.lavrix.game.screens.ShopScreen
import com.vortemika208.lavrix.game.utils.advanced.AdvancedScreen
import com.vortemika208.lavrix.game.utils.gdxGame
import com.vortemika208.lavrix.game.utils.runGDX

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
        LoaderScreen        ::class.java.name -> LoaderScreen()
        MenuScreen          ::class.java.name -> MenuScreen()
        SettingsScreen      ::class.java.name -> SettingsScreen()
        ShopScreen          ::class.java.name -> ShopScreen()
        GameChampionsScreen ::class.java.name -> GameChampionsScreen()

        else -> MenuScreen()
    }

}