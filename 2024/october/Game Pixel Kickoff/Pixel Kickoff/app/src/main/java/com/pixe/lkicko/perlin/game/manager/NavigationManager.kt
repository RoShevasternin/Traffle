package com.pixe.lkicko.perlin.game.manager

import com.badlogic.gdx.Gdx
import com.pixe.lkicko.perlin.game.LibGDXGame
import com.pixe.lkicko.perlin.game.screens.*
import com.pixe.lkicko.perlin.game.utils.advanced.AdvancedScreen
import com.pixe.lkicko.perlin.game.utils.runGDX

class NavigationManager(val game: LibGDXGame) {

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

    fun clearBackStack() {
        backStack.clear()
    }


    fun exit() = runGDX { Gdx.app.exit() }


    fun isBackStackEmpty() = backStack.isEmpty()

    private fun getScreenByName(name: String): AdvancedScreen = when (name) {
        LoaderScreen     ::class.java.name -> LoaderScreen(game)
        MenuScreen       ::class.java.name -> MenuScreen(game)
        PlayScreen       ::class.java.name -> PlayScreen(game)
        GameScreen       ::class.java.name -> GameScreen(game)
        ShopScreen       ::class.java.name -> ShopScreen(game)
        DailyBonusScreen ::class.java.name -> DailyBonusScreen(game)
        CoinsScreen      ::class.java.name -> CoinsScreen(game)
        SettingsScreen   ::class.java.name -> SettingsScreen(game)
        AwardsScreen     ::class.java.name -> AwardsScreen(game)
        GalleryScreen    ::class.java.name -> GalleryScreen(game)

        else -> MenuScreen(game)
    }

}