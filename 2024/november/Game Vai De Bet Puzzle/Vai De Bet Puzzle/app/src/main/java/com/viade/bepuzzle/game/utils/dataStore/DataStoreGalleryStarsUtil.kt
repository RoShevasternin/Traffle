package com.viade.bepuzzle.game.utils.dataStore

import com.viade.bepuzzle.game.manager.GameDataStoreManager
import com.viade.bepuzzle.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DataStoreGalleryStarsUtil(val coroutine: CoroutineScope) {

    var starsFlow = MutableStateFlow<Set<Stars>>(setOf())
        private set

    init {
        coroutine.launch {
            val starsStr = GameDataStoreManager.GalleryStars.get()
            if (starsStr != null) starsFlow.value = Json.decodeFromString<Set<Stars>>(starsStr)

            log("Store ${this@DataStoreGalleryStarsUtil::class.simpleName} = ${starsFlow.value}")
        }
    }

    fun update(block: (Set<Stars>) -> Set<Stars>) {
        coroutine.launch {
            starsFlow.value = block(starsFlow.value)

            log("Store ${this@DataStoreGalleryStarsUtil::class.simpleName} update = ${starsFlow.value}")
            GameDataStoreManager.GalleryStars.update { Json.encodeToString<Set<Stars>>(starsFlow.value) }
        }
    }

    @Serializable
    data class Stars(val categoryIndex: Int, val levelIndex: Int, val starIndex: Int)

}