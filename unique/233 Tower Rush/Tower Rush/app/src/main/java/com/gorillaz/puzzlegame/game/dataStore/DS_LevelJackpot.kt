package com.gorillaz.puzzlegame.game.dataStore

import com.gorillaz.puzzlegame.game.manager.DataStoreManager
import com.gorillaz.puzzlegame.game.utils.GLOBAL_listDataLocation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.Serializable
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