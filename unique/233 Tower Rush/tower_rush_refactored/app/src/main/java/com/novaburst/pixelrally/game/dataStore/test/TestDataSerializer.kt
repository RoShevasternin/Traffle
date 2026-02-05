/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.dataStore.test//package com.novaburst.pixelrally.game.dataStore
//
//import com.liberator.wisoliter.game.manager.StorageManager
//import com.liberator.wisoliter.game.utils.ITEM_COUNT
//import com.novaburst.pixelrally.game.manager.StorageManager
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.serialization.builtins.ListSerializer
//import kotlinx.serialization.builtins.serializer
//
//class TestDataSerializer(override val coroutine: CoroutineScope): JsonSerializer<List<Int>>(
//    serializer   = ListSerializer(Int.serializer()),
//    deserializer = ListSerializer(Int.serializer()),
//) {
//
//    override val dataStore = StorageManager.ItemCount
//
//    override val flow = MutableStateFlow(List(ITEM_COUNT) { 0 })
//
//    init { initialize() }
//
//}