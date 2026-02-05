/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.dataStore.test//package com.novaburst.pixelrally.game.dataStore
//
//import com.liberator.wisoliter.game.manager.StorageManager
//import com.novaburst.pixelrally.game.manager.StorageManager
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.serialization.Serializable
//import kotlinx.serialization.builtins.ListSerializer
//
//class DS_DataItem(override val coroutine: CoroutineScope): JsonSerializer<List<List<DataItem>>>(
//    serializer   = ListSerializer(ListSerializer(DataItem.serializer())),
//    deserializer = ListSerializer(ListSerializer(DataItem.serializer())),
//) {
//
//    override val dataStore = StorageManager.ItemCount
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