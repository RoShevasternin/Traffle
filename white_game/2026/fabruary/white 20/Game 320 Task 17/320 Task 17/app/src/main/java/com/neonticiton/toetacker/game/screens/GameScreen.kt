package com.neonticiton.toetacker.game.screens

import com.badlogic.gdx.scenes.scene2d.Group
import com.neonticiton.toetacker.game.actors.APanelGame
import com.neonticiton.toetacker.game.actors.button.AButton
import com.neonticiton.toetacker.game.utils.Block
import com.neonticiton.toetacker.game.utils.TIME_ANIM_SCREEN
import com.neonticiton.toetacker.game.utils.actor.addActorWithConstraints
import com.neonticiton.toetacker.game.utils.actor.animDelay
import com.neonticiton.toetacker.game.utils.actor.animHide
import com.neonticiton.toetacker.game.utils.actor.animShow
import com.neonticiton.toetacker.game.utils.advanced.AdvancedScreen
import com.neonticiton.toetacker.game.utils.gdxGame

class GameScreen: AdvancedScreen() {

    private val aBackBtn   = AButton(this, AButton.Type.MENU)
    private val aPanelGame = APanelGame(this)

    override fun show() {
        setBackBackground(gdxGame.assetsAll.B_DEF)
        super.show()
        animShowScreen()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        addBtnBack()
        addGamePanel()
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

    private fun Group.addGamePanel() {
        aPanelGame.setSize(1122f, 1527f)
        addActorWithConstraints(aPanelGame) {
            startToStartOf   = this@addGamePanel
            endToEndOf       = this@addGamePanel
            topToTopOf       = this@addGamePanel
            bottomToBottomOf = this@addGamePanel

            verticalBias = 0.47f
        }

        aPanelGame.apply {
            blockWin  = { animHideScreen { gdxGame.navigationManager.navigate(WinScreen::class.java.name) } }
            blockLose = { animHideScreen { gdxGame.navigationManager.navigate(LoseScreen::class.java.name) } }
        }
    }

}