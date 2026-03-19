package com.senqorvia774.lottomatica.game.model

import com.senqorvia774.lottomatica.game.data.PlayerData
import com.senqorvia774.lottomatica.game.dataStore.DS_PlayerData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

class PlayerModel(
    coroutine: CoroutineScope,
    private val ds: DS_PlayerData
) {

    // ----------------------------------------------------------------
    // Base Flow
    // ----------------------------------------------------------------

    val playerFlow: StateFlow<PlayerData> = ds.flow

    // ----------------------------------------------------------------
    // Coin Flow
    // ----------------------------------------------------------------

    val coinFlow: StateFlow<Int> = playerFlow
        .map { it.coin }
        .stateIn(
            scope = coroutine,
            started = SharingStarted.Eagerly,
            initialValue = playerFlow.value.coin
        )

    val coin: Int
        get() = coinFlow.value

    // ----------------------------------------------------------------
    // Coin Logic
    // ----------------------------------------------------------------

    fun addCoin(value: Int) {
        ds.update { it.copy(coin = it.coin + value) }
    }

    fun spendCoin(value: Int): Boolean {
        if (coin < value) return false
        ds.update { it.copy(coin = it.coin - value) }
        return true
    }

    fun setCoin(value: Int) {
        ds.update { it.copy(coin = value) }
    }

    // ----------------------------------------------------------------
    // Daily Bonus Flow
    // ----------------------------------------------------------------

    val dailyBonusAvailableFlow =
        playerFlow
            .map { System.currentTimeMillis() >= it.nextDailyBonusTime }
            .stateIn(
                scope = ds.coroutine,
                started = SharingStarted.Eagerly,
                initialValue = true
            )

    // ----------------------------------------------------------------
    // Daily Bonus Logic
    // ----------------------------------------------------------------
    fun claimDailyBonus(reward: Int) {
        val now = System.currentTimeMillis()
        if (now < playerFlow.value.nextDailyBonusTime) return
        val next = now + 24 * 60 * 60 * 1000L
        ds.update {
            it.copy(
                coin = it.coin + reward,
                nextDailyBonusTime = next
            )
        }
    }

    fun getRemainingDailyTime(): Long {
        val diff = playerFlow.value.nextDailyBonusTime - System.currentTimeMillis()
        return maxOf(0, diff)
    }

}