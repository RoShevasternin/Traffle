package com.tictactoe.classic.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.tictactoe.classic.game.actors.ATmpGroup
import com.tictactoe.classic.game.utils.Block
import com.tictactoe.classic.game.utils.TIME_ANIM_SCREEN
import com.tictactoe.classic.game.utils.actor.addActorWithConstraints
import com.tictactoe.classic.game.utils.actor.addActors
import com.tictactoe.classic.game.utils.actor.addAndFillActor
import com.tictactoe.classic.game.utils.actor.animDelay
import com.tictactoe.classic.game.utils.actor.animHide
import com.tictactoe.classic.game.utils.actor.animShow
import com.tictactoe.classic.game.utils.actor.setOnClickListener
import com.tictactoe.classic.game.utils.advanced.AdvancedScreen
import com.tictactoe.classic.game.utils.gdxGame

class WinScreen: AdvancedScreen() {

    private val group    = ATmpGroup(this)
    private val imgPanel = Image(gdxGame.assetsAll.WIN)

    override fun show() {
        gdxGame.soundUtil.apply { play(win_game) }
        setBackBackground(gdxGame.assetsAll.GREEN)
        super.show()
        animShowScreen()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f
        addGroup()
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

    private fun Group.addGroup() {
        group.setSize(677f, 646f)
        addActorWithConstraints(group) {
            startToStartOf   = this@addGroup
            endToEndOf       = this@addGroup
            topToTopOf       = this@addGroup
            bottomToBottomOf = this@addGroup
        }

        group.apply {
            addAndFillActor(imgPanel)

            val aN = Actor()
            val aM = Actor()
            addActors(aN, aM)
            aN.setBounds(23f, 204f, 631f, 202f)
            aM.setBounds(81f, 0f, 516f, 165f)

            aN.setOnClickListener(gdxGame.soundUtil) {
                animHideScreen {
                    gdxGame.navigationManager.navigate(GameScreen::class.java.name)
                }
            }
            aM.setOnClickListener(gdxGame.soundUtil) {
                animHideScreen {
                    gdxGame.navigationManager.clearBackStack()
                    gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
                }
            }
        }

    }

}