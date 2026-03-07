package com.puzzlertron.dohistorical.game.screens

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.puzzlertron.dohistorical.game.actors.ATimer
import com.puzzlertron.dohistorical.game.actors.ATmpGroup
import com.puzzlertron.dohistorical.game.actors.button.AButton
import com.puzzlertron.dohistorical.game.actors.puzzle.APuzzlePanel
import com.puzzlertron.dohistorical.game.utils.Block
import com.puzzlertron.dohistorical.game.utils.TIME_ANIM_SCREEN
import com.puzzlertron.dohistorical.game.utils.actor.addActorWithConstraints
import com.puzzlertron.dohistorical.game.utils.actor.addAndFillActor
import com.puzzlertron.dohistorical.game.utils.actor.animDelay
import com.puzzlertron.dohistorical.game.utils.actor.animHide
import com.puzzlertron.dohistorical.game.utils.actor.animShow
import com.puzzlertron.dohistorical.game.utils.actor.disable
import com.puzzlertron.dohistorical.game.utils.advanced.AdvancedScreen
import com.puzzlertron.dohistorical.game.utils.gdxGame
import com.puzzlertron.dohistorical.game.utils.puzzle.Puzzles
import com.puzzlertron.dohistorical.game.utils.region

class GameScreen: AdvancedScreen() {

    private val aBackBtn   = AButton(this, AButton.Type.BACK)
    private val aGameGroup = ATmpGroup(this)

    override fun show() {
        setBackBackground(gdxGame.assetsAll.B_DEF)
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
        aBackBtn.setSize(148f, 137f)
        addActorWithConstraints(aBackBtn) {
            startToStartOf = this@addBtnBack
            topToTopOf     = this@addBtnBack

            marginStart = 77f
            marginTop   = 76f
        }
        aBackBtn.setOnClickListener { animHideScreen { gdxGame.navigationManager.back() } }

    }

    private fun Group.addGamePanel() {
        aGameGroup.setSize(978f, 1234f)
        addActorWithConstraints(aGameGroup) {
            startToStartOf   = this@addGamePanel
            endToEndOf       = this@addGamePanel
            topToBottomOf    = aBackBtn

            marginTop = 55f
        }
        aGameGroup.addAndFillActor(Image(gdxGame.assetsAll.GAME_PAN))

        aBackBtn.toFront()

        val aTimer       = ATimer(this@GameScreen)
        val puzzlesPanel = APuzzlePanel(this@GameScreen, gdxGame.assetsAll.listPuzzle.random().region)

        aGameGroup.apply {
            addActor(aTimer)
            addActor(puzzlesPanel)
        }

        aTimer.setBounds(370f, 1064f, 202f, 96f)
        aTimer.startTimer(60)
        aTimer.finishBlock = { animHideScreen { gdxGame.navigationManager.navigate(LoseScreen::class.java.name) } }

        puzzlesPanel.setBounds(146f, 129f, 684f, 684f)
        puzzlesPanel.finishBlock = {
            this.disable()
            animDelay(0.4f) {
                animHideScreen { gdxGame.navigationManager.navigate(WinScreen::class.java.name) }
            }
        }
    }

}