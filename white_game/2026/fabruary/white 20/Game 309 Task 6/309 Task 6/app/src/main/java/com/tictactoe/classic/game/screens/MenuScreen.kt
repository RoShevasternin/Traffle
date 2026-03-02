package com.tictactoe.classic.game.screens

import com.badlogic.gdx.scenes.scene2d.Group
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

class MenuScreen: AdvancedScreen() {

    private val aPlayBtn   = AButton(this, AButton.Type.PLAY)
    private val aRulesBtn  = AButton(this, AButton.Type.RULES)
    private val aSoundBox  = ACheckBox(this, ACheckBox.Type.SOUND)

    override fun show() {
        setBackBackground(gdxGame.assetsAll.BACKGROUND_MENU)
        super.show()
        animShowScreen()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        addPlayBtn()
        addRulesBtn()
        addSoundBox()

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

    private fun Group.addPlayBtn() {
        aPlayBtn.setSize(632f, 202f)
        addActorWithConstraints(aPlayBtn) {
            startToStartOf   = this@addPlayBtn
            endToEndOf       = this@addPlayBtn
            topToTopOf       = this@addPlayBtn
            bottomToBottomOf = this@addPlayBtn

            verticalBias = 0.6f
        }

        aPlayBtn.setOnClickListener {
            animHideScreen { gdxGame.navigationManager.navigate(GameScreen::class.java.name, MenuScreen::class.java.name) }
        }

    }

    private fun Group.addRulesBtn() {
        aRulesBtn.setSize(517f, 166f)
        addActorWithConstraints(aRulesBtn) {
            startToStartOf = aPlayBtn
            topToBottomOf  = aPlayBtn

            marginStart = 58f
            marginTop   = 38f
        }

        aRulesBtn.setOnClickListener {
            animHideScreen { gdxGame.navigationManager.navigate(RulesScreen::class.java.name, MenuScreen::class.java.name) }
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