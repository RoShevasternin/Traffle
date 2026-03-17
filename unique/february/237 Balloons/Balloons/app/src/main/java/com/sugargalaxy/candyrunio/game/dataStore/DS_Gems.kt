package com.sugargalaxy.candyrunio.game.dataStore

import com.sugargalaxy.candyrunio.game.manager.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

class DS_Gems(override val coroutine: CoroutineScope): DataStoreUtil<Int>() {

    override val dataStore = DataStoreManager.Gems

    override val flow = MutableStateFlow(100)

    init { initialize() }

}