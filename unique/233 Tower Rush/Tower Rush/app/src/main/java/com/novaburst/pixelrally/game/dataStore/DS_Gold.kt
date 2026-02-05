package com.novaburst.pixelrally.game.dataStore

import com.novaburst.pixelrally.game.manager.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

class DS_Gold(override val coroutine: CoroutineScope): DataStoreUtil<Int>() {

    override val dataStore = DataStoreManager.Gold

    override val flow = MutableStateFlow(10_000)

    init { initialize() }

}