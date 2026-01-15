package com.catchroyal.oceancatcher.assets.util

import com.badlogic.gdx.assets.AssetManager

class AssetManagerGroup(
    private vararg val managers: AssetManager
) {

    private var commonProgress = 0f

    val progress: Float get() = commonProgress / managers.size
    val isFinished: Boolean get() = managers.all { it.isFinished }

    fun update(): Boolean {
        commonProgress = 0f
        managers.onEach {
            it.update()
            commonProgress += it.progress
        }

        return managers.all { it.isFinished }
    }

}
