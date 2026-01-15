package com.cosmostarsca.quantumfruits.game.manager

import com.badlogic.gdx.Gdx
import com.cosmostarsca.quantumfruits.game.LibGDXGame
import com.cosmostarsca.quantumfruits.game.screens.*
import com.cosmostarsca.quantumfruits.game.utils.advanced.AdvancedScreen
import com.cosmostarsca.quantumfruits.game.utils.runGDX

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


    fun exit() = runGDX { Gdx.app.exit() }


    fun isBackStackEmpty() = backStack.isEmpty()

    private fun getScreenByName(name: String): AdvancedScreen = when(name) {
        StartScreen::class.java.name -> StartScreen(game)
        MenuScreen ::class.java.name -> MenuScreen(game)
        RulesScreen::class.java.name -> RulesScreen(game)
        SettingsScreen::class.java.name -> SettingsScreen(game)
        DishchScreen::class.java.name -> DishchScreen(game)

        else -> MenuScreen(game)
    }

}