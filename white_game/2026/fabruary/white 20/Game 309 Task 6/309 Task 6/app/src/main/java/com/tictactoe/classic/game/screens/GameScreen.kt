package com.tictactoe.classic.game.screens

import com.badlogic.gdx.scenes.scene2d.Group
import com.tictactoe.classic.game.actors.APanelGame
import com.tictactoe.classic.game.actors.button.AButton
import com.tictactoe.classic.game.utils.Block
import com.tictactoe.classic.game.utils.TIME_ANIM_SCREEN
import com.tictactoe.classic.game.utils.actor.addActorWithConstraints
import com.tictactoe.classic.game.utils.actor.animDelay
import com.tictactoe.classic.game.utils.actor.animHide
import com.tictactoe.classic.game.utils.actor.animShow
import com.tictactoe.classic.game.utils.advanced.AdvancedScreen
import com.tictactoe.classic.game.utils.gdxGame

class GameScreen: AdvancedScreen() {

    private val aBackBtn   = AButton(this, AButton.Type.MENU)
    private val aPanelGame = APanelGame(this)

    override fun show() {
        setBackBackground(gdxGame.assetsAll.BACKGROUND_GAME)
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
        aBackBtn.setSize(120f, 120f)
        addActorWithConstraints(aBackBtn) {
            startToStartOf = this@addBtnBack
            topToTopOf     = this@addBtnBack

            marginStart = 83f
            marginTop   = 33f
        }

        aBackBtn.setOnClickListener { animHideScreen { gdxGame.navigationManager.back() } }

    }

    private fun Group.addGamePanel() {
        aPanelGame.setSize(882f, 1365f)
        addActorWithConstraints(aPanelGame) {
            startToStartOf   = this@addGamePanel
            endToEndOf       = this@addGamePanel
            topToTopOf       = this@addGamePanel
            bottomToBottomOf = this@addGamePanel
        }

        aPanelGame.apply {
            blockWin  = { animHideScreen { gdxGame.navigationManager.navigate(WinScreen::class.java.name) } }
            blockLose = { animHideScreen { gdxGame.navigationManager.navigate(LoseScreen::class.java.name) } }
        }
    }

}