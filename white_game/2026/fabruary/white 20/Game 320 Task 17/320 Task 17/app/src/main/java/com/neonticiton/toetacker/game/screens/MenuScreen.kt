package com.neonticiton.toetacker.game.screens

import com.badlogic.gdx.scenes.scene2d.Group
import com.neonticiton.toetacker.game.actors.button.AButton
import com.neonticiton.toetacker.game.actors.checkbox.ACheckBox
import com.neonticiton.toetacker.game.utils.Block
import com.neonticiton.toetacker.game.utils.TIME_ANIM_SCREEN
import com.neonticiton.toetacker.game.utils.actor.addActorWithConstraints
import com.neonticiton.toetacker.game.utils.actor.animDelay
import com.neonticiton.toetacker.game.utils.actor.animHide
import com.neonticiton.toetacker.game.utils.actor.animShow
import com.neonticiton.toetacker.game.utils.advanced.AdvancedScreen
import com.neonticiton.toetacker.game.utils.gdxGame

class MenuScreen: AdvancedScreen() {

    private val aPlayBtn   = AButton(this, AButton.Type.PLAY)
    private val aRulesBtn  = AButton(this, AButton.Type.RULES)
    private val aSoundBox  = ACheckBox(this, ACheckBox.Type.SOUND)
    private val aMusicBox  = ACheckBox(this, ACheckBox.Type.MUSIC)

    override fun show() {
        setBackBackground(gdxGame.assetsAll.B_DEF)
        super.show()
        animShowScreen()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        addPlayBtn()
        addRulesBtn()
        addSoundBox()
        addMusicBox()

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
        aPlayBtn.setSize(713f, 212f)
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
        aRulesBtn.setSize(713f, 212f)
        addActorWithConstraints(aRulesBtn) {
            startToStartOf = aPlayBtn
            topToBottomOf  = aPlayBtn

            marginTop = 39f
        }

        aRulesBtn.setOnClickListener {
            animHideScreen { gdxGame.navigationManager.navigate(RulesScreen::class.java.name, MenuScreen::class.java.name) }
        }

    }

    private fun Group.addSoundBox() {
        aSoundBox.setSize(119f, 121f)
        addActorWithConstraints(aSoundBox) {
            startToStartOf = this@addSoundBox
            topToTopOf     = this@addSoundBox

            marginStart = 65f
            marginTop   = 41f
        }

        if (gdxGame.soundUtil.isPause) aSoundBox.check()

        aSoundBox.setOnCheckListener { isCheck ->
            gdxGame.soundUtil.isPause = isCheck
        }

    }

    private fun Group.addMusicBox() {
        aMusicBox.setSize(119f, 121f)
        addActorWithConstraints(aMusicBox) {
            endToEndOf     = this@addMusicBox
            topToTopOf     = this@addMusicBox

            marginEnd   = 65f
            marginTop   = 41f
        }

        if (gdxGame.musicUtil.currentMusic?.isPlaying == false) aMusicBox.check()

        aMusicBox.setOnCheckListener { isCheck ->
            if (isCheck) gdxGame.musicUtil.currentMusic?.pause() else gdxGame.musicUtil.currentMusic?.play()
        }

    }

}