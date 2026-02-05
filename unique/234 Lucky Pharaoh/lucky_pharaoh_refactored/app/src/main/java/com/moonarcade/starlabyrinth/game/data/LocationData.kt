/*
 * Refactored Application Module
 * Build: AE94966F
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.data

import com.moonarcade.starlabyrinth.game.utils.actor.PosSize

data class LocationData(
    val index         : Int,
    val maxBet        : Int,
    val levelAvailable: Int,
    val nName         : String,
    val collectionPriceUp   : List<Int>,
    val persPosSize   : PosSize
)
