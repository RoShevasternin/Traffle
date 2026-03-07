package com.puzzlertron.dohistorical.game.screens

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.puzzlertron.dohistorical.game.actors.button.AButton
import com.puzzlertron.dohistorical.game.actors.checkbox.ACheckBox
import com.puzzlertron.dohistorical.game.utils.Block
import com.puzzlertron.dohistorical.game.utils.TIME_ANIM_SCREEN
import com.puzzlertron.dohistorical.game.utils.actor.addActorWithConstraints
import com.puzzlertron.dohistorical.game.utils.actor.animDelay
import com.puzzlertron.dohistorical.game.utils.actor.animHide
import com.puzzlertron.dohistorical.game.utils.actor.animShow
import com.puzzlertron.dohistorical.game.utils.advanced.AdvancedScreen
import com.puzzlertron.dohistorical.game.utils.gdxGame
import com.puzzlertron.dohistorical.util.log

class RulesScreen: AdvancedScreen() {

    private val aRulesImg = Image(gdxGame.assetsAll.RULES_PAN)
    private val aBackBtn  = AButton(this, AButton.Type.BACK)

    override fun show() {
        setBackBackground(gdxGame.assetsAll.B_DEF)
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
        aBackBtn.setSize(148f, 136f)
        addActorWithConstraints(aBackBtn) {
            startToStartOf = this@addBtnBack
            topToTopOf     = this@addBtnBack

            marginStart = 77f
            marginTop   = 76f
        }

        aBackBtn.setOnClickListener { animHideScreen { gdxGame.navigationManager.back() } }

    }

    private fun Group.addRulesImg() {
        aRulesImg.setSize(884f, 1044f)
        addActorWithConstraints(aRulesImg) {
            startToStartOf   = this@addRulesImg
            endToEndOf       = this@addRulesImg
            topToTopOf       = this@addRulesImg
            bottomToBottomOf = this@addRulesImg
        }
    }

}