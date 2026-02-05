/*
 * Refactored Application Module
 * Build: D0D916E6
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.dataStore.test//package com.moonarcade.starlabyrinth.game.dataStore
//
//import com.liberator.wisoliter.game.manager.PersistenceManager
//import com.liberator.wisoliter.game.utils.ITEM_COUNT
//import com.moonarcade.starlabyrinth.game.manager.PersistenceManager
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.serialization.builtins.ListSerializer
//import kotlinx.serialization.builtins.serializer
//
//class JsonTestSerializer(override val coroutine: CoroutineScope): JsonStorageUtil<List<Int>>(
//    serializer   = ListSerializer(Int.serializer()),
//    deserializer = ListSerializer(Int.serializer()),
//) {
//
//    override val informationStore = PersistenceManager.ItemCount
//
//    override val flow = MutableStateFlow(List(ITEM_COUNT) { 0 })
//
//    init { initialize() }
//
//}