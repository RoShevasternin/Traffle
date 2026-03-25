package com.keltrivaapp.casibom.game.util

import com.keltrivaapp.casibom.game.manager.GameDataStoreManager
import com.keltrivaapp.casibom.util.Once
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