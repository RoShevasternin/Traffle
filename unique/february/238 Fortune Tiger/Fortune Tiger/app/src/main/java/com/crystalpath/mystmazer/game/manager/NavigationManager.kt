package com.crystalpath.mystmazer.game.manager

import com.badlogic.gdx.Gdx
import com.crystalpath.mystmazer.game.screens.GalleryScreen
import com.crystalpath.mystmazer.game.screens.GameScreen
import com.crystalpath.mystmazer.game.screens.MenuScreen
import com.crystalpath.mystmazer.game.screens.LoaderScreen
import com.crystalpath.mystmazer.game.screens.PlayScreen
import com.crystalpath.mystmazer.game.screens.ProfileScreen
import com.crystalpath.mystmazer.game.screens.SettingsScreen
import com.crystalpath.mystmazer.game.screens.ShopScreen
import com.crystalpath.mystmazer.game.screens.Wheel_of_FortuneScreen
import com.crystalpath.mystmazer.game.utils.advanced.AdvancedScreen
import com.crystalpath.mystmazer.game.utils.gdxGame
import com.crystalpath.mystmazer.game.utils.runGDX

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

    private fun getScreenByName(name: String): AdvancedScreen = when(name) {
        LoaderScreen          ::class.java.name -> LoaderScreen()
        MenuScreen            ::class.java.name -> MenuScreen()
        Wheel_of_FortuneScreen::class.java.name -> Wheel_of_FortuneScreen()
        ProfileScreen         ::class.java.name -> ProfileScreen()
        ShopScreen            ::class.java.name -> ShopScreen()
        GalleryScreen         ::class.java.name -> GalleryScreen()
        SettingsScreen        ::class.java.name -> SettingsScreen()
        PlayScreen            ::class.java.name -> PlayScreen()
        GameScreen            ::class.java.name -> GameScreen()

        else -> MenuScreen()
    }

}