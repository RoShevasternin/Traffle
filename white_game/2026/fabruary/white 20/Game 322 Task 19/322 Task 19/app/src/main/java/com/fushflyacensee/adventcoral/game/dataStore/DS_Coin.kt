package com.fushflyacensee.adventcoral.game.dataStore//package com.fushflyacensee.adventcoral.game.dataStore
//
//import com.fushflyacensee.adventcoral.game.manager.DataStoreManager
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