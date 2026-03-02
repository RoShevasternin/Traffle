package com.monkeystreet.roadracejungle.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.monkeystreet.roadracejungle.game.actors.ATmpGroup
import com.monkeystreet.roadracejungle.game.utils.Block
import com.monkeystreet.roadracejungle.game.utils.HEIGHT_UI
import com.monkeystreet.roadracejungle.game.utils.TIME_ANIM_SCREEN
import com.monkeystreet.roadracejungle.game.utils.WIDTH_UI
import com.monkeystreet.roadracejungle.game.utils.actor.HAlign
import com.monkeystreet.roadracejungle.game.utils.actor.VAlign
import com.monkeystreet.roadracejungle.game.utils.actor.addActorAligned
import com.monkeystreet.roadracejungle.game.utils.actor.animDelay
import com.monkeystreet.roadracejungle.game.utils.actor.animHide
import com.monkeystreet.roadracejungle.game.utils.actor.animShow
import com.monkeystreet.roadracejungle.game.utils.actor.setOnClickListener
import com.monkeystreet.roadracejungle.game.utils.advanced.AdvancedScreen
import com.monkeystreet.roadracejungle.game.utils.gdxGame

class MenuScreen: AdvancedScreen() {

    private val aPanelGroup = ATmpGroup(this)
    private val aBtnsImg    = Image(gdxGame.assetsAll.BTNS)

    override fun show() {
        setBackBackground(gdxGame.assetsAll.BACKGROUND)
        super.show()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        addPanelGroup()

        animShow()
    }

    override fun animHide(blockEnd: Block) {
        stageUI.root.animHide(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    override fun animShow(blockEnd: Block) {
        //stageUI.root.children.onEach { it.clearActions() }

        stageUI.root.animShow(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    // Actors ------------------------------------------------------------------------

    private fun Group.addPanelGroup() {
        aPanelGroup.setSize(WIDTH_UI, HEIGHT_UI)
        addActorAligned(aPanelGroup, HAlign.CENTER, VAlign.TOP)

        aPanelGroup.apply {
            addBtns()
        }
    }

    private fun Group.addBtns() {
        addActor(aBtnsImg)
        aBtnsImg.setBounds(194f, 308f, 692f, 1150f)

        repeat(4) { index ->
            addActor(Actor().apply {
                when(index) {
                    0 -> {
                        setBounds(194f, 1074f, 692f, 265f)
                        setOnClickListener { this@MenuScreen.animHide { gdxGame.navigationManager.navigate(GameScreen::class.java.name, MenuScreen::class.java.name) } }
                    }
                    1 -> {
                        setBounds(261f, 837f, 558f, 213f)
                        setOnClickListener { this@MenuScreen.animHide { gdxGame.navigationManager.navigate(RulesScreen::class.java.name, MenuScreen::class.java.name) } }
                    }
                    2 -> {
                        setBounds(261f, 600f, 558f, 213f)
                        setOnClickListener { this@MenuScreen.animHide { gdxGame.navigationManager.navigate(LeaderboardScreen::class.java.name, MenuScreen::class.java.name) } }
                    }
                    3 -> {
                        setBounds(343f, 426f, 394f, 150f)
                        setOnClickListener { gdxGame.navigationManager.exit() }
                    }
                }
            })
        }
    }

}