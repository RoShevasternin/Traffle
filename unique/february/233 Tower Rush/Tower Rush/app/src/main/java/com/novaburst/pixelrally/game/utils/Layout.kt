package com.novaburst.pixelrally.game.utils

import com.badlogic.gdx.math.Vector2

object Layout {

    object Slot {
        val slot = LayoutData(0f, 0f, 160f, 160f, vs = 0f)
        val endY = -3355f
    }
    object Glow {
        val glow = LayoutData(0f, 0f, 234f, 228f, vs = -2f)
    }
    object SlotGroup {
        val slot = LayoutData(23f, 49f, 160f, 3840f, hs = 50f)
        val glow = LayoutData(0f, 0f, 234f, 680f, hs = 1f)
    }

    data class LayoutData(
        val x: Float = 0f,
        val y: Float = 0f,
        val w: Float = 0f,
        val h: Float = 0f,
        // horizontal space
        val hs: Float = 0f,
        // vertical space
        val vs: Float = 0f,
    ) {

        fun position() = Vector2(x, y)
        fun size() = Vector2(w, h)

    }

}












