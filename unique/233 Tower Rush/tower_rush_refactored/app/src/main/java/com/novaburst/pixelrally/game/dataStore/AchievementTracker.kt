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

class AchievementTracker(override val coroutine: CoroutineScope): JsonSerializer<DataAchievement>(
    serializer   = DataAchievement.serializer(),
    deserializer = DataAchievement.serializer(),
) {

    override val dataStore = StorageManager.Achievement

    override val flow = MutableStateFlow(
        DataAchievement(
            number_of_spins  = 0,
            number_of_wins = 0,
            maximum_winnings = 0,
        )
    )

    init { initialize() }

}

@Serializable
data class DataAchievement(
    var number_of_spins : Int,
    var number_of_wins  : Int,
    var maximum_winnings: Int,
)