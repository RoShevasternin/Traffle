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

class AWin(override val screen: AdvancedScreen): AdvancedGroup() {
    
    private val listStars = screen.game.assetsAll.listStars

    private val imgPanel = Image(screen.game.assetsAll.win)
    private val imgStars = Image(listStars[SelectLevelScreen.DIFFICULTY_INDEX])
    private val aMain    = Actor()
    private val aAgain   = Actor()

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addImgStars()
        addMain()
        addAgain()
    }

    private fun addImgStars() {
        addActor(imgStars)
        imgStars.setBounds(317f, 922f, 492f, 212f)
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