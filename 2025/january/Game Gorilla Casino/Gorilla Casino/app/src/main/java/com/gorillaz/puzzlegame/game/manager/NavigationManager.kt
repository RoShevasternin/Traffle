package com.gorillaz.puzzlegame.game.manager

import com.badlogic.gdx.Gdx
import com.gorillaz.puzzlegame.game.screens.GalleryScreen
import com.gorillaz.puzzlegame.game.screens.GameScreen
import com.gorillaz.puzzlegame.game.screens.MenuScreen
import com.gorillaz.puzzlegame.game.screens.LoaderScreen
import com.gorillaz.puzzlegame.game.screens.PlayScreen
import com.gorillaz.puzzlegame.game.screens.ProfileScreen
import com.gorillaz.puzzlegame.game.screens.SettingsScreen
import com.gorillaz.puzzlegame.game.screens.ShopScreen
import com.gorillaz.puzzlegame.game.screens.Wheel_of_FortuneScreen
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedScreen
import com.gorillaz.puzzlegame.game.utils.gdxGame
import com.gorillaz.puzzlegame.game.utils.runGDX

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