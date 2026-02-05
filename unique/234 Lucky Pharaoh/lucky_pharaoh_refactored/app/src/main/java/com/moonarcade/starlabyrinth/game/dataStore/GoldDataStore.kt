/*
 * Refactored Application Module
 * Build: 3F8AAD8B
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.dataStore

import com.moonarcade.starlabyrinth.game.manager.PersistenceManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

class GoldDataStore(override val coroutine: CoroutineScope): StorageUtility<Int>() {

    override val informationStore = PersistenceManager.Gold

    override val flow = MutableStateFlow(10_000)

    init { initialize() }

}