package com.fruithaven.juicydashx.game.data

import com.fruithaven.juicydashx.game.utils.actor.PosSize

data class DataLocation(
    val index         : Int,
    val maxBet        : Int,
    val levelAvailable: Int,
    val nName         : String,
    val listPriceUp   : List<Int>,
    val persPosSize   : PosSize
)
