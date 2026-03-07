package com.neonticiton.toetacker.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.neonticiton.toetacker.game.actors.ATmpGroup
import com.neonticiton.toetacker.game.utils.Block
import com.neonticiton.toetacker.game.utils.TIME_ANIM_SCREEN
import com.neonticiton.toetacker.game.utils.actor.addActorWithConstraints
import com.neonticiton.toetacker.game.utils.actor.addActors
import com.neonticiton.toetacker.game.utils.actor.addAndFillActor
import com.neonticiton.toetacker.game.utils.actor.animDelay
import com.neonticiton.toetacker.game.utils.actor.animHide
import com.neonticiton.toetacker.game.utils.actor.animShow
import com.neonticiton.toetacker.game.utils.actor.setOnClickListener
import com.neonticiton.toetacker.game.utils.advanced.AdvancedScreen
import com.neonticiton.toetacker.game.utils.gdxGame

class LoseScreen: AdvancedScreen() {

    private val group    = ATmpGroup(this)
    private val imgPanel = Image(gdxGame.assetsAll.AGAIN)

    override fun show() {
        gdxGame.soundUtil.apply { play(fail) }
        setBackBackground(gdxGame.assetsAll.B_DEF)
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
        group.setSize(894f, 754f)
        addActorWithConstraints(group) {
            startToStartOf   = this@addGroup
            endToEndOf       = this@addGroup
            topToTopOf       = this@addGroup
            bottomToBottomOf = this@addGroup
        }

        group.apply {
            addAndFillActor(imgPanel)

            val aRestart = Actor()
            val aRules   = Actor()
            addActors(aRestart, aRules)
            aRestart.setBounds(97f, 251f, 713f, 212f)
            aRules.setBounds(97f, 0f, 713f, 212f)

            aRestart.setOnClickListener(gdxGame.soundUtil) {
                animHideScreen {
                    gdxGame.navigationManager.navigate(GameScreen::class.java.name)
                }
            }
            aRules.setOnClickListener(gdxGame.soundUtil) {
                animHideScreen {
                    gdxGame.navigationManager.clearBackStack()
                    gdxGame.navigationManager.navigate(RulesScreen::class.java.name)
                }
            }
        }

    }

}