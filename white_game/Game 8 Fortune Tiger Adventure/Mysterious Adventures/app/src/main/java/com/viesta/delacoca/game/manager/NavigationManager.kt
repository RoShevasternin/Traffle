package com.viesta.delacoca.game.manager

import com.badlogic.gdx.Gdx
import com.viesta.delacoca.game.LibGDXGame
import com.viesta.delacoca.game.screens.IncasExitScreen
import com.viesta.delacoca.game.screens.IncasLoadingScreen
import com.viesta.delacoca.game.screens.IncasResultScreen
import com.viesta.delacoca.game.screens.common.IncasLevelScreen
import com.viesta.delacoca.game.screens.common.IncasMenuScreen
import com.viesta.delacoca.game.screens.common.IncasRulesScreen
import com.viesta.delacoca.game.screens.common.IncasSettingsScreen
import com.viesta.delacoca.game.screens.level.IncasEasyScreen
import com.viesta.delacoca.game.screens.level.IncasHardScreen
import com.viesta.delacoca.game.screens.level.IncasNormalScreen
import com.viesta.delacoca.game.utils.advanced.AdvancedScreen
import com.viesta.delacoca.game.utils.runGDX

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
        IncasLoadingScreen ::class.java.name -> IncasLoadingScreen(game)
        IncasExitScreen    ::class.java.name -> IncasExitScreen(game)
        IncasMenuScreen    ::class.java.name -> IncasMenuScreen(game)
        IncasRulesScreen   ::class.java.name -> IncasRulesScreen(game)
        IncasSettingsScreen::class.java.name -> IncasSettingsScreen(game)
        IncasLevelScreen   ::class.java.name -> IncasLevelScreen(game)
        IncasResultScreen  ::class.java.name -> IncasResultScreen(game)

        // Levels
        IncasEasyScreen  ::class.java.name -> IncasEasyScreen(game)
        IncasNormalScreen::class.java.name -> IncasNormalScreen(game)
        IncasHardScreen  ::class.java.name -> IncasHardScreen(game)

        else -> IncasMenuScreen(game)
    }

}