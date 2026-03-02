package com.jellymp.jumpem.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.jellymp.jumpem.game.actors.ATmpGroup
import com.jellymp.jumpem.game.utils.Block
import com.jellymp.jumpem.game.utils.HEIGHT_UI
import com.jellymp.jumpem.game.utils.TIME_ANIM_SCREEN
import com.jellymp.jumpem.game.utils.WIDTH_UI
import com.jellymp.jumpem.game.utils.actor.HAlign
import com.jellymp.jumpem.game.utils.actor.VAlign
import com.jellymp.jumpem.game.utils.actor.addActorAligned
import com.jellymp.jumpem.game.utils.actor.addActors
import com.jellymp.jumpem.game.utils.actor.addAndFillActor
import com.jellymp.jumpem.game.utils.actor.animDelay
import com.jellymp.jumpem.game.utils.actor.animHide
import com.jellymp.jumpem.game.utils.actor.animShow
import com.jellymp.jumpem.game.utils.actor.setBounds
import com.jellymp.jumpem.game.utils.actor.setOnClickListener
import com.jellymp.jumpem.game.utils.advanced.AdvancedScreen
import com.jellymp.jumpem.game.utils.gdxGame

class MenuScreen: AdvancedScreen() {

    private val aPanelGroup = ATmpGroup(this)

    override fun show() {
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
        aPanelGroup.setSize(1040f, 465f)
        addActorAligned(aPanelGroup, HAlign.CENTER, VAlign.CENTER)

        aPanelGroup.apply {
            addAndFillActor(Image(gdxGame.assetsAll.BTNS))
            addBtns()
        }
    }

    private fun Group.addBtns() {
        val aSettings = Actor()
        val aPlay     = Actor()
        val aRules    = Actor()
        addActors(aSettings, aPlay, aRules)
        aSettings.setBounds(0f, 0f, 292f, 317f)
        aPlay.setBounds(306f, 0f, 427f, 464f)
        aRules.setBounds(747f, 0f, 292f, 317f)

        aSettings.setOnClickListener { animHideScreen { gdxGame.navigationManager.navigate(SettingsScreen::class.java.name, MenuScreen::class.java.name) } }
        aPlay.setOnClickListener { animHideScreen { gdxGame.navigationManager.navigate(GameScreen::class.java.name, MenuScreen::class.java.name) } }
        aRules.setOnClickListener { animHideScreen { gdxGame.navigationManager.navigate(RulesScreen::class.java.name, MenuScreen::class.java.name) } }
    }

}