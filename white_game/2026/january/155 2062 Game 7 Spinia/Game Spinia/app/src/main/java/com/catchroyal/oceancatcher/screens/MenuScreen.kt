package com.catchroyal.oceancatcher.screens

import com.badlogic.gdx.scenes.scene2d.Actor
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

class MenuScreen : AdvancedScreen() {
    override val viewport = FitViewport(WIDTH, HEIGHT)



    override fun show() {
        super.show()
        background = SpriteManager.backgroundList[0]
        stage.addActors(getActors())
    }



    private fun getActors() = listOf<Actor>(
        setUpPlay(),
        setUpExit(),
        setUpR()
    )



    private fun setUpPlay() = ButtonClickable(
        ButtonClickable.Style(
        default = SpriteManager.play_def,
        pressed = SpriteManager.play_press,
    )).apply {
        setBoundsFigmaY(PLAY_X, PLAY_Y, PLAY_W, PLAY_H)
        setOnClickListener { NavigationUtil.navigate(GameScreen(), MenuScreen()) }
    }

    private fun setUpExit() = ButtonClickable(
        ButtonClickable.Style(
        default = SpriteManager.exit_def,
        pressed = SpriteManager.exit_press,
    )).apply {
        setBoundsFigmaY(EXIT_X, EXIT_Y, EXIT_W, EXIT_H)
        setOnClickListener { NavigationUtil.exit() }
    }

    private fun setUpR() = ButtonClickable(
        ButtonClickable.Style(
            default = SpriteManager.rls,
            pressed = SpriteManager.rlsd,
        )).apply {
        setBounds(139f, 305f, 314f, 101f)
        setOnClickListener { NavigationUtil.navigate(RulesScreen(), MenuScreen()) }
    }

}