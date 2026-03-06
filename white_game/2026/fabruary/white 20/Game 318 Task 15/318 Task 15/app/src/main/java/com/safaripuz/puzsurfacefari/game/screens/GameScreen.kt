package com.safaripuz.puzsurfacefari.game.screens

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.safaripuz.puzsurfacefari.game.actors.ATimer
import com.safaripuz.puzsurfacefari.game.actors.ATmpGroup
import com.safaripuz.puzsurfacefari.game.actors.button.AButton
import com.safaripuz.puzsurfacefari.game.actors.puzzle.APuzzlePanel
import com.safaripuz.puzsurfacefari.game.utils.Block
import com.safaripuz.puzsurfacefari.game.utils.TIME_ANIM_SCREEN
import com.safaripuz.puzsurfacefari.game.utils.actor.addActorWithConstraints
import com.safaripuz.puzsurfacefari.game.utils.actor.addAndFillActor
import com.safaripuz.puzsurfacefari.game.utils.actor.animDelay
import com.safaripuz.puzsurfacefari.game.utils.actor.animHide
import com.safaripuz.puzsurfacefari.game.utils.actor.animShow
import com.safaripuz.puzsurfacefari.game.utils.actor.disable
import com.safaripuz.puzsurfacefari.game.utils.advanced.AdvancedScreen
import com.safaripuz.puzsurfacefari.game.utils.gdxGame
import com.safaripuz.puzsurfacefari.game.utils.puzzle.Puzzles
import com.safaripuz.puzsurfacefari.game.utils.region

class GameScreen: AdvancedScreen() {

    private val aBackBtn   = AButton(this, AButton.Type.BACK)
    private val aGameGroup = ATmpGroup(this)
    private val aLineImg   = Image(gdxGame.assetsAll.LION)

    override fun show() {
        setBackBackground(gdxGame.assetsAll.B_BLUR)
        super.show()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        addBtnBack()
        addGamePanel()
        addLionImg()

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

    private fun Group.addLionImg() {
        aLineImg.setSize(1024f, 732f)
        addActorWithConstraints(aLineImg) {
            startToStartOf = this@addLionImg
            bottomToBottomOf     = this@addLionImg

            marginStart = 28f
        }
    }

    private fun Group.addBtnBack() {
        aBackBtn.setSize(150f, 150f)
        addActorWithConstraints(aBackBtn) {
            startToStartOf = this@addBtnBack
            topToTopOf     = this@addBtnBack

            marginStart = 71f
            marginTop   = 59f
        }
        aBackBtn.setOnClickListener { animHideScreen { gdxGame.navigationManager.back() } }

    }

    private fun Group.addGamePanel() {
        aGameGroup.setSize(889f, 1138f)
        addActorWithConstraints(aGameGroup) {
            startToStartOf   = this@addGamePanel
            endToEndOf       = this@addGamePanel
            topToBottomOf    = aBackBtn

            marginTop = 51f
        }
        aGameGroup.addAndFillActor(Image(gdxGame.assetsAll.GAME_PAN))

        aBackBtn.toFront()

        val aTimer       = ATimer(this@GameScreen)
        val puzzlesPanel = APuzzlePanel(this@GameScreen, gdxGame.assetsAll.listPuzzle.random().region)

        aGameGroup.apply {
            addActor(aTimer)
            addActor(puzzlesPanel)
        }

        aTimer.setBounds(354f, 983f, 190f, 108f)
        aTimer.startTimer(60)
        aTimer.finishBlock = { animHideScreen { gdxGame.navigationManager.navigate(LoseScreen::class.java.name) } }

        puzzlesPanel.setBounds(102f, 98f, 684f, 684f)
        puzzlesPanel.finishBlock = {
            this.disable()
            animDelay(0.4f) {
                animHideScreen { gdxGame.navigationManager.navigate(WinScreen::class.java.name) }
            }
        }
    }

}