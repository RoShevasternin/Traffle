package com.fontantwo.tennisfortwo.game.utils

import com.badlogic.gdx.math.Vector2

object Layout {

    object Tutorial {
        object Menu {
            val rules       = LayoutData(387f, 508f, 830f, 156f)
            val settings    = LayoutData(387f, 356f, 830f, 156f)
            val games       = LayoutData(387f, 656f, 830f, 156f)
            val fruitRecord = LayoutData(283f, 28f, 947f, 279f)
        }
        object Rules {
            val next = LayoutData(58f, 218f, 1208f, 476f)
            val back = LayoutData(196f, 381f, 784f, 402f)
        }
        object Bonus {
            val records = LayoutData(52f, 99f, 308f, 746f)
        }
        object Levels {
            val locked = LayoutData(287f, 229f, 1030f, 192f)
        }
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
        fun size()     = Vector2(w, h)

    }

}












