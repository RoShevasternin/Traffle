package com.spacepuz.puzlesspace.game.screens

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.spacepuz.puzlesspace.game.actors.button.AButton
import com.spacepuz.puzlesspace.game.actors.checkbox.ACheckBox
import com.spacepuz.puzlesspace.game.utils.Block
import com.spacepuz.puzlesspace.game.utils.TIME_ANIM_SCREEN
import com.spacepuz.puzlesspace.game.utils.actor.addActorWithConstraints
import com.spacepuz.puzlesspace.game.utils.actor.animDelay
import com.spacepuz.puzlesspace.game.utils.actor.animHide
import com.spacepuz.puzlesspace.game.utils.actor.animShow
import com.spacepuz.puzlesspace.game.utils.advanced.AdvancedScreen
import com.spacepuz.puzlesspace.game.utils.gdxGame
import com.spacepuz.puzlesspace.util.log

class RulesScreen: AdvancedScreen() {

    private val aRulesImg = Image(gdxGame.assetsAll.PANEL_RULES)
    private val aBackBtn  = AButton(this, AButton.Type.BACK)

    override fun show() {
        setBackBackground(gdxGame.assetsAll.RULES)
        super.show()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        addRulesImg()
        addBtnBack()

        animShowScreen()
    }

    override fun animHideScreen(blockEnd: Block) {
        stageUI.root.animHide(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    override fun animShowScreen(blockEnd: Block) {
        stageUI.root.animShow(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    // Actors ------------------------------------------------------------------------

    private fun Group.addBtnBack() {
        aBackBtn.setSize(110f, 110f)
        addActorWithConstraints(aBackBtn) {
            startToStartOf = this@addBtnBack
            topToTopOf     = this@addBtnBack

            marginStart = 85f
            marginTop   = 65f
        }

        aBackBtn.setOnClickListener { animHideScreen { gdxGame.navigationManager.back() } }

    }

    private fun Group.addRulesImg() {
        aRulesImg.setSize(876f, 943f)
        addActorWithConstraints(aRulesImg) {
            startToStartOf   = this@addRulesImg
            endToEndOf       = this@addRulesImg
            topToTopOf       = this@addRulesImg
            bottomToBottomOf = this@addRulesImg
        }
    }

}