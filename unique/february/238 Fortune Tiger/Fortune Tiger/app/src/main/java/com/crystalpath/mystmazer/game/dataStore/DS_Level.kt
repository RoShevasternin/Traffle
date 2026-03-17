package com.crystalpath.mystmazer.game.dataStore

import com.crystalpath.mystmazer.game.manager.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

class DS_Level(override val coroutine: CoroutineScope): DataStoreUtil<Int>() {

    override val dataStore = DataStoreManager.Level

    override val flow = MutableStateFlow(1)

    init { initialize() }

}