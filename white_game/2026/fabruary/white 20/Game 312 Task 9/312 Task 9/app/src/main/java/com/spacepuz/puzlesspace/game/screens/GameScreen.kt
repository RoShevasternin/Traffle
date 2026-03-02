package com.spacepuz.puzlesspace.game.screens

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.spacepuz.puzlesspace.game.actors.ATimer
import com.spacepuz.puzlesspace.game.actors.ATmpGroup
import com.spacepuz.puzlesspace.game.actors.button.AButton
import com.spacepuz.puzlesspace.game.actors.puzzle.APuzzlePanel
import com.spacepuz.puzlesspace.game.utils.Block
import com.spacepuz.puzlesspace.game.utils.TIME_ANIM_SCREEN
import com.spacepuz.puzlesspace.game.utils.actor.addActorWithConstraints
import com.spacepuz.puzlesspace.game.utils.actor.addAndFillActor
import com.spacepuz.puzlesspace.game.utils.actor.animDelay
import com.spacepuz.puzlesspace.game.utils.actor.animHide
import com.spacepuz.puzlesspace.game.utils.actor.animShow
import com.spacepuz.puzlesspace.game.utils.actor.disable
import com.spacepuz.puzlesspace.game.utils.advanced.AdvancedScreen
import com.spacepuz.puzlesspace.game.utils.gdxGame
import com.spacepuz.puzlesspace.game.utils.puzzle.Puzzles
import com.spacepuz.puzlesspace.game.utils.region

class GameScreen: AdvancedScreen() {

    private val aBackBtn   = AButton(this, AButton.Type.BACK)
    private val aGameGroup = ATmpGroup(this)

    override fun show() {
        setBackBackground(gdxGame.assetsAll.DEF)
        super.show()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        addBtnBack()
        addGamePanel()

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

    override fun dispose() {
        Puzzles.dispose()
        super.dispose()
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

    private fun Group.addGamePanel() {
        aGameGroup.setSize(876f, 1362f)
        addActorWithConstraints(aGameGroup) {
            startToStartOf   = this@addGamePanel
            endToEndOf       = this@addGamePanel
            topToTopOf       = aBackBtn
        }
        aGameGroup.addAndFillActor(Image(gdxGame.assetsAll.GAME_PANEL))

        aBackBtn.toFront()

        val aTimer       = ATimer(this@GameScreen)
        val puzzlesPanel = APuzzlePanel(this@GameScreen, gdxGame.assetsAll.listPuzzle.random().region)

        aGameGroup.apply {
            addActor(aTimer)
            addActor(puzzlesPanel)
        }

        aTimer.setBounds(231f, 1132f, 423f, 157f)
        aTimer.startTimer(60)
        aTimer.finishBlock = { animHideScreen { gdxGame.navigationManager.navigate(LoseScreen::class.java.name) } }

        puzzlesPanel.setBounds(96f, 130f, 684f, 684f)
        puzzlesPanel.finishBlock = {
            this.disable()
            animDelay(0.4f) {
                animHideScreen { gdxGame.navigationManager.navigate(WinScreen::class.java.name) }
            }
        }
    }

}