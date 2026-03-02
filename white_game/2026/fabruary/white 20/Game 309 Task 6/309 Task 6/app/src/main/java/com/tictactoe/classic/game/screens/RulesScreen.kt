package com.tictactoe.classic.game.screens

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.tictactoe.classic.game.actors.button.AButton
import com.tictactoe.classic.game.actors.checkbox.ACheckBox
import com.tictactoe.classic.game.utils.Block
import com.tictactoe.classic.game.utils.TIME_ANIM_SCREEN
import com.tictactoe.classic.game.utils.actor.addActorWithConstraints
import com.tictactoe.classic.game.utils.actor.animDelay
import com.tictactoe.classic.game.utils.actor.animHide
import com.tictactoe.classic.game.utils.actor.animShow
import com.tictactoe.classic.game.utils.advanced.AdvancedScreen
import com.tictactoe.classic.game.utils.gdxGame
import com.tictactoe.classic.util.log

class RulesScreen: AdvancedScreen() {

    private val aRulesImg = Image(gdxGame.assetsAll.RULES)
    private val aSoundBox = ACheckBox(this, ACheckBox.Type.SOUND)
    private val aBackBtn   = AButton(this, AButton.Type.MENU)

    override fun show() {
        setBackBackground(gdxGame.assetsAll.BACKGROUND_RULES)
        super.show()
        animShowScreen()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        addRulesImg()
        addSoundBox()
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
        aBackBtn.setSize(120f, 120f)
        addActorWithConstraints(aBackBtn) {
            endToEndOf     = this@addBtnBack
            topToTopOf     = this@addBtnBack

            marginEnd   = 83f
            marginTop   = 33f
        }

        aBackBtn.setOnClickListener { animHideScreen { gdxGame.navigationManager.back() } }

    }

    private fun Group.addRulesImg() {
        aRulesImg.setSize(882f, 1144f)
        addActorWithConstraints(aRulesImg) {
            startToStartOf   = this@addRulesImg
            endToEndOf       = this@addRulesImg
            topToTopOf       = this@addRulesImg
            bottomToBottomOf = this@addRulesImg
        }
    }

    private fun Group.addSoundBox() {
        aSoundBox.setSize(120f, 120f)
        addActorWithConstraints(aSoundBox) {
            startToStartOf = this@addSoundBox
            topToTopOf     = this@addSoundBox

            marginStart = 83f
            marginTop   = 33f
        }

        if (gdxGame.soundUtil.isPause) aSoundBox.check()

        aSoundBox.setOnCheckListener { isCheck ->
            gdxGame.soundUtil.isPause = isCheck
        }

    }

}