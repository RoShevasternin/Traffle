package com.senqorvia774.lottomatica.game.manager

import com.badlogic.gdx.Gdx
import com.senqorvia774.lottomatica.game.screens.GameAdventuresScreen
import com.senqorvia774.lottomatica.game.screens.SettingsScreen
import com.senqorvia774.lottomatica.game.screens.GameChampionsScreen
import com.senqorvia774.lottomatica.game.screens.LoaderScreen
import com.senqorvia774.lottomatica.game.screens.MenuScreen
import com.senqorvia774.lottomatica.game.screens.ShopScreen
import com.senqorvia774.lottomatica.game.utils.advanced.AdvancedScreen
import com.senqorvia774.lottomatica.game.utils.gdxGame
import com.senqorvia774.lottomatica.game.utils.runGDX

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
        GameAdventuresScreen::class.java.name -> GameAdventuresScreen()

        else -> MenuScreen()
    }

}