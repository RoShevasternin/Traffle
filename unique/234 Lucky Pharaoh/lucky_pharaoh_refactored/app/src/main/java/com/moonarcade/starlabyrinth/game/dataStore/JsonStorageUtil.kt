/*
 * Refactored Application Module
 * Build: 05D41409
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.dataStore

import com.moonarcade.starlabyrinth.game.manager.AbstractDataStore
import com.moonarcade.starlabyrinth.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

abstract class JsonStorageUtil<T>(
    private val serializer  : KSerializer<T>,
    private val deserializer: DeserializationStrategy<T>
) {
    val simpleName: String get() = this::class.java.simpleName

    abstract val coroutine: CoroutineScope
    abstract val flow     : MutableStateFlow<T>
    abstract val informationStore: AbstractDataStore.DataStoreElement<String>

    open fun initialize() {
        coroutine.launch(Dispatchers.IO) {
            informationStore.get()?.let { value -> flow.value = Json.decodeFromString(deserializer, value) }
            log("Store $simpleName = ${flow.value}")
        }
    }

    open fun update(block: (T) -> T) {
        coroutine.launch(Dispatchers.IO) {
            flow.value = block(flow.value)

            log("Store $simpleName update = ${flow.value}")
            informationStore.update { Json.encodeToString(serializer, flow.value) }
        }
    }
}
