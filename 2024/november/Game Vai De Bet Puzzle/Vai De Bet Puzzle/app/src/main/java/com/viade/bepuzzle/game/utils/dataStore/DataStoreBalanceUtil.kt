package com.viade.bepuzzle.game.utils.dataStore

import com.viade.bepuzzle.game.manager.GameDataStoreManager
import com.viade.bepuzzle.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DataStoreBalanceUtil(val coroutine: CoroutineScope) {

    var balanceFlow = MutableStateFlow(100)
        private set

    init {
        coroutine.launch {
            balanceFlow.value = GameDataStoreManager.Balance.get() ?: 100
            log("Store ${this@DataStoreBalanceUtil::class.simpleName} = ${balanceFlow.value}")
        }
    }

    fun update(block: (Int) -> Int) {
        coroutine.launch {
            balanceFlow.value = block(balanceFlow.value)

            log("Store ${this@DataStoreBalanceUtil::class.simpleName} update = ${balanceFlow.value}")
            GameDataStoreManager.Balance.update { balanceFlow.value }
        }
    }

}