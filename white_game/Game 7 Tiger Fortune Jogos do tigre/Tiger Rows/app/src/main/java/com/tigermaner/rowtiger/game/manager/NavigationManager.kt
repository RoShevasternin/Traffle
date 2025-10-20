package com.tigermaner.rowtiger.game.manager

import com.badlogic.gdx.Gdx
import com.tigermaner.rowtiger.game.LibGDXGame
import com.tigermaner.rowtiger.game.screens.ValLevelScreen
import com.tigermaner.rowtiger.game.screens.ValLoaderScreen
import com.tigermaner.rowtiger.game.screens.ValMenuScreen
import com.tigermaner.rowtiger.game.screens.ValResultScreen
import com.tigermaner.rowtiger.game.screens.ValRulesScreen
import com.tigermaner.rowtiger.game.screens.ValSettingsScreen
import com.tigermaner.rowtiger.game.screens.level.ValLevel_1_Screen
import com.tigermaner.rowtiger.game.screens.level.ValLevel_2_Screen
import com.tigermaner.rowtiger.game.screens.level.ValLevel_3_Screen
import com.tigermaner.rowtiger.game.screens.level.ValLevel_4_Screen
import com.tigermaner.rowtiger.game.utils.advanced.AdvancedScreen
import com.tigermaner.rowtiger.game.utils.runGDX

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
        ValLoaderScreen  ::class.java.name -> ValLoaderScreen(game)
        ValMenuScreen    ::class.java.name -> ValMenuScreen(game)
        ValRulesScreen   ::class.java.name -> ValRulesScreen(game)
        ValSettingsScreen::class.java.name -> ValSettingsScreen(game)
        ValLevelScreen   ::class.java.name -> ValLevelScreen(game)
        ValResultScreen  ::class.java.name -> ValResultScreen(game)

        // Level
        ValLevel_1_Screen::class.java.name -> ValLevel_1_Screen(game)
        ValLevel_2_Screen::class.java.name -> ValLevel_2_Screen(game)
        ValLevel_3_Screen::class.java.name -> ValLevel_3_Screen(game)
        ValLevel_4_Screen::class.java.name -> ValLevel_4_Screen(game)

        else -> ValMenuScreen(game)
    }

}