package com.pinlq.esst.bloo.game.manager

import com.badlogic.gdx.Gdx
import com.pinlq.esst.bloo.game.LibGDXGame
import com.pinlq.esst.bloo.game.screens.*
import com.pinlq.esst.bloo.game.utils.advanced.AdvancedScreen
import com.pinlq.esst.bloo.game.utils.runGDX

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
        SplashScreen       ::class.java.name -> SplashScreen(game)
        MenuScreen         ::class.java.name -> MenuScreen(game)
        RulesScreen        ::class.java.name -> RulesScreen(game)
        NumOfPlayersScreen ::class.java.name -> NumOfPlayersScreen(game)
        SelectPlayersScreen::class.java.name -> SelectPlayersScreen(game)
        ShakeScreen        ::class.java.name -> ShakeScreen(game)
        CharacterScreen    ::class.java.name -> CharacterScreen(game)
        CharacterShowScreen::class.java.name -> CharacterShowScreen(game)
        SubjectScreen      ::class.java.name -> SubjectScreen(game)
        SubjectShowScreen  ::class.java.name -> SubjectShowScreen(game)
        QuestScreen        ::class.java.name -> QuestScreen(game)
        QuestShowScreen    ::class.java.name -> QuestShowScreen(game)
        SettScreen         ::class.java.name -> SettScreen(game)
        CollectionScreen         ::class.java.name -> CollectionScreen(game)

        else -> MenuScreen(game)
    }

}