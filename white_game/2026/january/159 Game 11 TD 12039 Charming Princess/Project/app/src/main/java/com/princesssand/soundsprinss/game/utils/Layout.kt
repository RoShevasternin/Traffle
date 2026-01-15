package com.princesssand.soundsprinss.game.utils

import com.badlogic.gdx.math.Vector2

object Layout {

    object Tutorial {
        object Menu {
            val rules    = LayoutData(490f, 420f, 268f, 107f)
            val settings = LayoutData(490f, 528f, 268f, 107f)
            val levels   = LayoutData(490f, 637f, 268f, 107f)
        }
        object Rules {
            val back = LayoutData(196f, 381f, 784f, 402f)
        }
        object Levels {
            val locked = LayoutData(395f, 499f, 199f, 199f)
        }
        object Game {
            val ball = LayoutData(704f, 391f, 118f, 118f)
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












