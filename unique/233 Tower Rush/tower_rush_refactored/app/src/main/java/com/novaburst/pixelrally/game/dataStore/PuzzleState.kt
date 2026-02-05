/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.dataStore

import com.novaburst.pixelrally.game.manager.StorageManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

class PuzzleState(override val coroutine: CoroutineScope): JsonSerializer<List<DataPuzzle>>(
    serializer   = ListSerializer(DataPuzzle.serializer()),
    deserializer = ListSerializer(DataPuzzle.serializer()),
) {

    override val dataStore = StorageManager.Puzzle

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