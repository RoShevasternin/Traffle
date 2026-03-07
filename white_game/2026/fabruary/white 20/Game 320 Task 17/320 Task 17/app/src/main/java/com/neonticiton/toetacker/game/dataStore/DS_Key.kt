package com.neonticiton.toetacker.game.dataStore

import com.neonticiton.toetacker.game.manager.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

class DS_Key(override val coroutine: CoroutineScope): DataStoreUtil<Int>() {

    override val dataStore = DataStoreManager.Key

    override val flow = MutableStateFlow(1)

    init {
        initialize()
    }

}