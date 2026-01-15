package com.toyscatcher.factoryatoy.actors

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.toyscatcher.factoryatoy.VOLUME_TAP_VERTICAL
import com.toyscatcher.factoryatoy.advanced.AdvancedGroup
import com.toyscatcher.factoryatoy.assets.SpriteManager
import com.toyscatcher.factoryatoy.utils.*
import kotlinx.coroutines.delay

class Tap : AdvancedGroup() {

    private val arm = Image(SpriteManager.tap_arm)
    private val grab = Image(SpriteManager.tap_grab)

    var isDown = false
        private set



    override fun sizeChanged() {
        if (width > 0 && height > 0) addActors(getActors())
    }



    private fun getActors() = listOf(
        setUpArm(),
        setUpTop(),
        setUpGrab(),
    )



    private fun setUpTop() = Image(SpriteManager.tap_top).apply {
        setBoundsFigmaY(TAP_TOP_X, TAP_TOP_Y, TAP_TOP_W, TAP_TOP_H, this@Tap.height)
    }

    private fun setUpArm() = arm.apply {
        setBoundsFigmaY(TAP_ARM_X, TAP_ARM_Y, TAP_ARM_W, TAP_ARM_H, this@Tap.height)
    }

    private fun setUpGrab() = grab.apply {
        setBoundsFigmaY(TAP_GRAB_X, TAP_GRAB_Y, TAP_GRAB_W, TAP_GRAB_H, this@Tap.height)
    }



    fun down() {
        val armY = getFigmaY(TAP_ARM_MAX_Y, arm.height, height)
        with(arm){ addAction(Actions.moveTo(x, armY, 5f)) }
        val grabY = getFigmaY(TAP_ARM_MAX_Y, arm.height+grab.height, height)
        with(grab){ addAction(Actions.sequence(Actions.moveTo(x, grabY, 5f), Actions.run { isDown = true })) }
    }

    fun setToyById(id: Int) {
        grab.drawable = TextureRegionDrawable(SpriteManager.catchToyList[id])
    }

    suspend fun up(doOnEnd: suspend () -> Unit) {
        isDown = false
        val armY = getFigmaY(TAP_ARM_MIN_Y, arm.height, height)
        with(arm){ addAction(Actions.moveTo(x, armY, 4f)) }
        val grabY = getFigmaY(TAP_ARM_MIN_Y, arm.height+grab.height, height)
        with(grab){ addAction(Actions.moveTo(x, grabY, 4f))}
        delay(4000)
        grab.drawable = TextureRegionDrawable(SpriteManager.tap_grab)
        doOnEnd()
    }

}

