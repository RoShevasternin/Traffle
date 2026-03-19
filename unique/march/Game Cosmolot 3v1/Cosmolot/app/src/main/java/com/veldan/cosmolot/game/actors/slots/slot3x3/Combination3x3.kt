package com.veldan.cosmolot.game.actors.slots.slot3x3

import com.veldan.cosmolot.game.actors.slots.slot3x3.Matrix3x3.Item.*

interface CombinationMatrix3x3 {
    val matrixList: List<Matrix3x3>
}

object Combination3x3 {

    object Mix : CombinationMatrix3x3 {
        val _1 = Matrix3x3(
            a1 = a1, b1 = a4, c1 = a7,
            a2 = a2, b2 = a5, c2 = a8,
            a3 = a3, b3 = a6, c3 = a9,
        )
        val _2 = Matrix3x3(
            a1 = a1, b1 = a4, c1 = a7,
            a2 = a2, b2 = a5, c2 = a8,
            a3 = a3, b3 = a6, c3 = a2,
        )
        val _3 = Matrix3x3(
            a1 = a1, b1 = a4, c1 = a2,
            a2 = a2, b2 = a5, c2 = a1,
            a3 = a3, b3 = a6, c3 = a2,
        )
        val _4 = Matrix3x3(
            a1 = a1, b1 = a5, c1 = a7,
            a2 = a2, b2 = a5, c2 = a1,
            a3 = a1, b3 = a6, c3 = a2,
        )
        val _5 = Matrix3x3(
            a1 = a3, b1 = a4, c1 = a3,
            a2 = a2, b2 = a5, c2 = a1,
            a3 = a3, b3 = a6, c3 = a2,
        )
        val _6 = Matrix3x3(
            a1 = a1, b1 = a4, c1 = a7,
            a2 = a2, b2 = a8, c2 = a1,
            a3 = a3, b3 = a8, c3 = a2,
        )

        override val matrixList = listOf<Matrix3x3>(_1, _2, _3, _4, _5, _6)
    }

    object WinMonochrome: CombinationMatrix3x3 {
        val _1 = Matrix3x3(
            scheme = """ 1 1 1
                         - - -
                         - - -""",
            winItemList = listOf(a1),
            a1 = a1, b1 = a1, c1 = a1,
            a2 = a2, b2 = a5, c2 = a4,
            a3 = a3, b3 = a6, c3 = a2,
        )
        val _2 = Matrix3x3(
            scheme = """ - - -
                         1 1 1
                         - - -""",
            winItemList = listOf(a1),
            a1 = a5, b1 = a3, c1 = a2,
            a2 = a1, b2 = a1, c2 = a1,
            a3 = a3, b3 = a6, c3 = a2,
        )
        val _3 = Matrix3x3(
            scheme = """ 1 1 1
                         - - -
                         1 1 1""",
            winItemList = listOf(a1),
            a1 = a1, b1 = a1, c1 = a1,
            a2 = a2, b2 = a5, c2 = a4,
            a3 = a1, b3 = a1, c3 = a1,
        )
        val _4 = Matrix3x3(
            scheme = """ 1 - -
                         - 1 1
                         1 - -""",
            winItemList = listOf(a1),
            a1 = a1, b1 = a3, c1 = a6,
            a2 = a2, b2 = a1, c2 = a1,
            a3 = a1, b3 = a6, c3 = a2,
        )
        val _5 = Matrix3x3(
            scheme = """ 1 1 1
                         1 - -
                         1 - -""",
            winItemList = listOf(a1),
            a1 = a1, b1 = a1, c1 = a1,
            a2 = a1, b2 = a5, c2 = a4,
            a3 = a1, b3 = a6, c3 = a2,
        )
        val _6 = Matrix3x3(
            scheme = """ 1 1 1
                         - 1 -
                         - - 1""",
            winItemList = listOf(a1),
            a1 = a1, b1 = a1, c1 = a1,
            a2 = a2, b2 = a1, c2 = a4,
            a3 = a3, b3 = a6, c3 = a1,
        )
        val _7 = Matrix3x3(
            scheme = """ - - -
                         1 1 -
                         1 1 -""",
            winItemList = listOf(a1),
            a1 = a3, b1 = a5, c1 = a6,
            a2 = a1, b2 = a1, c2 = a4,
            a3 = a1, b3 = a1, c3 = a2,
        )
        val _8 = Matrix3x3(
            scheme = """ - 1 1
                         - 1 1
                         - - -""",
            winItemList = listOf(a1),
            a1 = a3, b1 = a1, c1 = a1,
            a2 = a2, b2 = a1, c2 = a1,
            a3 = a3, b3 = a6, c3 = a2,
        )
        val _9 = Matrix3x3(
            scheme = """ - - -
                         - - 1
                         1 1 -""",
            winItemList = listOf(a1),
            a1 = a5, b1 = a6, c1 = a7,
            a2 = a2, b2 = a5, c2 = a1,
            a3 = a1, b3 = a1, c3 = a2,
        )
        val _10 = Matrix3x3(
            scheme = """ - 1 -
                         - 1 1
                         - 1 -""",
            winItemList = listOf(a1),
            a1 = a4, b1 = a1, c1 = a5,
            a2 = a2, b2 = a1, c2 = a1,
            a3 = a3, b3 = a1, c3 = a2,
        )
        val _11 = Matrix3x3(
            scheme = """ - 1 -
                         1 1 1
                         1 1 -""",
            winItemList = listOf(a1),
            a1 = a4, b1 = a1, c1 = a5,
            a2 = a1, b2 = a1, c2 = a1,
            a3 = a1, b3 = a1, c3 = a2,
        )
        override val matrixList = listOf<Matrix3x3>(_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11)
    }

    object WinMonochromeWithWild: CombinationMatrix3x3 {
        val _1 = Matrix3x3(
            scheme = """ W 1 W
                         - 1 -
                         - - -""",
            winItemList = listOf(W, a1),
            a1 = W, b1 = a1, c1 = W,
            a2 = a5, b2 = a1, c2 = a2,
            a3 = a7, b3 = a6, c3 = a5,
        )
        val _2 = Matrix3x3(
            scheme = """ - - -
                         1 1 1
                         W 1 1""",
            winItemList = listOf(W, a1),
            a1 = a3, b1 = a5, c1 = a4,
            a2 = a1, b2 = a1, c2 = a1,
            a3 = W, b3 = a1, c3 = a1,
        )
        val _3 = Matrix3x3(
            scheme = """ 1 1 1
                         1 W 1
                         1 1 1""",
            winItemList = listOf(W, a1),
            a1 = a1, b1 = a1, c1 = a1,
            a2 = a1, b2 = W, c2 = a1,
            a3 = a1, b3 = a1, c3 = a1,
        )
        val _4 = Matrix3x3(
            scheme = """ W 1 1
                         1 W 1
                         1 1 W""",
            winItemList = listOf(W, a1),
            a1 = W, b1 = a1, c1 = a1,
            a2 = a1, b2 = W, c2 = a1,
            a3 = a1, b3 = a1, c3 = W,
        )

        override val matrixList = listOf<Matrix3x3>(_1, _2, _3, _4)
    }

}