package com.neonticiton.toetacker.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.neonticiton.toetacker.game.actors.ATmpGroup
import com.neonticiton.toetacker.game.actors.button.AButton
import com.neonticiton.toetacker.game.actors.checkbox.ACheckBox
import com.neonticiton.toetacker.game.utils.Block
import com.neonticiton.toetacker.game.utils.TIME_ANIM_SCREEN
import com.neonticiton.toetacker.game.utils.actor.addActorWithConstraints
import com.neonticiton.toetacker.game.utils.actor.addAndFillActor
import com.neonticiton.toetacker.game.utils.actor.animDelay
import com.neonticiton.toetacker.game.utils.actor.animHide
import com.neonticiton.toetacker.game.utils.actor.animShow
import com.neonticiton.toetacker.game.utils.actor.setOnClickListener
import com.neonticiton.toetacker.game.utils.advanced.AdvancedScreen
import com.neonticiton.toetacker.game.utils.gdxGame
import com.neonticiton.toetacker.util.log

class RulesScreen: AdvancedScreen() {

    private val aRulesImg = Image(gdxGame.assetsAll.RULES)
    private val aBackBtn  = AButton(this, AButton.Type.MENU)

    override fun show() {
        setBackBackground(gdxGame.assetsAll.B_DEF)
        super.show()
        animShowScreen()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        addRulesImg()
        addBtnBack()

        animShow()
    }

    override fun animHideScreen(blockEnd: Block) {
        stageUI.root.children.onEach { it.clearActions() }

        stageUI.root.animHide(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    override fun animShowScreen(blockEnd: Block) {
        stageUI.root.children.onEach { it.clearActions() }

        stageUI.root.animShow(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    // Actors ------------------------------------------------------------------------

    private fun Group.addBtnBack() {
        aBackBtn.setSize(119f, 121f)
        addActorWithConstraints(aBackBtn) {
            startToStartOf = this@addBtnBack
            topToTopOf     = this@addBtnBack

            marginStart = 65f
            marginTop   = 41f
        }

        aBackBtn.setOnClickListener { animHideScreen { gdxGame.navigationManager.back() } }

    }

    private fun Group.addRulesImg() {
        val aTmpRules = ATmpGroup(this@RulesScreen)
        aTmpRules.setSize(885f, 1316f)
        addActorWithConstraints(aTmpRules) {
            startToStartOf   = this@addRulesImg
            endToEndOf       = this@addRulesImg
            topToTopOf       = this@addRulesImg
            bottomToBottomOf = this@addRulesImg

            verticalBias = 0.35f
        }

        val aPlay = Actor()

        aTmpRules.apply {
            addAndFillActor(aRulesImg)
            addActor(aPlay)
        }

        aPlay.setBounds(186f, 0f, 512f, 160f)
        aPlay.setOnClickListener(gdxGame.soundUtil) {
            animHideScreen { gdxGame.navigationManager.navigate(GameScreen::class.java.name, MenuScreen::class.java.name) }
        }
    }

}