package com.viade.bepuzzle.game.utils.dataStore

import com.viade.bepuzzle.game.manager.GameDataStoreManager
import com.viade.bepuzzle.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DataStoreK2Util(val coroutine: CoroutineScope) {

    var k2Flow = MutableStateFlow(1)
        private set

    init {
        coroutine.launch {
            k2Flow.value = GameDataStoreManager.K2.get() ?: 1
            log("Store ${this@DataStoreK2Util::class.simpleName} = ${k2Flow.value}")
        }
    }

    fun update(block: (Int) -> Int) {
        coroutine.launch {
            k2Flow.value = block(k2Flow.value)

            log("Store ${this@DataStoreK2Util::class.simpleName} update = ${k2Flow.value}")
            GameDataStoreManager.K2.update { k2Flow.value }
        }
    }

}