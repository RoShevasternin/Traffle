package com.crystalpath.mystmazer.game.dataStore

import com.crystalpath.mystmazer.game.manager.DataStoreManager
import com.crystalpath.mystmazer.game.utils.GLOBAL_listDataLocation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer

class DS_LevelJackpot(override val coroutine: CoroutineScope): DataStoreJsonUtil<List<Int>>(
    serializer   = ListSerializer(Int.serializer()),
    deserializer = ListSerializer(Int.serializer()),
) {

    override val dataStore = DataStoreManager.LevelJackpot

    override val flow = MutableStateFlow(List(GLOBAL_listDataLocation.size) { 0 }) // 0..27

    init { initialize() }

}