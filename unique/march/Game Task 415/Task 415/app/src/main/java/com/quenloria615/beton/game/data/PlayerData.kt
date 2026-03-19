package com.quenloria615.beton.game.data

import kotlinx.serialization.Serializable

@Serializable
data class PlayerData(
    val coin: Int = 5_000,
    val nextDailyBonusTime: Long = 0L
)
