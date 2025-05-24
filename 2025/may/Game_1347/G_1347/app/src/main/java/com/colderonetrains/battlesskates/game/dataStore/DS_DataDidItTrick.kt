package com.colderonetrains.battlesskates.game.dataStore

import com.colderonetrains.battlesskates.game.manager.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

class DS_DataDidItTrick(override val coroutine: CoroutineScope): DataStoreJsonUtil<List<DataDidItTrick>>(
    serializer   = ListSerializer(DataDidItTrick.serializer()),
    deserializer = ListSerializer(DataDidItTrick.serializer()),
) {

    override val dataStore = DataStoreManager.DataDidItTrick

    override val flow = MutableStateFlow(listOf<DataDidItTrick>())

    init { initialize() }

}

@Serializable
data class DataDidItTrick(
    val type     : LevelType,
    val nameTrick: String,
)

@Serializable
enum class LevelType { Beginner, Intermediate, Pro }