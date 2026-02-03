package com.gorillaz.puzzlegame.game.actors.slots

import com.gorillaz.puzzlegame.game.utils.ITEM_JACKPOT_INDEX as W

interface ICombinationMatrix5x3 {
    val matrixList: List<Matrix5x3>
}

/**
 *
 * ITEM index = 0..9
 * WILD index = 100 { ITEM_JACKPOT_INDEX }
 *
 * */
object Combination5x3 {

    object Mix : ICombinationMatrix5x3 {
        val _1 = Matrix5x3(
            a1 = 1, b1 = 4, c1 = 7, d1 = 0, e1 = 3,
            a2 = 2, b2 = 5, c2 = 8, d2 = 1, e2 = 4,
            a3 = 3, b3 = 6, c3 = 9, d3 = 2, e3 = 5,
        )
        val _2 = Matrix5x3(
            a1 = 1, b1 = 2, c1 = 3, d1 = 4, e1 = 5,
            a2 = 6, b2 = 7, c2 = 8, d2 = 9, e2 = 0,
            a3 = 1, b3 = 2, c3 = 3, d3 = 4, e3 = 5,
        )
        val _3 = Matrix5x3(
            a1 = 0, b1 = 5, c1 = 6, d1 = 1, e1 = 2,
            a2 = 1, b2 = 4, c2 = 7, d2 = 0, e2 = 3,
            a3 = 2, b3 = 3, c3 = 8, d3 = 9, e3 = 4,
        )
        val _4 = Matrix5x3(
            a1 = 2, b1 = 3, c1 = 5, d1 = 9, e1 = 2,
            a2 = 4, b2 = 1, c2 = 0, d2 = 5, e2 = 1,
            a3 = 6, b3 = 9, c3 = 4, d3 = 3, e3 = 0,
        )
        val _5 = Matrix5x3(
            a1 = 1, b1 = 1, c1 = 2, d1 = 3, e1 = 0,
            a2 = 2, b2 = 9, c2 = 2, d2 = 6, e2 = 0,
            a3 = 3, b3 = 8, c3 = 7, d3 = 6, e3 = 4,
        )

        override val matrixList = listOf<Matrix5x3>(_1, _2, _3, _4, _5)
    }

    object MixWithWild : ICombinationMatrix5x3 {
        val _1 = Matrix5x3(
            a1 = W, b1 = 1, c1 = 2, d1 = 3, e1 = 4,
            a2 = 4, b2 = 3, c2 = 9, d2 = 8, e2 = 5,
            a3 = 5, b3 = 8, c3 = 9, d3 = 7, e3 = 6,
        )
        val _2 = Matrix5x3(
            a1 = 1, b1 = 4, c1 = 7, d1 = 0, e1 = 3,
            a2 = 2, b2 = 5, c2 = W, d2 = 1, e2 = 4,
            a3 = 3, b3 = 6, c3 = 9, d3 = 2, e3 = 5,
        )
        val _3 = Matrix5x3(
            a1 = 1, b1 = 4, c1 = 7, d1 = 0, e1 = 3,
            a2 = 2, b2 = 5, c2 = 8, d2 = 1, e2 = 4,
            a3 = W, b3 = 6, c3 = 9, d3 = 2, e3 = W,
        )
        val _4 = Matrix5x3(
            a1 = 1, b1 = 4, c1 = 7, d1 = 0, e1 = W,
            a2 = 2, b2 = 5, c2 = 8, d2 = 1, e2 = 4,
            a3 = 3, b3 = 6, c3 = 9, d3 = 2, e3 = 5,
        )
        val _5 = Matrix5x3(
            a1 = 1, b1 = W, c1 = 7, d1 = 0, e1 = 3,
            a2 = 2, b2 = 5, c2 = 8, d2 = 1, e2 = 4,
            a3 = 3, b3 = 6, c3 = 9, d3 = 2, e3 = 5,
        )

        override val matrixList = listOf<Matrix5x3>(_1, _2, _3, _4, _5)
    }

    object Win : ICombinationMatrix5x3 {
        val _1 = Matrix5x3(
            a1 = 0, b1 = 0, c1 = 0, d1 = 0, e1 = 0,
            a2 = 1, b2 = 2, c2 = 3, d2 = 4, e2 = 5,
            a3 = 6, b3 = 7, c3 = 8, d3 = 9, e3 = 0,

//            resultMatrix5x3 = ResultMatrix5x3(
//                a1 = true, b1 = false, c1 = false, d1 = false, e1 = false,
//                a2 = true, b2 = false, c2 = false, d2 = false, e2 = false,
//                a3 = true, b3 = false, c3 = false, d3 = false, e3 = false,
//            )

            resultShape5x3 = ResultShape5x3.Line_Top
        )
        val _2 = Matrix5x3(
            a1 = 1, b1 = 2, c1 = 3, d1 = 4, e1 = 5,
            a2 = 0, b2 = 0, c2 = 0, d2 = 0, e2 = 0,
            a3 = 6, b3 = 7, c3 = 8, d3 = 9, e3 = 1,

            resultShape5x3 = ResultShape5x3.Line_Center
        )
        val _3 = Matrix5x3(
            a1 = 1, b1 = 2, c1 = 3, d1 = 4, e1 = 5,
            a2 = 6, b2 = 7, c2 = 8, d2 = 9, e2 = 2,
            a3 = 0, b3 = 0, c3 = 0, d3 = 0, e3 = 0,

            resultShape5x3 = ResultShape5x3.Line_Bottom
        )
        val _4 = Matrix5x3(
            a1 = 0, b1 = 1, c1 = 2, d1 = 1, e1 = 0,
            a2 = 3, b2 = 0, c2 = 9, d2 = 0, e2 = 8,
            a3 = 4, b3 = 5, c3 = 0, d3 = 6, e3 = 7,

            resultShape5x3 = ResultShape5x3.V_Up
        )
        val _5 = Matrix5x3(
            a1 = 1, b1 = 7, c1 = 0, d1 = 8, e1 = 9,
            a2 = 2, b2 = 0, c2 = 6, d2 = 0, e2 = 8,
            a3 = 0, b3 = 3, c3 = 4, d3 = 5, e3 = 0,

            resultShape5x3 = ResultShape5x3.V_Down
        )
        val _6 = Matrix5x3(
            a1 = 0, b1 = 0, c1 = 1, d1 = 2, e1 = 3,
            a2 = 7, b2 = 6, c2 = 0, d2 = 5, e2 = 4,
            a3 = 8, b3 = 9, c3 = 4, d3 = 0, e3 = 0,

            resultShape5x3 = ResultShape5x3.Z_Start_End
        )
        val _7 = Matrix5x3(
            a1 = 1, b1 = 4, c1 = 5, d1 = 0, e1 = 0,
            a2 = 2, b2 = 3, c2 = 0, d2 = 8, e2 = 8,
            a3 = 0, b3 = 0, c3 = 6, d3 = 7, e3 = 9,

            resultShape5x3 = ResultShape5x3.Z_End_Start
        )

        override val matrixList = listOf<Matrix5x3>(
            _1,_2,_3,_4,_5,_6,_7,
        )
    }

    object WinWithWild : ICombinationMatrix5x3 {
        val _1 = Matrix5x3(
            a1 = W, b1 = W, c1 = W, d1 = W, e1 = W,
            a2 = 1, b2 = 2, c2 = 3, d2 = 4, e2 = 5,
            a3 = 6, b3 = 7, c3 = 8, d3 = 9, e3 = 0,

//            resultMatrix5x3 = ResultMatrix5x3(
//                a1 = true, b1 = false, c1 = false, d1 = false, e1 = false,
//                a2 = true, b2 = false, c2 = false, d2 = false, e2 = false,
//                a3 = true, b3 = false, c3 = false, d3 = false, e3 = false,
//            )

            resultShape5x3 = ResultShape5x3.Line_Top
        )
        val _2 = Matrix5x3(
            a1 = 1, b1 = 2, c1 = 3, d1 = 4, e1 = 5,
            a2 = W, b2 = W, c2 = W, d2 = W, e2 = W,
            a3 = 6, b3 = 7, c3 = 8, d3 = 9, e3 = 1,

            resultShape5x3 = ResultShape5x3.Line_Center
        )
        val _3 = Matrix5x3(
            a1 = 1, b1 = 2, c1 = 3, d1 = 4, e1 = 5,
            a2 = 6, b2 = 7, c2 = 8, d2 = 9, e2 = 2,
            a3 = W, b3 = W, c3 = W, d3 = W, e3 = W,

            resultShape5x3 = ResultShape5x3.Line_Bottom
        )
        val _4 = Matrix5x3(
            a1 = W, b1 = 1, c1 = 2, d1 = 1, e1 = W,
            a2 = 3, b2 = W, c2 = 9, d2 = W, e2 = 8,
            a3 = 4, b3 = 5, c3 = W, d3 = 6, e3 = 7,

            resultShape5x3 = ResultShape5x3.V_Up
        )
        val _5 = Matrix5x3(
            a1 = 1, b1 = 7, c1 = W, d1 = 1, e1 = 9,
            a2 = 2, b2 = W, c2 = 6, d2 = W, e2 = 8,
            a3 = W, b3 = 3, c3 = 4, d3 = 5, e3 = W,

            resultShape5x3 = ResultShape5x3.V_Down
        )
        val _6 = Matrix5x3(
            a1 = W, b1 = W, c1 = 1, d1 = 2, e1 = 3,
            a2 = 7, b2 = 6, c2 = W, d2 = 5, e2 = 4,
            a3 = 8, b3 = 9, c3 = 4, d3 = W, e3 = W,

            resultShape5x3 = ResultShape5x3.Z_Start_End
        )
        val _7 = Matrix5x3(
            a1 = 1, b1 = 4, c1 = 5, d1 = W, e1 = W,
            a2 = 2, b2 = 3, c2 = W, d2 = 8, e2 = 2,
            a3 = W, b3 = W, c3 = 6, d3 = 7, e3 = 9,

            resultShape5x3 = ResultShape5x3.Z_End_Start
        )

        override val matrixList = listOf<Matrix5x3>(
            _1,_2,_3,_4,_5,_6,_7,
        )
    }

}