package com.fishfestival.bubbleparty.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.fishfestival.bubbleparty.game.actors.ATmpGroup
import com.fishfestival.bubbleparty.game.utils.Block
import com.fishfestival.bubbleparty.game.utils.HEIGHT_UI
import com.fishfestival.bubbleparty.game.utils.TIME_ANIM_SCREEN
import com.fishfestival.bubbleparty.game.utils.WIDTH_UI
import com.fishfestival.bubbleparty.game.utils.actor.addActorWithConstraints
import com.fishfestival.bubbleparty.game.utils.actor.addActors
import com.fishfestival.bubbleparty.game.utils.actor.addAndFillActor
import com.fishfestival.bubbleparty.game.utils.actor.animDelay
import com.fishfestival.bubbleparty.game.utils.actor.animHide
import com.fishfestival.bubbleparty.game.utils.actor.animShow
import com.fishfestival.bubbleparty.game.utils.actor.setOnClickListener
import com.fishfestival.bubbleparty.game.utils.advanced.AdvancedScreen
import com.fishfestival.bubbleparty.game.utils.gdxGame

var GDX_isWin = true

class ResultScreen: AdvancedScreen() {

    private val aPanelGroup = ATmpGroup(this)
    private val aPanelImg   = Image(if (GDX_isWin) gdxGame.assetsAll.WIN else gdxGame.assetsAll.LOSE)

    override fun show() {
        gdxGame.soundUtil.apply { play(if (GDX_isWin) win else lose) }
        setBackBackground(if (GDX_isWin) gdxGame.assetsAll.B_WIN else gdxGame.assetsAll.B_LOSE)
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
        aPanelGroup.setSize(WIDTH_UI, HEIGHT_UI)
        addActorWithConstraints(aPanelGroup) {
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

        aMen.setBounds(290f, 90f, 220f, 220f)
        aNext.setBounds(570f, 90f, 220f, 220f)

        aMen.setOnClickListener { this@ResultScreen.animHideScreen { gdxGame.navigationManager.navigate(MenuScreen::class.java.name) } }
        aNext.setOnClickListener { this@ResultScreen.animHideScreen { gdxGame.goToGame() } }
    }

}