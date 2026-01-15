package com.princesssand.soundsprinss.game.utils.dataStoreUtil

import com.princesssand.soundsprinss.game.manager.GameDataStoreManager
import com.princesssand.soundsprinss.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ResultUtil(val coroutine: CoroutineScope) {

    var resultList = mutableListOf<Int>()
        private set

    private var resultStr = ""

    init {
        coroutine.launch {
            val result = GameDataStoreManager.Result.get() ?: "0|0|0|0|0|0|0|0|0|0"
            log("Store result = $result")

            resultList = result.split('|').map { it.toInt() }.toMutableList()
            log("List result = $resultList")
        }
    }

    fun update(lvlIndex: Int, result: Int) {
        coroutine.launch {
            resultList[lvlIndex] = result
            resultStr = ""
            resultList.onEachIndexed { index, result ->
                resultStr += if (index == 9) "$result" else "$result|"
            }
            log("Store result update = $resultStr")

            GameDataStoreManager.Result.update { resultStr }
        }
    }

}