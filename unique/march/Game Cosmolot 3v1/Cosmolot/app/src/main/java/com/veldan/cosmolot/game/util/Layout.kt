package com.veldan.cosmolot.game.util

import com.badlogic.gdx.math.Vector2

object Layout {

    object Menu {
        val photo     = LayoutData(525f, 468f, 230f, 230f)
        val nick      = LayoutData(100f, 391f, 1080f, 49f)
        val coinImage = LayoutData(100f, 543f, 120f, 81f)
        val coinLabel = LayoutData(232f, 543f, 281f, 49f)
        val icon1     = LayoutData(100f, 66f, 298f, 298f)
        val icon2     = LayoutData(491f, 66f, 298f, 298f)
        val icon3     = LayoutData(882f, 66f, 298f, 298f)
    }

    object Game1 {
        val balance   = LayoutData(411f, 12f, 160f, 24f)
        val menu      = LayoutData(335f, 18f, 33f, 22f)
        val betText   = LayoutData(844f, 12f, 160f, 24f)
        val minus     = LayoutData(801f, 10f, 39f, 39f)
        val plus      = LayoutData(1008f, 10f, 39f, 39f)
        val betMax    = LayoutData(1179f, 196f, 64f, 64f)
        val spin      = LayoutData(1157f, 310f, 108f, 108f)
        val slotGroup = LayoutData(266f, 66f, 845f, 566f)
        val lastWin   = LayoutData(598f, 12f, 160f, 24f)
        val music     = LayoutData(1179f, 468f, 64f, 64f)

        object Slot {
            val slot = LayoutData(0f, 0f, 231f, 169f, vs = 15f)
            val startY = 15f
            val endY   = -6424f
        }

        object Glow {
            val glow = LayoutData(0f, 0f, 186f, 186f, vs = -1f)
        }

        object SlotGroup {
            val slot = LayoutData(26f, 15f, 231f, 6977f, hs = 51f)
            val glow = LayoutData(49f, 5f, 186f, 556f, hs = 95f)
        }
    }

    object Game2 {
        val balance   = LayoutData(5f, 764f, 138f, 24f)
        val menu      = LayoutData(12f, 37f, 57f, 57f)
        val box       = LayoutData(77f, 37f, 130f, 38f, hs = 10f)
        val boxText   = LayoutData(31f, 7f, 96f, 24f)
        val music     = LayoutData(784f, 37f, 129f, 38f)
        val spin      = LayoutData(1060f, 37f, 197f, 56f)
        val auto      = LayoutData(922f, 37f, 129f, 38f)
        val slotGroup = LayoutData(192f, 136f, 883f, 566f)


        object Slot {
            val slot = LayoutData(0f, 0f, 115f, 115f, vs = 25f)
            val startY = 15f
            val endY   = -10_626f
        }

        object Glow {
            val glow = LayoutData(0f, 0f, 118f, 109f, vs = 31f)
        }

        object SlotGroup {
            val slot = LayoutData(10f, 15f, 115f, 11175f, hs = 33f)
            val glow = LayoutData(9f, 18f, 118f, 529f, hs = 30f)
        }
    }

    object Game3 {
        val balance   = LayoutData(98f, 12f, 1083f, 48f)
        val menu      = LayoutData(507f, 79f, 70f, 70f)
        val music     = LayoutData(115f, 90f, 19f, 19f)
        val musicArea = LayoutData(103f, 79f, 43f, 43f)
        val spin      = LayoutData(577f, 75f, 130f, 130f)
        val auto      = LayoutData(700f, 79f, 70f, 70f)
        val betText   = LayoutData(213f, 82f, 181f, 36f)
        val plus      = LayoutData(400f, 82f, 36f, 36f)
        val minus     = LayoutData(170f, 82f, 36f, 36f)
        val lastWin   = LayoutData(835f, 82f, 285f, 43f)
        val slotGroup = LayoutData(91f, 136f, 1097f, 658f)


        object Slot {
            val slot = LayoutData(0f, 0f, 199f, 199f, vs = 17f)
            val startY = 22f
            val endY   = -27_416f
        }

        object Glow {
            val glow = LayoutData(0f, 0f, 200f, 223f, vs = -5f)
        }

        object SlotGroup {
            val slot = LayoutData(7f, 22f, 199f, 28_063f, hs = 22f)
            val glow = LayoutData(7f, 0f, 200f, 659f, hs = 21f)
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
        fun size() = Size(w, h)

    }

}












