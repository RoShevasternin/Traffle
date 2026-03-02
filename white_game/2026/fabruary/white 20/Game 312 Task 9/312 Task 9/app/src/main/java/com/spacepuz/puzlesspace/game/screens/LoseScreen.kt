package com.spacepuz.puzlesspace.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.spacepuz.puzlesspace.game.actors.ATmpGroup
import com.spacepuz.puzlesspace.game.utils.Block
import com.spacepuz.puzlesspace.game.utils.TIME_ANIM_SCREEN
import com.spacepuz.puzlesspace.game.utils.actor.addActorWithConstraints
import com.spacepuz.puzlesspace.game.utils.actor.addActors
import com.spacepuz.puzlesspace.game.utils.actor.addAndFillActor
import com.spacepuz.puzlesspace.game.utils.actor.animDelay
import com.spacepuz.puzlesspace.game.utils.actor.animHide
import com.spacepuz.puzlesspace.game.utils.actor.animShow
import com.spacepuz.puzlesspace.game.utils.actor.setOnClickListener
import com.spacepuz.puzlesspace.game.utils.advanced.AdvancedScreen
import com.spacepuz.puzlesspace.game.utils.gdxGame

class LoseScreen: AdvancedScreen() {

    private val group    = ATmpGroup(this)
    private val imgPanel = Image(gdxGame.assetsAll.RESTART_HOME)

    override fun show() {
        gdxGame.soundUtil.apply { play(lose_game) }
        setBackBackground(gdxGame.assetsAll.LOSE)
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
        group.setSize(917f, 165f)
        addActorWithConstraints(group) {
            startToStartOf   = this@addGroup
            endToEndOf       = this@addGroup
            bottomToBottomOf = this@addGroup

            marginBottom = 488f
        }

        group.apply {
            addAndFillActor(imgPanel)

            val aRestart = Actor()
            val aHome    = Actor()
            addActors(aRestart, aHome)
            aRestart.setBounds(0f, 0f, 444f, 164f)
            aHome.setBounds(470f, 0f, 444f, 164f)

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
        }

    }

}