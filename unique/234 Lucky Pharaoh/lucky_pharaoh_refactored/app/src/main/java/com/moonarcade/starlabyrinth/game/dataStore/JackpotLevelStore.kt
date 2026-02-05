/*
 * Refactored Application Module
 * Build: 640429DB
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.dataStore

import com.moonarcade.starlabyrinth.game.manager.PersistenceManager
import com.moonarcade.starlabyrinth.game.utils.GLOBAL_listDataLocation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer

class JackpotLevelStore(override val coroutine: CoroutineScope): JsonStorageUtil<List<Int>>(
    serializer = ListSerializer(Int.serializer()),
    deserializer = ListSerializer(Int.serializer()),
) {

    override val informationStore = PersistenceManager.LevelJackpot

    override val flow = MutableStateFlow(List(GLOBAL_listDataLocation.size) { 0 }) // 0..27

    init { initialize() }

}