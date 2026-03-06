package com.bunnypanny.eggcatch.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.bunnypanny.eggcatch.game.GDXGame
import com.bunnypanny.eggcatch.game.actors.button.AButton
import com.bunnypanny.eggcatch.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.bunnypanny.eggcatch.game.utils.actor.animHide
import com.bunnypanny.eggcatch.game.utils.actor.animShow
import com.bunnypanny.eggcatch.game.utils.advanced.AdvancedScreen
import com.bunnypanny.eggcatch.game.utils.advanced.AdvancedStage
import com.bunnypanny.eggcatch.game.utils.gdxGame
import com.bunnypanny.eggcatch.game.utils.region

class RulesScreen(override val game: GDXGame) : AdvancedScreen() {

    private val aRulesImg = Image(gdxGame.assetsAll.RULES_PAN)
    private val aBackBtn  = AButton(this, AButton.Static.Type.BACK)

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.assetsAll.BACKGROUND.region)
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
        aBackBtn.setBounds(75f, 1680f, 220f, 220f)

        aBackBtn.setOnClickListener {
            stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) { game.navigationManager.back() }
        }
    }

    private fun AdvancedStage.addRules() {
        addActor(aRulesImg)
        aRulesImg.setBounds(107f, 437f, 867f, 962f)
    }

}