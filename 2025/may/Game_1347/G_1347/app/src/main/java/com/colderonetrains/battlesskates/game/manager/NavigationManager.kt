package com.colderonetrains.battlesskates.game.manager

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.colderonetrains.battlesskates.game.screens.AchievementsScreen
import com.colderonetrains.battlesskates.game.screens.BattleSetupScreen
import com.colderonetrains.battlesskates.game.screens.GameScreen
import com.colderonetrains.battlesskates.game.screens.WelcomeScreen
import com.colderonetrains.battlesskates.game.screens.LoaderScreen
import com.colderonetrains.battlesskates.game.screens.HomeScreen
import com.colderonetrains.battlesskates.game.screens.RandomScreen
import com.colderonetrains.battlesskates.game.screens.TrickCatalogScreen
import com.colderonetrains.battlesskates.game.screens.TrickDescriptionScreen
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedScreen
import com.colderonetrains.battlesskates.game.utils.gdxGame
import com.colderonetrains.battlesskates.game.utils.runGDX

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
        LoaderScreen          ::class.java.name -> LoaderScreen()
        WelcomeScreen         ::class.java.name -> WelcomeScreen()
        HomeScreen            ::class.java.name -> HomeScreen()
        TrickCatalogScreen    ::class.java.name -> TrickCatalogScreen()
        TrickDescriptionScreen::class.java.name -> TrickDescriptionScreen()
        AchievementsScreen    ::class.java.name -> AchievementsScreen()
        RandomScreen          ::class.java.name -> RandomScreen()
        BattleSetupScreen     ::class.java.name -> BattleSetupScreen()
        GameScreen            ::class.java.name -> GameScreen()

        else -> WelcomeScreen()
    }

}