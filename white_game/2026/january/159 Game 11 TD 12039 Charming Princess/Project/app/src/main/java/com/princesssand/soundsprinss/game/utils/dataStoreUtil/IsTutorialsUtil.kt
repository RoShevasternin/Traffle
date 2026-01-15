package com.princesssand.soundsprinss.game.utils.dataStoreUtil

import com.princesssand.soundsprinss.game.manager.GameDataStoreManager
import com.princesssand.soundsprinss.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class IsTutorialsUtil(val coroutine: CoroutineScope) {

    var isTutorials = false
        private set

    init {
        coroutine.launch {
            isTutorials = GameDataStoreManager.IsTutorials.get() ?: true
            log("Store isTutorials = $isTutorials")
        }
    }

    fun update(flag: Boolean) {
        coroutine.launch {
            isTutorials = flag
            log("Store isTutorials update = $isTutorials")
            GameDataStoreManager.IsTutorials.update { isTutorials }
        }
    }

}