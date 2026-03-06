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

class LoseScreen(override val game: GDXGame) : AdvancedScreen() {

    private val aPanelImg = Image(gdxGame.assetsAll.LOSE)

    override fun show() {
        gdxGame.soundUtil.apply { play(lose) }
        stageUI.root.animHide()
        setBackBackground(game.assetsAll.AGAIN.region)
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
        aPanelImg.setBounds(72f, 340f, 936f, 1246f)

        val aPlay = Actor()
        addActor(aPlay)
        aPlay.setBounds(256f, 691f, 585f, 247f)
        aPlay.setOnClickListener(gdxGame.soundUtil) {
            stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) { gdxGame.navigationManager.navigate(GameScreen::class.java.name) }
        }

        val aExit = Actor()
        addActor(aExit)
        aExit.setBounds(330f, 475f, 437f, 184f)
        aExit.setOnClickListener(gdxGame.soundUtil) {
            stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) { gdxGame.navigationManager.back() }
        }
    }

}