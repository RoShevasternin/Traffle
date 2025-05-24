package com.viade.bepuzzle.game.utils.dataStore

import com.viade.bepuzzle.game.manager.GameDataStoreManager
import com.viade.bepuzzle.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DataStoreK1Util(val coroutine: CoroutineScope) {

    var k1Flow = MutableStateFlow(1)
        private set

    init {
        coroutine.launch {
            k1Flow.value = GameDataStoreManager.K1.get() ?: 1
            log("Store ${this@DataStoreK1Util::class.simpleName} = ${k1Flow.value}")
        }
    }

    fun update(block: (Int) -> Int) {
        coroutine.launch {
            k1Flow.value = block(k1Flow.value)

            log("Store ${this@DataStoreK1Util::class.simpleName} update = ${k1Flow.value}")
            GameDataStoreManager.K1.update { k1Flow.value }
        }
    }

}