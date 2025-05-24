package com.viade.bepuzzle.game.manager

import com.badlogic.gdx.Gdx
import com.viade.bepuzzle.game.GDXGame
import com.viade.bepuzzle.game.screens.*
import com.viade.bepuzzle.game.utils.advanced.AdvancedScreen
import com.viade.bepuzzle.game.utils.runGDX

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

    fun isBackStackEmpty() = backStack.isEmpty()

    fun clearBackStack() = backStack.clear()

    private fun getScreenByName(name: String): AdvancedScreen = when(name) {
        LoaderScreen      ::class.java.name -> LoaderScreen(game)
        MenuScreen        ::class.java.name -> MenuScreen(game)
        SelectLevelScreen ::class.java.name -> SelectLevelScreen(game)
        InAppScreen       ::class.java.name -> InAppScreen(game)
        SettingsScreen    ::class.java.name -> SettingsScreen(game)
        ShopScreen        ::class.java.name -> ShopScreen(game)
        GalleryScreen     ::class.java.name -> GalleryScreen(game)
        GameScreen        ::class.java.name -> GameScreen(game)
        AwordsScreen      ::class.java.name -> AwordsScreen(game)

        else -> MenuScreen(game)
    }

}