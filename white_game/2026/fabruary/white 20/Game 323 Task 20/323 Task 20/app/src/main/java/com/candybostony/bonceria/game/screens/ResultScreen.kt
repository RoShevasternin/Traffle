package com.candybostony.bonceria.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.candybostony.bonceria.game.actors.ATmpGroup
import com.candybostony.bonceria.game.utils.Block
import com.candybostony.bonceria.game.utils.TIME_ANIM_SCREEN
import com.candybostony.bonceria.game.utils.actor.addActorWithConstraints
import com.candybostony.bonceria.game.utils.actor.addActors
import com.candybostony.bonceria.game.utils.actor.addAndFillActor
import com.candybostony.bonceria.game.utils.actor.animDelay
import com.candybostony.bonceria.game.utils.actor.animHide
import com.candybostony.bonceria.game.utils.actor.animShow
import com.candybostony.bonceria.game.utils.actor.setOnClickListener
import com.candybostony.bonceria.game.utils.advanced.AdvancedScreen
import com.candybostony.bonceria.game.utils.gdxGame

var GDX_isWin = true

class ResultScreen: AdvancedScreen() {

    private val aPanelGroup = ATmpGroup(this)
    private val aPanelImg   = Image(if (GDX_isWin) gdxGame.assetsAll.WIN else gdxGame.assetsAll.LOSE)

    override fun show() {
        gdxGame.soundUtil.apply { play(if (GDX_isWin) win else lose) }
        setBackBackground(gdxGame.assetsAll.BACKGROUND_DEF)
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
        aPanelGroup.setSize(972f, 811f)
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

        aMen.setBounds(292f, 102f, 387f, 162f)
        aNext.setBounds(292f, 289f, 387f, 162f)

        aMen.setOnClickListener { this@ResultScreen.animHideScreen { gdxGame.navigationManager.back() } }
        aNext.setOnClickListener { this@ResultScreen.animHideScreen { gdxGame.navigationManager.navigate(GameScreen::class.java.name) } }
    }

}