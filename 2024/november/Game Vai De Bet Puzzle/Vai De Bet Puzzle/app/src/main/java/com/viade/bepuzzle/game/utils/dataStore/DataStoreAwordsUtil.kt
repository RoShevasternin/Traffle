package com.viade.bepuzzle.game.utils.dataStore

import com.viade.bepuzzle.game.manager.GameDataStoreManager
import com.viade.bepuzzle.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DataStoreAwordsUtil(val coroutine: CoroutineScope) {

    var awordsFlow = MutableStateFlow<List<Boolean>>(List(10) { true })
        private set

    init {
        coroutine.launch {
            val awordsStr = GameDataStoreManager.Awords.get()
            if (awordsStr != null) awordsFlow.value = Json.decodeFromString<List<Boolean>>(awordsStr)
            log("Store ${this@DataStoreAwordsUtil::class.simpleName} = ${awordsFlow.value}")
        }
    }

    fun update(block: (List<Boolean>) -> List<Boolean>) {
        coroutine.launch {
            awordsFlow.value = block(awordsFlow.value)

            log("Store ${this@DataStoreAwordsUtil::class.simpleName} update = ${awordsFlow.value}")
            GameDataStoreManager.Awords.update { Json.encodeToString(awordsFlow.value) }
        }
    }

}