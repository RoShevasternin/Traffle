package com.toyscatcher.factoryatoy.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.toyscatcher.factoryatoy.assets.SpriteManager
import com.toyscatcher.factoryatoy.game

object NavigationUtil {

    private val backStack = mutableListOf<Screen>()
    var key: Int? = null
        private set

    fun navigate(to: Screen, from: Screen? = null, key: Int? = null) {
        NavigationUtil.key = key

        game.screen = to
        from?.let { backStack.add(it) }
        if (backStack.contains(to)) backStack.remove(to)
    }

    fun back(key: Int? = null) {
        NavigationUtil.key = key

        if (backStack.isEmpty()) exit()
        else game.screen = backStack.removeAt(backStack.lastIndex)
    }

    fun exit() {
        backStack.clear()
        Gdx.app.exit()
    }

}