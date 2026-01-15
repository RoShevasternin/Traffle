package com.toyscatcher.factoryatoy.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.viewport.FitViewport
import com.toyscatcher.factoryatoy.HEIGHT
import com.toyscatcher.factoryatoy.WIDTH
import com.toyscatcher.factoryatoy.advanced.AdvancedScreen
import com.toyscatcher.factoryatoy.assets.SpriteManager
import com.toyscatcher.factoryatoy.utils.*

class RulesScreen : AdvancedScreen() {
    override val viewport = FitViewport(WIDTH, HEIGHT)



    override fun show() {
        super.show()
        background = SpriteManager.bek

        setUpPlay()
        Gdx.input.setCatchKey(Input.Keys.BACK, true)
    }

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Input.Keys.BACK) NavigationUtil.back()
        return false
    }



    private fun setUpPlay() {
        val img = Image(SpriteManager.pan)
        stage.addActor(img)
        img.setBounds(55f, 406f, 601f, 691f)
    }

}