package com.veldan.cosmolot.game.util

import com.veldan.cosmolot.game.manager.GameDataStoreManager
import com.veldan.cosmolot.util.Once
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

object Balance {

    private val onceInitBalance = Once()

    val balanceFlow = MutableStateFlow(0L)


    fun init(value: Long) = onceInitBalance.once {
        CoroutineScope(Dispatchers.IO).launch {
            balanceFlow.value = value
            balanceFlow.collect { balance -> GameDataStoreManager.Balance.update { balance } }
        }
    }

}