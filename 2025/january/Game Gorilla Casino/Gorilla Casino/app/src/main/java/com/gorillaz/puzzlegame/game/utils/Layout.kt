package com.gorillaz.puzzlegame.game.utils

import com.badlogic.gdx.math.Vector2

object Layout {

    object Slot {
        val slot = LayoutData(0f, 0f, 305f, 305f, vs = -90f)
        val endY = -3268f
    }
    object Glow {
        val glow = LayoutData(0f, 0f, 234f, 228f, vs = -2f)
    }
    object SlotGroup {
        val slot = LayoutData(-43f, -43f, 305f, 3960f, hs = -90f)
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












