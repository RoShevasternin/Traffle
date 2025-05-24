package com.pink.plinuirtaster.game.manager

import com.badlogic.gdx.Gdx
import com.pink.plinuirtaster.game.LibGDXGame
import com.pink.plinuirtaster.game.screens.*
import com.pink.plinuirtaster.game.utils.advanced.AdvancedScreen
import com.pink.plinuirtaster.game.utils.runGDX

class NavigationManager(val game: LibGDXGame) {

    val backStack = mutableListOf<String>()
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
        SplashScreen   ::class.java.name -> SplashScreen(game)
        MenuScreen     ::class.java.name -> MenuScreen(game)
        RulesScreen    ::class.java.name -> RulesScreen(game)
        NamesScreen    ::class.java.name -> NamesScreen(game)
        ScoreScreen    ::class.java.name -> ScoreScreen(game)
        ShakeScreen    ::class.java.name -> ShakeScreen(game)
        ChooseScreen   ::class.java.name -> ChooseScreen(game)
        TaskScreen     ::class.java.name -> TaskScreen(game)
        BonusScreen    ::class.java.name -> BonusScreen(game)
        SettingsScreen ::class.java.name -> SettingsScreen(game)

        else -> MenuScreen(game)
    }

}