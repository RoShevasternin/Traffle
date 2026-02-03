package com.gorillaz.puzzlegame.game.dataStore

import com.gorillaz.puzzlegame.game.manager.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

class DS_Gems(override val coroutine: CoroutineScope): DataStoreUtil<Int>() {

    override val dataStore = DataStoreManager.Gems

    override val flow = MutableStateFlow(100)

    init { initialize() }

}