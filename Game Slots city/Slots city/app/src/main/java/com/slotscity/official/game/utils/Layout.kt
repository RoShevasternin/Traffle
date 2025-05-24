package com.slotscity.official.game.utils

import com.badlogic.gdx.math.Vector2

object Layout {

    object Splash {
        val progress = LayoutData(365f,399f,988f,282f)
    }

    object Slot {
        val slot = LayoutData(0f, 0f, 150f, 150f, vs = 10f)
        val endY = -3163f
    }

    object Glow {
        val glow = LayoutData(0f, 0f, 237f, 182f, vs = -22f)
    }

    object SlotGroup {
        val slot = LayoutData(44f, 37f, 150f, 3990f, hs = 76f)
        val glow = LayoutData(1f, 21f, 237f, 822f, hs = -11f)
    }

    object CarnavalCat {
        object Slot {
            val slot = LayoutData(0f, 0f, 234f, 228f, vs = 1f)
            val endY = -2748f
        }
        object Glow {
            val glow = LayoutData(0f, 0f, 234f, 228f, vs = -2f)
        }
        object SlotGroup {
            val slot = LayoutData(0f, 0f, 234f, 3434f, hs = 1f)
            val glow = LayoutData(0f, 0f, 234f, 680f, hs = 1f)
        }
    }

    object TreasureSnipes {
        object Slot {
            val slot = LayoutData(0f, 0f, 200f, 200f, vs = 1f)
            val endY = -3803f
        }
        object Glow {
            val glow = LayoutData(0f, 0f, 216f, 216f, vs = -15f)
        }
        object SlotGroup {
            val slot = LayoutData(49f, 16f, 200f, 4421f, hs = 20f)
            val glow = LayoutData(41f, 8f, 216f, 618f, hs = 4f)
        }
    }

    object SweetBonanza {
        object Slot {
            val slot = LayoutData(0f, 0f, 160f, 160f, vs = 7f)
            val endY = -4178f
        }
        object Glow {
            val glow = LayoutData(0f, 0f, 160f, 160f, vs = 7f)
        }
        object SlotGroup {
            val slot = LayoutData(20f, 3f, 160f, 5003f, hs = 50f)
            val glow = LayoutData(20f, 3f, 160f, 828f, hs = 50f)
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
        fun size() = Vector2(w, h)

    }

}












