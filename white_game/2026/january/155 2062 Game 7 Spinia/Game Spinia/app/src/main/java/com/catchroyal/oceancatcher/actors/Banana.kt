package com.catchroyal.oceancatcher.actors

import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Disposable
import com.catchroyal.oceancatcher.assets.SpriteManager
import com.catchroyal.oceancatcher.utils.BANANA_H
import com.catchroyal.oceancatcher.utils.BANANA_X
import com.catchroyal.oceancatcher.utils.BANANA_Y
import com.catchroyal.oceancatcher.utils.cancelCoroutinesAll
import com.catchroyal.oceancatcher.utils.getFigmaY
import kotlinx.coroutines.*
import kotlin.random.Random

class Banana: Image(SpriteManager.banana), Disposable {

    companion object{
        private const val MIN_X = 0
        private const val MAX_X = 1362
    }

    private val coroutineFly = CoroutineScope(Dispatchers.Main)

    private var jobFly: Job? = null
    private var actionFly: Action? = null


    init {
        setOrigin(Align.center)
    }



    override fun dispose() {
        cancelCoroutinesAll(coroutineFly)
    }



    private fun setUp(){
        rotation = 0f
        setPosition(BANANA_X, getFigmaY(BANANA_Y, BANANA_H))
    }



    fun fly() {
        jobFly = coroutineFly.launch {
            while (true) {
                val randomX = Random.nextInt(MIN_X, MAX_X).toFloat()
                val direction = if (Random.nextBoolean()) 1 else -1

                actionFly = Actions.parallel(
                    Actions.moveTo(randomX, -height, 3f),
                    Actions.rotateTo(1000f * direction, 3f),
                )
                addAction(actionFly)
                delay(3100)
                setUp()
            }
        }
    }

    fun reset(){
        removeAction(actionFly)
        jobFly?.cancel()
        setUp()
    }

    fun finishFly(){
        isVisible = false
        reset()
        coroutineFly.cancel()
    }

}