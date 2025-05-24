package com.viade.bepuzzle.game.utils.dataStore

import com.viade.bepuzzle.game.manager.GameDataStoreManager
import com.viade.bepuzzle.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DataStoreK5Util(val coroutine: CoroutineScope) {

    var k5Flow = MutableStateFlow(1)
        private set

    init {
        coroutine.launch {
            k5Flow.value = GameDataStoreManager.K5.get() ?: 1
            log("Store ${this@DataStoreK5Util::class.simpleName} = ${k5Flow.value}")
        }
    }

    fun update(block: (Int) -> Int) {
        coroutine.launch {
            k5Flow.value = block(k5Flow.value)

            log("Store ${this@DataStoreK5Util::class.simpleName} update = ${k5Flow.value}")
            GameDataStoreManager.K5.update { k5Flow.value }
        }
    }

}