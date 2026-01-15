package com.catchroyal.oceancatcher.actors

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Disposable
import com.catchroyal.oceancatcher.assets.SpriteManager
import com.catchroyal.oceancatcher.utils.cancelCoroutinesAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Monkey: Image(SpriteManager.monkey), Disposable {

    private val coroutineSwing = CoroutineScope(Dispatchers.Default)



    init {
        setOrigin(width / 2f, height)
    }



    override fun dispose() {
        cancelCoroutinesAll(coroutineSwing)
    }



    fun swing() {
        coroutineSwing.launch {
            addAction(
                Actions.forever(
                    Actions.sequence(
                        Actions.rotateTo(30f, 3f),
                        Actions.rotateTo(-30f, 3f),
                    )
                )
            )
        }
    }

}