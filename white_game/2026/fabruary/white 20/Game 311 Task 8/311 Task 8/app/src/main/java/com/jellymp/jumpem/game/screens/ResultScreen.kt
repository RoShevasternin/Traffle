package com.jellymp.jumpem.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.jellymp.jumpem.game.actors.ATmpGroup
import com.jellymp.jumpem.game.utils.Block
import com.jellymp.jumpem.game.utils.TIME_ANIM_SCREEN
import com.jellymp.jumpem.game.utils.actor.addActorWithConstraints
import com.jellymp.jumpem.game.utils.actor.addActors
import com.jellymp.jumpem.game.utils.actor.addAndFillActor
import com.jellymp.jumpem.game.utils.actor.animDelay
import com.jellymp.jumpem.game.utils.actor.animHide
import com.jellymp.jumpem.game.utils.actor.animShow
import com.jellymp.jumpem.game.utils.actor.setOnClickListener
import com.jellymp.jumpem.game.utils.advanced.AdvancedScreen
import com.jellymp.jumpem.game.utils.gdxGame

var GDX_isWin = true

class ResultScreen: AdvancedScreen() {

    private val aPanelGroup = ATmpGroup(this)
    private val aPanelImg   = Image(if (GDX_isWin) gdxGame.assetsAll.WIN else gdxGame.assetsAll.LOSE)

    override fun show() {
        gdxGame.soundUtil.apply { play(if (GDX_isWin) win else lose) }
        setBackBackground(if (GDX_isWin) gdxGame.assetsAll.BACKGROUND_DEF else gdxGame.assetsAll.BACKGROUND_GRAY)
        super.show()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        addPanelGroup()

        animShow()
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

    private fun Group.addPanelGroup() {
        aPanelGroup.setSize(1227f, 891f)
        addActorWithConstraints(aPanelGroup) {
            topToTopOf       = this@addPanelGroup
            bottomToBottomOf = this@addPanelGroup
            startToStartOf   = this@addPanelGroup
            endToEndOf       = this@addPanelGroup
        }

        val aMen  = Actor()
        val aNext = Actor()
        aPanelGroup.apply {
            addAndFillActor(aPanelImg)
            addActors(aMen, aNext)
        }

        aMen.setBounds(302f, 150f, 292f, 317f)
        aNext.setBounds(626f, 150f, 292f, 317f)

        aMen.setOnClickListener { this@ResultScreen.animHideScreen { gdxGame.navigationManager.back() } }
        aNext.setOnClickListener { this@ResultScreen.animHideScreen { gdxGame.navigationManager.navigate(GameScreen::class.java.name) } }
    }

}