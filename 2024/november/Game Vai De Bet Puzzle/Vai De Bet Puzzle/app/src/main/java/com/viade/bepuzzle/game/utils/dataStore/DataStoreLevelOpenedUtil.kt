package com.viade.bepuzzle.game.utils.dataStore

import com.viade.bepuzzle.game.manager.GameDataStoreManager
import com.viade.bepuzzle.game.utils.dataStore.DataStoreGalleryStarsUtil.Stars
import com.viade.bepuzzle.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DataStoreLevelOpenedUtil(val coroutine: CoroutineScope) {

    var levelOpenedFlow = MutableStateFlow<List<Int>>(listOf(1, 1, 1, 1))
        private set

    init {
        coroutine.launch {
            val levelOpenedStr = GameDataStoreManager.LevelOpened.get()
            if (levelOpenedStr != null) levelOpenedFlow.value = Json.decodeFromString<List<Int>>(levelOpenedStr)
            log("Store ${this@DataStoreLevelOpenedUtil::class.simpleName} = ${levelOpenedFlow.value}")
        }
    }

    fun update(block: (List<Int>) -> List<Int>) {
        coroutine.launch {
            levelOpenedFlow.value = block(levelOpenedFlow.value)

            log("Store ${this@DataStoreLevelOpenedUtil::class.simpleName} update = ${levelOpenedFlow.value}")
            GameDataStoreManager.LevelOpened.update { Json.encodeToString(levelOpenedFlow.value) }
        }
    }

}