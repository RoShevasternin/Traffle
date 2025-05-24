package com.colderonetrains.battlesskates.game.dataStore

import com.colderonetrains.battlesskates.game.manager.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

class DS_IsWelcome(override val coroutine: CoroutineScope): DataStoreUtil<Boolean>(){

    override val dataStore = DataStoreManager.IsWelcome

    override val flow = MutableStateFlow(true)

    init { initialize() }

}