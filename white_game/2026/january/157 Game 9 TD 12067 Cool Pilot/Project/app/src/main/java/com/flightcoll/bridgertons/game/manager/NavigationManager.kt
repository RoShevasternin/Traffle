package com.flightcoll.bridgertons.game.manager

import com.badlogic.gdx.Gdx
import com.flightcoll.bridgertons.game.SuberGame
import com.flightcoll.bridgertons.game.screens.*
import com.flightcoll.bridgertons.game.utils.advanced.AdvancedScreen
import com.flightcoll.bridgertons.game.utils.runGDX

class NavigationManager(val game: SuberGame) {

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
        ZagruzonScreen  ::class.java.name -> ZagruzonScreen(game)
        MunhenesiScreen ::class.java.name -> MunhenesiScreen(game)
        RuleroScreen    ::class.java.name -> RuleroScreen(game)
        StertoyScreen   ::class.java.name -> StertoyScreen(game)
        IglaScreen      ::class.java.name -> IglaScreen(game)

        else -> MunhenesiScreen(game)
    }

}