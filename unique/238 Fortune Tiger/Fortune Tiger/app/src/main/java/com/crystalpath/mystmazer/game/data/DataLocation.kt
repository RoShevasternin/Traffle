package com.crystalpath.mystmazer.game.data

import com.crystalpath.mystmazer.game.utils.actor.PosSize

data class DataLocation(
    val index         : Int,
    val maxBet        : Int,
    val levelAvailable: Int,
    val nName         : String,
    val listPriceUp   : List<Int>,
    val persPosSize   : PosSize
)
