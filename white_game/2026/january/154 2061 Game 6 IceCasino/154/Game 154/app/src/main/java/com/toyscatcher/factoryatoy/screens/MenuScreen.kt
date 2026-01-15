package com.toyscatcher.factoryatoy.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.utils.viewport.FitViewport
import com.toyscatcher.factoryatoy.HEIGHT
import com.toyscatcher.factoryatoy.WIDTH
import com.toyscatcher.factoryatoy.actors.ButtonClickable
import com.toyscatcher.factoryatoy.advanced.AdvancedScreen
import com.toyscatcher.factoryatoy.assets.SpriteManager
import com.toyscatcher.factoryatoy.utils.*

class MenuScreen : AdvancedScreen() {
    override val viewport = FitViewport(WIDTH, HEIGHT)



    override fun show() {
        super.show()
        background = SpriteManager.menu_background
        stage.addActors(getActors())
    }



    private fun getActors() = listOf<Actor>(
        setUpPlay(),
        setUpExit(),
        setUpRules()
    )



    private fun setUpPlay() = ButtonClickable(ButtonClickable.Style(
        default = SpriteManager.play_def,
        pressed = SpriteManager.play_press,
    )).apply {
        setBoundsFigmaY(PLAY_X, PLAY_Y, PLAY_W, PLAY_H)
        setOnClickListener { NavigationUtil.navigate(GameScreen(), MenuScreen()) }
    }

    private fun setUpExit() = ButtonClickable(ButtonClickable.Style(
        default = SpriteManager.exit_def,
        pressed = SpriteManager.exit_press,
    )).apply {
        setBoundsFigmaY(EXIT_X, EXIT_Y, EXIT_W, EXIT_H)
        setOnClickListener { NavigationUtil.exit() }
    }

    private fun setUpRules() = ButtonClickable(ButtonClickable.Style(
        default = SpriteManager.rules,
        pressed = SpriteManager.exit_press,
    )).apply {
        setBoundsFigmaY(328f, 821f, 328f, 135f)
        setOnClickListener { NavigationUtil.navigate(RulesScreen(), MenuScreen()) }
    }

}