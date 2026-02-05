/*
 * Refactored Application Module
 * Build: CD9AAB8C
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.dataStore.test//package com.moonarcade.starlabyrinth.game.dataStore
//
//import com.liberator.wisoliter.game.manager.PersistenceManager
//import com.moonarcade.starlabyrinth.game.manager.PersistenceManager
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.serialization.Serializable
//import kotlinx.serialization.builtins.ListSerializer
//
//class DS_DataItem(override val coroutine: CoroutineScope): JsonStorageUtil<List<List<DataItem>>>(
//    serializer   = ListSerializer(ListSerializer(DataItem.serializer())),
//    deserializer = ListSerializer(ListSerializer(DataItem.serializer())),
//) {
//
//    override val informationStore = PersistenceManager.ItemCount
//
//    override val flow = MutableStateFlow(
//        listOf(
//            listOf(DataItem(DataItemType._1)),
//            listOf(),
//            listOf(),
//            listOf(),
//            listOf(),
//            listOf(),
//            listOf(),
//        )
//    )
//
//    init { initialize() }
//
//}
//
//@Serializable
//data class DataItem(
//    val type: DataItemType,
//    var xp  : Int = type.xp
//)
//
//@Serializable
//enum class DataItemType(val xp: Int) {
//    _1(50),  _2(60),  _3(80),  _4(100),  _5(120),  _6(140),  _7(150),
//}