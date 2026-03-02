package com.jellymp.jumpem.game.dataStore//package com.jellymp.jumpem.game.dataStore
//
//import com.jellymp.jumpem.game.manager.DataStoreManager
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.flow.MutableStateFlow
//
//class DS_Coin(override val coroutine: CoroutineScope): DataStoreUtil<Long>() {
//
//    override val dataStore = DataStoreManager.Coin
//
//    override val flow = MutableStateFlow(100L)
//
//    init {
//        initialize()
//    }
//}