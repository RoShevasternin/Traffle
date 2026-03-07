package com.puzzlertron.dohistorical.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.puzzlertron.dohistorical.game.actors.ATmpGroup
import com.puzzlertron.dohistorical.game.utils.Block
import com.puzzlertron.dohistorical.game.utils.TIME_ANIM_SCREEN
import com.puzzlertron.dohistorical.game.utils.actor.addActorWithConstraints
import com.puzzlertron.dohistorical.game.utils.actor.addActors
import com.puzzlertron.dohistorical.game.utils.actor.addAndFillActor
import com.puzzlertron.dohistorical.game.utils.actor.animDelay
import com.puzzlertron.dohistorical.game.utils.actor.animHide
import com.puzzlertron.dohistorical.game.utils.actor.animShow
import com.puzzlertron.dohistorical.game.utils.actor.setOnClickListener
import com.puzzlertron.dohistorical.game.utils.advanced.AdvancedScreen
import com.puzzlertron.dohistorical.game.utils.gdxGame

class WinScreen: AdvancedScreen() {

    private val group    = ATmpGroup(this)
    private val imgPanel = Image(gdxGame.assetsAll.WIN_PAN)

    override fun show() {
        gdxGame.soundUtil.apply { play(win_game) }
        setBackBackground(gdxGame.assetsAll.B_WIN)
        super.show()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f
        addGroup()
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

    // Actors ------------------------------------------------------------------------

    private fun Group.addGroup() {
        group.setSize(882f, 1319f)
        addActorWithConstraints(group) {
            startToStartOf   = this@addGroup
            endToEndOf       = this@addGroup
            topToTopOf       = this@addGroup
            bottomToBottomOf = this@addGroup
        }

        group.apply {
            addAndFillActor(imgPanel)

            val aRestart = Actor()
            val aHome    = Actor()
            val aExit    = Actor()
            addActors(aRestart, aHome, aExit)
            aRestart.setBounds(177f, 666f, 543f, 205f)
            aHome   .setBounds(177f, 419f, 543f, 205f)
            aExit   .setBounds(177f, 171f, 543f, 205f)

            aRestart.setOnClickListener(gdxGame.soundUtil) {
                animHideScreen {
                    gdxGame.navigationManager.navigate(GameScreen::class.java.name)
                }
            }
            aHome.setOnClickListener(gdxGame.soundUtil) {
                animHideScreen {
                    gdxGame.navigationManager.clearBackStack()
                    gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
                }
            }
            aExit.setOnClickListener(gdxGame.soundUtil) {
                animHideScreen { gdxGame.navigationManager.exit() }
            }
        }

    }

}