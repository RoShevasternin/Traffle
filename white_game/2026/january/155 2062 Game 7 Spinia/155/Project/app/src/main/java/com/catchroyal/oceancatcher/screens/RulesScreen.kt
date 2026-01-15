package com.catchroyal.oceancatcher.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.viewport.FitViewport
import com.catchroyal.oceancatcher.HEIGHT
import com.catchroyal.oceancatcher.WIDTH
import com.catchroyal.oceancatcher.actors.ButtonClickable
import com.catchroyal.oceancatcher.advanced.AdvancedScreen
import com.catchroyal.oceancatcher.assets.SpriteManager
import com.catchroyal.oceancatcher.utils.EXIT_H
import com.catchroyal.oceancatcher.utils.EXIT_W
import com.catchroyal.oceancatcher.utils.EXIT_X
import com.catchroyal.oceancatcher.utils.EXIT_Y
import com.catchroyal.oceancatcher.utils.NavigationUtil
import com.catchroyal.oceancatcher.utils.PLAY_H
import com.catchroyal.oceancatcher.utils.PLAY_W
import com.catchroyal.oceancatcher.utils.PLAY_X
import com.catchroyal.oceancatcher.utils.PLAY_Y
import com.catchroyal.oceancatcher.utils.setBoundsFigmaY

class RulesScreen : AdvancedScreen() {
    override val viewport = FitViewport(WIDTH, HEIGHT)



    override fun show() {
        super.show()
        background = SpriteManager.backgroundList[0]
        stage.addActors(getActors())
        Gdx.input.setCatchKey(Input.Keys.BACK, true)
    }



    private fun getActors() = listOf<Actor>(
        setUpR()
    )

    private fun setUpR() = Image(SpriteManager.rules).apply {
        setBounds(211f, 91f, 978f, 518f)
    }

    override fun keyDown(keycode: Int): Boolean {
        when (keycode) {
            Input.Keys.BACK -> NavigationUtil.back()
        }
        return super.keyDown(keycode)
    }
}