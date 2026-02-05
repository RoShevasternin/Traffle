/*
 * Refactored Application Module
 * Build: 48CE2A69
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.dataStore

import com.moonarcade.starlabyrinth.game.manager.PersistenceManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.Serializable

class AchievementStore(override val coroutine: CoroutineScope): JsonStorageUtil<DataAchievement>(
    serializer = DataAchievement.serializer(),
    deserializer = DataAchievement.serializer(),
) {

    override val informationStore = PersistenceManager.Achievement

    override val flow = MutableStateFlow(
        DataAchievement(
            number_of_spins = 0,
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