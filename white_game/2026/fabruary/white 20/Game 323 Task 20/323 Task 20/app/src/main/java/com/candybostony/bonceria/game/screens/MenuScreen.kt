package com.candybostony.bonceria.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.candybostony.bonceria.game.actors.ATmpGroup
import com.candybostony.bonceria.game.utils.Block
import com.candybostony.bonceria.game.utils.HEIGHT_UI
import com.candybostony.bonceria.game.utils.TIME_ANIM_SCREEN
import com.candybostony.bonceria.game.utils.WIDTH_UI
import com.candybostony.bonceria.game.utils.actor.HAlign
import com.candybostony.bonceria.game.utils.actor.VAlign
import com.candybostony.bonceria.game.utils.actor.addActorAligned
import com.candybostony.bonceria.game.utils.actor.addActors
import com.candybostony.bonceria.game.utils.actor.addAndFillActor
import com.candybostony.bonceria.game.utils.actor.animDelay
import com.candybostony.bonceria.game.utils.actor.animHide
import com.candybostony.bonceria.game.utils.actor.animShow
import com.candybostony.bonceria.game.utils.actor.setBounds
import com.candybostony.bonceria.game.utils.actor.setOnClickListener
import com.candybostony.bonceria.game.utils.advanced.AdvancedScreen
import com.candybostony.bonceria.game.utils.gdxGame

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
        aPanelGroup.setSize(679f, 828f)
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
        aSettings.setBounds(67f, 0f, 533f, 224f)
        aPlay.setBounds(0f, 269f, 668f, 281f)
        aRules.setBounds(67f, 577f, 533f, 224f)

        aSettings.setOnClickListener { animHideScreen { gdxGame.navigationManager.navigate(SettingsScreen::class.java.name, MenuScreen::class.java.name) } }
        aPlay.setOnClickListener { animHideScreen { gdxGame.navigationManager.navigate(GameScreen::class.java.name, MenuScreen::class.java.name) } }
        aRules.setOnClickListener { animHideScreen { gdxGame.navigationManager.navigate(RulesScreen::class.java.name, MenuScreen::class.java.name) } }
    }

}