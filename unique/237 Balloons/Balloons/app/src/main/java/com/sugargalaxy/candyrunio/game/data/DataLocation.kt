package com.sugargalaxy.candyrunio.game.data

import com.sugargalaxy.candyrunio.game.utils.actor.PosSize

data class DataLocation(
    val index         : Int,
    val maxBet        : Int,
    val levelAvailable: Int,
    val nName         : String,
    val listPriceUp   : List<Int>,
    val persPosSize   : PosSize
)
