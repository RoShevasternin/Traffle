package com.slotscity.official.game.manager

import com.badlogic.gdx.Gdx
import com.slotscity.official.game.LibGDXGame
import com.slotscity.official.game.screens.games.GatesOfOlympusScreen
import com.slotscity.official.game.screens.LoaderScreen
import com.slotscity.official.game.screens.MenuScreen
import com.slotscity.official.game.screens.games.CarnavalCatScreen
import com.slotscity.official.game.screens.games.SweetBonanzaScreen
import com.slotscity.official.game.screens.games.TreasureSnipesScreen
import com.slotscity.official.game.utils.advanced.AdvancedScreen
import com.slotscity.official.game.utils.runGDX

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

        if (isBackStackEmpty()) exit() else game.updateScreen(getScreenByName(backStack.removeLast()))
    }


    fun exit() = runGDX { Gdx.app.exit() }


    fun isBackStackEmpty() = backStack.isEmpty()

    private fun getScreenByName(name: String): AdvancedScreen = when (name) {
        LoaderScreen::class.java.name -> LoaderScreen(game)

        // Games
        GatesOfOlympusScreen::class.java.name -> GatesOfOlympusScreen(game)
        CarnavalCatScreen::class.java.name    -> CarnavalCatScreen(game)
        SweetBonanzaScreen::class.java.name   -> SweetBonanzaScreen(game)
        TreasureSnipesScreen::class.java.name -> TreasureSnipesScreen(game)

        else -> MenuScreen(game)
    }

}