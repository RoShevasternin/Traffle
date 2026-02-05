/*
 * Refactored Application Module
 * Build: 8BEC7C1C
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

abstract class StorageUtility<T> {
    val simpleName: String get() = this::class.java.simpleName

    abstract val coroutine: CoroutineScope
    abstract val flow     : MutableStateFlow<T>
    abstract val informationStore: AbstractDataStore.DataStoreElement<T>

    open fun initialize() {
        coroutine.launch(Dispatchers.IO) {
            informationStore.get()?.let { value -> flow.value = value }
            log("Store $simpleName = ${flow.value}")
        }
    }

    open fun update(block: (T) -> T) {
        coroutine.launch(Dispatchers.IO) {
            flow.value = block(flow.value)

            log("Store $simpleName update = ${flow.value}")
            informationStore.update { flow.value }
        }
    }
}
