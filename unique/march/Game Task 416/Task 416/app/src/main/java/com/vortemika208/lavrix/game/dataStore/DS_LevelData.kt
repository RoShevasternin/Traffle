package com.vortemika208.lavrix.game.dataStore

import com.vortemika208.lavrix.game.data.PlayerData
import com.vortemika208.lavrix.game.manager.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

class DS_PlayerData(override val coroutine: CoroutineScope): DataStoreJsonUtil<PlayerData>(
    serializer   = PlayerData.serializer(),
    deserializer = PlayerData.serializer(),
) {

    override val dataStore = DataStoreManager.Player

    override val flow = MutableStateFlow(PlayerData())

    init { initialize() }

}