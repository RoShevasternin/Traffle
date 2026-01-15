package com.catchroyal.oceancatcher.actors

import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Disposable
import com.catchroyal.oceancatcher.HEIGHT
import com.catchroyal.oceancatcher.WIDTH
import com.catchroyal.oceancatcher.assets.SpriteManager
import com.catchroyal.oceancatcher.utils.JEWEL_H
import com.catchroyal.oceancatcher.utils.JEWEL_W
import com.catchroyal.oceancatcher.utils.Jewel
import com.catchroyal.oceancatcher.utils.cancelCoroutinesAll
import kotlinx.coroutines.*
import kotlin.random.Random

class JewelActor : Image(), Disposable {

    companion object {
        private const val MIN_X = 0
        private const val MAX_X = (WIDTH - JEWEL_W).toInt()
        private const val MIN_Y = HEIGHT
        private const val MAX_Y = -JEWEL_H
    }

    private val coroutineGo = CoroutineScope(Dispatchers.Main)

    private var jobGo: Job? = null
    private var actionGo: Action? = null

    private val directionValues = listOf(1, -1)

    var jewel: Jewel? = null
        set(value) {
            field = value
            drawable = TextureRegionDrawable(value?.region ?: SpriteManager.none)
        }

    var isFree = true



    init {
        y = MIN_Y
        setSize(JEWEL_W, JEWEL_H)
        setOrigin(Align.center)
    }



    override fun dispose() {
        cancelCoroutinesAll(coroutineGo)
    }



    private fun initActionGo(x: Float = 0f) {
        actionGo = Actions.parallel(
            Actions.moveTo(x, MAX_Y, 5f),
            Actions.rotateBy(960f * directionValues.random(), 5f),
        )
    }



    fun go() {
        jobGo = coroutineGo.launch {
            isFree = false

            val randomX = Random.nextInt(MIN_X, MAX_X).toFloat()
            x = randomX

            initActionGo(randomX)
            addAction(actionGo)

            delay(5100)
            finishGo()
        }
    }

    fun finishGo(){
        removeAction(actionGo)
        jewel = null
        jobGo?.cancel()
        isFree = true
        y = MIN_Y
    }

}