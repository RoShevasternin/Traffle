package com.skynebowow.runnerblue.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.skynebowow.runnerblue.game.GDXGame
import com.skynebowow.runnerblue.game.actors.button.AButton
import com.skynebowow.runnerblue.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.skynebowow.runnerblue.game.utils.actor.animHide
import com.skynebowow.runnerblue.game.utils.actor.animShow
import com.skynebowow.runnerblue.game.utils.actor.setBounds
import com.skynebowow.runnerblue.game.utils.actor.setOnClickListener
import com.skynebowow.runnerblue.game.utils.advanced.AdvancedScreen
import com.skynebowow.runnerblue.game.utils.advanced.AdvancedStage
import com.skynebowow.runnerblue.game.utils.gdxGame
import com.skynebowow.runnerblue.game.utils.region

class RulesScreen(override val game: GDXGame) : AdvancedScreen() {

    private val aRulesImg = Image(gdxGame.gameAssets.RLS)
    private val aBackBtn  = AButton(this, AButton.Static.Type.BACK)

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.gameAssets.BK_REST.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM_SCREEN_ALPHA)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addRules()
        addBack()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addBack() {
        addActor(aBackBtn)
        aBackBtn.setBounds(62f, 1757f, 110f, 110f)

        aBackBtn.setOnClickListener {
            stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) { game.navigationManager.back() }
        }
    }

    private fun AdvancedStage.addRules() {
        addActor(aRulesImg)
        aRulesImg.setBounds(72f, 211f, 936f, 1375f)

        val a = Actor()
        addActor(a)
        a.setBounds(449f, 211f, 197f, 197f)
        a.setOnClickListener(gdxGame.soundUtil) {
            stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) { gdxGame.navigationManager.navigate(GameScreen::class.java.name) }
        }
    }

}