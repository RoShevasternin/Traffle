package com.vortemika208.lavrix.game.actors.slotGroup

import com.badlogic.gdx.math.Vector2

object Layout {

    object Slot {
        val slot = LayoutData(0f, 0f, 155f, 155f, vs = 55f)
        val endY = -3447f
    }
    object SlotGroup {
        val slot = LayoutData(53f, 123f, 155f, 4145f, hs = 60f)
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












