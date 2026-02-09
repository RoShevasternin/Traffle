package com.sugargalaxy.candyrunio.game.dataStore

import com.sugargalaxy.candyrunio.game.manager.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

class DS_Gold(override val coroutine: CoroutineScope): DataStoreUtil<Int>() {

    override val dataStore = DataStoreManager.Gold

    override val flow = MutableStateFlow(10_000)

    init { initialize() }

}