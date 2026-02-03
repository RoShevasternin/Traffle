package com.bigfish.pairtoper.game.dataStore//package com.bigfish.pairtoper.game.dataStore
//
//import com.bigfish.pairtoper.game.manager.DataStoreManager
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.serialization.builtins.ListSerializer
//
//class DS_LevelData(override val coroutine: CoroutineScope): DataStoreJsonUtil<List<DataLevel>>(
//    serializer   = ListSerializer(DataLevel.serializer()),
//    deserializer = ListSerializer(DataLevel.serializer()),
//) {
//
//    override val dataStore = DataStoreManager.DataLevel
//
//    override val flow = MutableStateFlow(List(12) { DataLevel(it.inc(), isLock = it != 0) })
//
//    init { initialize() }
//
//}