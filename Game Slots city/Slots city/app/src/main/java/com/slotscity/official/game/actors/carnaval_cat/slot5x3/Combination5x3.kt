package com.slotscity.official.game.actors.carnaval_cat.slot5x3

interface ICombinationMatrix5x3 {
    val matrixList: List<Matrix5x3>
}

/**
 *
 * ITEM index = 0..13
 * WILD index = 14
 *
 * */
object Combination5x3 {

    object Mix : ICombinationMatrix5x3 {
        val _1 = Matrix5x3(
            a1 = 1, b1 = 6, c1 = 11, d1 = 0, e1 = 9,
            a2 = 2, b2 = 7, c2 = 12, d2 = 4, e2 = 10,
            a3 = 3, b3 = 8, c3 = 13, d3 = 5, e3 = 1,
        )
        val _2 = Matrix5x3(
            a1 = 1, b1 = 6, c1 = 1, d1 = 0, e1 = 9,
            a2 = 2, b2 = 7, c2 = 12, d2 = 4, e2 = 10,
            a3 = 3, b3 = 8, c3 = 3, d3 = 5, e3 = 10,
        )
        val _3 = Matrix5x3(
            a1 = 2, b1 = 6, c1 = 12, d1 = 0, e1 = 3,
            a2 = 2, b2 = 4, c2 = 10, d2 = 4, e2 = 3,
            a3 = 7, b3 = 0, c3 = 12, d3 = 5, e3 = 1,
        )
        val _4 = Matrix5x3(
            a1 = 2, b1 = 6, c1 = 12, d1 = 0, e1 = 3,
            a2 = 2, b2 = 7, c2 = 12, d2 = 4, e2 = 10,
            a3 = 3, b3 = 8, c3 = 13, d3 = 5, e3 = 1,
        )
        val _5 = Matrix5x3(
            a1 = 1, b1 = 6, c1 = 11, d1 = 0, e1 = 0,
            a2 = 2, b2 = 7, c2 = 2, d2 = 4, e2 = 1,
            a3 = 3, b3 = 3, c3 = 13, d3 = 5, e3 = 14,
        )

        override val matrixList = listOf<Matrix5x3>(_1, _2, _3, _4, _5)
    }

    object Win : ICombinationMatrix5x3 {
        val _1 = Matrix5x3(
            a1 = 1, b1 = 2, c1 = 7, d1 = 8, e1 = 13,
            a2 = 1, b2 = 3, c2 = 6, d2 = 9, e2 = 12,
            a3 = 1, b3 = 4, c3 = 5, d3 = 10, e3 = 11,

            resultMatrix5x3 = ResultMatrix5x3(
                a1 = true, b1 = false, c1 = false, d1 = false, e1 = false,
                a2 = true, b2 = false, c2 = false, d2 = false, e2 = false,
                a3 = true, b3 = false, c3 = false, d3 = false, e3 = false,
            )
        )
        val _2 = Matrix5x3(
            a1 = 2, b1 = 1, c1 = 7, d1 = 8, e1 = 13,
            a2 = 3, b2 = 1, c2 = 6, d2 = 9, e2 = 12,
            a3 = 4, b3 = 1, c3 = 5, d3 = 10, e3 = 11,

            resultMatrix5x3 = ResultMatrix5x3(
                a1 = false, b1 = true, c1 = false, d1 = false, e1 = false,
                a2 = false, b2 = true, c2 = false, d2 = false, e2 = false,
                a3 = false, b3 = true, c3 = false, d3 = false, e3 = false,
            )
        )
        val _3 = Matrix5x3(
            a1 = 2, b1 = 7, c1 = 1, d1 = 8, e1 = 13,
            a2 = 3, b2 = 6, c2 = 1, d2 = 9, e2 = 12,
            a3 = 4, b3 = 5, c3 = 1, d3 = 10, e3 = 11,

            resultMatrix5x3 = ResultMatrix5x3(
                a1 = false, b1 = false, c1 = true, d1 = false, e1 = false,
                a2 = false, b2 = false, c2 = true, d2 = false, e2 = false,
                a3 = false, b3 = false, c3 = true, d3 = false, e3 = false,
            )
        )
        val _4 = Matrix5x3(
            a1 = 2, b1 = 7, c1 = 8, d1 = 1, e1 = 13,
            a2 = 3, b2 = 6, c2 = 9, d2 = 1, e2 = 14,
            a3 = 4, b3 = 5, c3 = 10, d3 = 1, e3 = 11,

            resultMatrix5x3 = ResultMatrix5x3(
                a1 = false, b1 = false, c1 = false, d1 = true, e1 = false,
                a2 = false, b2 = false, c2 = false, d2 = true, e2 = true,
                a3 = false, b3 = false, c3 = false, d3 = true, e3 = false,
            )
        )
        val _5 = Matrix5x3(
            a1 = 2, b1 = 7, c1 = 8, d1 = 13, e1 = 1,
            a2 = 3, b2 = 6, c2 = 9, d2 = 12, e2 = 1,
            a3 = 4, b3 = 5, c3 = 10, d3 = 11, e3 = 1,

            resultMatrix5x3 = ResultMatrix5x3(
                a1 = false, b1 = false, c1 = false, d1 = false, e1 = true,
                a2 = false, b2 = false, c2 = false, d2 = false, e2 = true,
                a3 = false, b3 = false, c3 = false, d3 = false, e3 = true,
            )
        )
        val _6 = Matrix5x3(
            a1 = 1, b1 = 1, c1 = 1, d1 = 1, e1 = 1,
            a2 = 3, b2 = 6, c2 = 9, d2 = 12, e2 = 2,
            a3 = 4, b3 = 5, c3 = 10, d3 = 11, e3 = 4,

            resultMatrix5x3 = ResultMatrix5x3(
                a1 = true, b1 = true, c1 = true, d1 = true, e1 = true,
                a2 = false, b2 = false, c2 = false, d2 = false, e2 = false,
                a3 = false, b3 = false, c3 = false, d3 = false, e3 = false,
            )
        )
        val _7 = Matrix5x3(
            a1 = 3, b1 = 6, c1 = 9, d1 = 12, e1 = 2,
            a2 = 1, b2 = 1, c2 = 1, d2 = 1, e2 = 1,
            a3 = 4, b3 = 5, c3 = 10, d3 = 11, e3 = 4,

            resultMatrix5x3 = ResultMatrix5x3(
                a1 = false, b1 = false, c1 = false, d1 = false, e1 = false,
                a2 = true, b2 = true, c2 = true, d2 = true, e2 = true,
                a3 = false, b3 = false, c3 = false, d3 = false, e3 = false,
            )
        )
        val _8 = Matrix5x3(
            a1 = 3, b1 = 6, c1 = 9, d1 = 12, e1 = 2,
            a2 = 4, b2 = 5, c2 = 10, d2 = 11, e2 = 4,
            a3 = 1, b3 = 1, c3 = 1, d3 = 1, e3 = 1,

            resultMatrix5x3 = ResultMatrix5x3(
                a1 = false, b1 = false, c1 = false, d1 = false, e1 = false,
                a2 = false, b2 = false, c2 = false, d2 = false, e2 = false,
                a3 = true, b3 = true, c3 = true, d3 = true, e3 = true,
            )
        )

        override val matrixList = listOf<Matrix5x3>(
            _1,_2,_3,_4,_5,_6,_7,_8
        )
    }

}