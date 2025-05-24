package com.gorillaz.puzzlegame.game.dataStore

import com.gorillaz.puzzlegame.game.manager.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.Serializable

class DS_Achievement(override val coroutine: CoroutineScope): DataStoreJsonUtil<DataAchievement>(
    serializer   = DataAchievement.serializer(),
    deserializer = DataAchievement.serializer(),
) {

    override val dataStore = DataStoreManager.Achievement

    override val flow = MutableStateFlow(
        DataAchievement(
            number_of_spins  = 0,
            number_of_wins   = 0,
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