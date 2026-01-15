package com.toyscatcher.factoryatoy.utils

import com.badlogic.gdx.utils.Disposable
import kotlinx.coroutines.*

var gameTime = 0

class Timer(
    var time: Int = 60
): Disposable {

    companion object {
        private const val SECOND_DELAY = 1000L
        private const val SECOND_VALUE = 1
    }

    private val coroutineTimer = CoroutineScope(Dispatchers.Default)

    var doAfterZero: () -> Unit = { }
    var listener: (Int) -> Unit = { }


    override fun dispose() {
        cancelCoroutinesAll(coroutineTimer)
    }



    fun go() {
        coroutineTimer.launch {
            while (true) {
                delay(SECOND_DELAY)
                gameTime++
                if (time > 60) time = 60
                time -= SECOND_VALUE
                if (time <= 0) {
                    listener(0)
                    doAfterZero()
                    cancel()
                } else listener(time)
            }
        }
    }

}