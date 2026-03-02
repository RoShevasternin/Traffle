package com.monkeystreet.roadracejungle.game.dataStore//package com.monkeystreet.roadracejungle.game.dataStore
//
//import com.monkeystreet.roadracejungle.game.manager.DataStoreManager
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