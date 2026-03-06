package com.bounceques.ternationaret.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.bounceques.ternationaret.game.LibGDXGame
import com.bounceques.ternationaret.game.actors.button.AButton
import com.bounceques.ternationaret.game.utils.TIME_ANIM
import com.bounceques.ternationaret.game.utils.actor.animHide
import com.bounceques.ternationaret.game.utils.actor.animShow
import com.bounceques.ternationaret.game.utils.advanced.AdvancedScreen
import com.bounceques.ternationaret.game.utils.advanced.AdvancedStage
import com.bounceques.ternationaret.game.utils.gdxGame
import com.bounceques.ternationaret.game.utils.region

class PinkRulesScreen(override val game: LibGDXGame): AdvancedScreen() {

    private val panelImg = Image(game.assetsAll.RULES_PAN)
    private val aBackBtn = AButton(this, AButton.Static.Type.BACK)

    override fun show() {
        stageUI.root.animHide(TIME_ANIM)
        setBackBackground(game.assetsAll.B1.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addPanel()
        addBack()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun AdvancedStage.addPanel() {
        addActors(panelImg)
        panelImg.setBounds(118f, 361f, 845f, 1198f)
    }

    private fun AdvancedStage.addBack() {
        addActors(aBackBtn)
        aBackBtn.setBounds(68f, 1707f, 156f, 165f)

        aBackBtn.setOnClickListener {
            stageUI.root.animHide(TIME_ANIM) { gdxGame.navigationManager.back() }
        }
    }

}