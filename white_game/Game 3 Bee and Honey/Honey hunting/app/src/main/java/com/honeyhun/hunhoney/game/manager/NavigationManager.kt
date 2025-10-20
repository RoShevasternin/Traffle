package com.honeyhun.hunhoney.game.manager

import com.badlogic.gdx.Gdx
import com.honeyhun.hunhoney.game.LibGDXGame
import com.honeyhun.hunhoney.game.screens.BeeGameScreen
import com.honeyhun.hunhoney.game.screens.BeeLoaderScreen
import com.honeyhun.hunhoney.game.screens.BeeMenuScreen
import com.honeyhun.hunhoney.game.screens.BeeRulesScreen
import com.honeyhun.hunhoney.game.utils.advanced.AdvancedScreen
import com.honeyhun.hunhoney.game.utils.runGDX

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
        BeeLoaderScreen::class.java.name -> BeeLoaderScreen(game)
        BeeMenuScreen  ::class.java.name -> BeeMenuScreen(game)
        BeeGameScreen  ::class.java.name -> BeeGameScreen(game)
        BeeRulesScreen ::class.java.name -> BeeRulesScreen(game)

        else -> BeeGameScreen(game)
    }

}


