package com.bunnypanny.eggcatch.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.bunnypanny.eggcatch.game.GDXGame
import com.bunnypanny.eggcatch.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.bunnypanny.eggcatch.game.utils.actor.animHide
import com.bunnypanny.eggcatch.game.utils.actor.animShow
import com.bunnypanny.eggcatch.game.utils.actor.setOnClickListener
import com.bunnypanny.eggcatch.game.utils.advanced.AdvancedScreen
import com.bunnypanny.eggcatch.game.utils.advanced.AdvancedStage
import com.bunnypanny.eggcatch.game.utils.gdxGame
import com.bunnypanny.eggcatch.game.utils.region

class WinScreen(override val game: GDXGame) : AdvancedScreen() {

    private val aPanelImg = Image(gdxGame.assetsAll.DONE)

    override fun show() {
        gdxGame.soundUtil.apply { play(win) }
        stageUI.root.animHide()
        setBackBackground(game.assetsAll.WIN.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM_SCREEN_ALPHA)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addPanel()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addPanel() {
        addActor(aPanelImg)
        aPanelImg.setBounds(204f, 342f, 687f, 1495f)

        val aPlay = Actor()
        addActor(aPlay)
        aPlay.setBounds(216f, 954f, 647f, 274f)
        aPlay.setOnClickListener(gdxGame.soundUtil) {
            stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) { gdxGame.navigationManager.navigate(GameScreen::class.java.name) }
        }

        val aSettings = Actor()
        addActor(aSettings)
        aSettings.setBounds(216f, 648f, 647f, 274f)
        aSettings.setOnClickListener(gdxGame.soundUtil) {
            stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) { gdxGame.navigationManager.navigate(SettingsScreen::class.java.name) }
        }

        val aExit = Actor()
        addActor(aExit)
        aExit.setBounds(216f, 342f, 647f, 274f)
        aExit.setOnClickListener(gdxGame.soundUtil) {
            stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) { gdxGame.navigationManager.back() }
        }
    }

}