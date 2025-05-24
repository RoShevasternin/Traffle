package com.slotscity.official.game.utils

import com.slotscity.official.game.manager.GameDataStoreManager
import com.slotscity.official.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class Balance(val coroutineScope: CoroutineScope) {

    val balanceFlow = MutableStateFlow(-1L)

    init {
        coroutineScope.launch(Dispatchers.IO) {
            balanceFlow.value = GameDataStoreManager.Balance.get() ?: 1000L

            balanceFlow.collect { balance ->
                if (balance != -1L) GameDataStoreManager.Balance.update { if (balance != it) balance else it }
            }
        }
    }

}