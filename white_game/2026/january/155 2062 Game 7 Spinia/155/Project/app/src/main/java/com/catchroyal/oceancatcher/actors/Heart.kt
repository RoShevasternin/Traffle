package com.catchroyal.oceancatcher.actors

import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Disposable
import com.catchroyal.oceancatcher.assets.SpriteManager
import com.catchroyal.oceancatcher.utils.cancelCoroutinesAll
import kotlinx.coroutines.*
import kotlin.random.Random

class Heart(val bird: Bird) : Image(SpriteManager.heart), Disposable {

    companion object {
        private val MIN_X = 2
        private val MAX_X = 1341
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



    private fun setUp() {
        rotation = 0f
        setPosition(bird.x, bird.y)
    }



    fun fly() {
        jobFly = coroutineFly.launch {
            isVisible = false
            delay(10_000)
            isVisible = true
            while (true) {
                val randomX = Random.nextInt(MIN_X, MAX_X).toFloat()
                val direction = if (Random.nextBoolean()) 1 else -1

                actionFly = Actions.parallel(
                    Actions.moveTo(randomX, -height, 5f),
                    Actions.rotateTo(1000f * direction, 5f),
                )
                addAction(actionFly)
                delay(10_000)
                setUp()
            }
        }
    }

    fun reset() {
        removeAction(actionFly)
        jobFly?.cancel()
        setUp()
    }

    fun finishFly() {
        isVisible = false
        reset()
        coroutineFly.cancel()
    }

}