/*
 * Refactored Application Module
 * Build: 97B6DFEF
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.dataStore

import com.moonarcade.starlabyrinth.game.manager.PersistenceManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

class PuzzleDataStore(override val coroutine: CoroutineScope): JsonStorageUtil<List<DataPuzzle>>(
    serializer = ListSerializer(DataPuzzle.serializer()),
    deserializer = ListSerializer(DataPuzzle.serializer()),
) {

    override val informationStore = PersistenceManager.Puzzle

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
    var collectionGetedPuzzleIndex: MutableList<Int>
)