package com.fishfestival.bubbleparty.game.screens

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.fishfestival.bubbleparty.game.actors.ATmpGroup
import com.fishfestival.bubbleparty.game.actors.button.AButton
import com.fishfestival.bubbleparty.game.utils.Block
import com.fishfestival.bubbleparty.game.utils.TIME_ANIM_SCREEN
import com.fishfestival.bubbleparty.game.utils.actor.HAlign
import com.fishfestival.bubbleparty.game.utils.actor.VAlign
import com.fishfestival.bubbleparty.game.utils.actor.addActorAligned
import com.fishfestival.bubbleparty.game.utils.actor.addActorWithConstraints
import com.fishfestival.bubbleparty.game.utils.actor.addAndFillActor
import com.fishfestival.bubbleparty.game.utils.actor.animDelay
import com.fishfestival.bubbleparty.game.utils.actor.animHide
import com.fishfestival.bubbleparty.game.utils.actor.animShow
import com.fishfestival.bubbleparty.game.utils.advanced.AdvancedScreen
import com.fishfestival.bubbleparty.game.utils.gdxGame

class RulesScreen: AdvancedScreen() {

    private val aHomeBtn    = AButton(this, AButton.Type.BACK)
    private val aPanelGroup = ATmpGroup(this)

    override fun show() {
        setBackBackground(gdxGame.assetsAll.B_BLUR)
        super.show()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        addPanelGroup()
        addBtnMenu()

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

    private fun Group.addPanelGroup() {
        aPanelGroup.setSize(916f, 1179f)
        addActorAligned(aPanelGroup, HAlign.CENTER, VAlign.CENTER)

        aPanelGroup.apply {
            addAndFillActor(Image(gdxGame.assetsAll.RULES))
        }
    }

    private fun Group.addBtnMenu() {
        aHomeBtn.setSize(118f, 118f)
        addActorWithConstraints(aHomeBtn) {
            startToStartOf = this@addBtnMenu
            topToTopOf     = this@addBtnMenu

            marginStart = 52f
            marginTop   = 59f
        }
        aHomeBtn.setOnClickListener { animHideScreen { gdxGame.navigationManager.back() } }
    }

}