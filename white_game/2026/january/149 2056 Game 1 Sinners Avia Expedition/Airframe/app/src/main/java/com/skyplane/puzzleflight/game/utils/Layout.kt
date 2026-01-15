package com.skyplane.puzzleflight.game.utils

import com.badlogic.gdx.math.Vector2

object Layout {

    object Splash {
        val loading = LayoutData(86f,147f,476f,55f)
    }

    data class LayoutData(
        var x: Float = 0f,
        var y: Float = 0f,
        var w: Float = 0f,
        var h: Float = 0f,
        // horizontal space
        val hs: Float = 0f,
        // vertical space
        val vs: Float = 0f,
    ) {

        fun position() = Vector2(x, y)
        fun size() = Vector2(w, h)

    }

}












