package com.novaburst.pixelrally.game.dataStore

import com.novaburst.pixelrally.game.manager.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

class DS_Gems(override val coroutine: CoroutineScope): DataStoreUtil<Int>() {

    override val dataStore = DataStoreManager.Gems

    override val flow = MutableStateFlow(100)

    init { initialize() }

}