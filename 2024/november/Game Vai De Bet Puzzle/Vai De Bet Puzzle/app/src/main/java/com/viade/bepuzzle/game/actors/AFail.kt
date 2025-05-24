package com.viade.bepuzzle.game.actors

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.viade.bepuzzle.game.screens.GameScreen
import com.viade.bepuzzle.game.screens.MenuScreen
import com.viade.bepuzzle.game.screens.SelectLevelScreen
import com.viade.bepuzzle.game.utils.actor.setOnClickListener
import com.viade.bepuzzle.game.utils.advanced.AdvancedGroup
import com.viade.bepuzzle.game.utils.advanced.AdvancedScreen

class AFail(override val screen: AdvancedScreen): AdvancedGroup() {

    private val imgPanel = Image(screen.game.assetsAll.fail)
    private val aMain    = Actor()
    private val aAgain   = Actor()

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addMain()
        addAgain()
    }

    private fun addMain() {
        addActor(aMain)
        aMain.setBounds(392f, 409f, 338f, 100f)
        aMain.setOnClickListener(screen.game.soundUtil) {
            screen.game.navigationManager.clearBackStack()
            screen.game.navigationManager.navigate(MenuScreen::class.java.name)
        }
    }

    private fun addAgain() {
        addActor(aAgain)
        aAgain.setBounds(392f, 257f, 338f, 100f)
        aAgain.setOnClickListener(screen.game.soundUtil) {
            screen.game.navigationManager.navigate(GameScreen::class.java.name)
        }
    }

}