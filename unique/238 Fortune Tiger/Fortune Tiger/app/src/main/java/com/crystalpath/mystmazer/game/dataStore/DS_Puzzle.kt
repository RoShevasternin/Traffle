package com.crystalpath.mystmazer.game.dataStore

import com.crystalpath.mystmazer.game.manager.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

class DS_Puzzle(override val coroutine: CoroutineScope): DataStoreJsonUtil<List<DataPuzzle>>(
    serializer   = ListSerializer(DataPuzzle.serializer()),
    deserializer = ListSerializer(DataPuzzle.serializer()),
) {

    override val dataStore = DataStoreManager.Puzzle

    override val flow = MutableStateFlow(
        listOf(
            DataPuzzle(false, mutableListOf<Int>()),
            DataPuzzle(false, mutableListOf<Int>()),
            DataPuzzle(false, mutableListOf<Int>()),
            DataPuzzle(false, mutableListOf<Int>()),
            DataPuzzle(false, mutableListOf<Int>()),
            DataPuzzle(false, mutableListOf<Int>()),
            DataPuzzle(false, mutableListOf<Int>()),
            DataPuzzle(false, mutableListOf<Int>()),
            DataPuzzle(false, mutableListOf<Int>()),
            DataPuzzle(false, mutableListOf<Int>()),
        )
    )

    init { initialize() }

}

@Serializable
data class DataPuzzle(
    var isGetedAward        : Boolean,
    var listGetedPuzzleIndex: MutableList<Int>
)