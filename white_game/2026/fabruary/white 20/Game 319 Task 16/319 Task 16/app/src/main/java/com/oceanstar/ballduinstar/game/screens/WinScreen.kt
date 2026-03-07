package com.oceanstar.ballduinstar.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.oceanstar.ballduinstar.game.actors.ATmpGroup
import com.oceanstar.ballduinstar.game.utils.Block
import com.oceanstar.ballduinstar.game.utils.HEIGHT_UI
import com.oceanstar.ballduinstar.game.utils.TIME_ANIM_SCREEN
import com.oceanstar.ballduinstar.game.utils.WIDTH_UI
import com.oceanstar.ballduinstar.game.utils.actor.HAlign
import com.oceanstar.ballduinstar.game.utils.actor.VAlign
import com.oceanstar.ballduinstar.game.utils.actor.addActorAligned
import com.oceanstar.ballduinstar.game.utils.actor.addActors
import com.oceanstar.ballduinstar.game.utils.actor.addAndFillActor
import com.oceanstar.ballduinstar.game.utils.actor.animDelay
import com.oceanstar.ballduinstar.game.utils.actor.animHide
import com.oceanstar.ballduinstar.game.utils.actor.animShow
import com.oceanstar.ballduinstar.game.utils.actor.setOnClickListener
import com.oceanstar.ballduinstar.game.utils.advanced.AdvancedScreen
import com.oceanstar.ballduinstar.game.utils.gdxGame

class WinScreen: AdvancedScreen() {

    private val aPanelGroup = ATmpGroup(this)
    private val aResultImg   = Image(gdxGame.assetsAll.RESULT_PAN)

    override fun show() {
        gdxGame.soundUtil.apply { play(win) }

        setBackBackground(gdxGame.assetsAll.B_WIN)
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
        stageUI.root.animShow(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    // Actors ------------------------------------------------------------------------

    private fun Group.addPanelGroup() {
        aPanelGroup.setSize(736f, 285f)
        addActorAligned(aPanelGroup, HAlign.CENTER, VAlign.BOTTOM)
        aPanelGroup.y = 600f

        aPanelGroup.apply {
            addAndFillActor(aResultImg)
            addBtnMenu()
        }
    }

    private fun Group.addBtnMenu() {
        val aRestart = Actor()
        val aMenu    = Actor()
        addActors(aRestart, aMenu)
        aRestart.setBounds(380f, 40f, 206f, 206f)
        aMenu.setBounds(150f, 40f, 206f, 206f)
        aRestart.setOnClickListener { this@WinScreen.animHide { gdxGame.navigationManager.navigate(GameScreen::class.java.name) } }
        aMenu.setOnClickListener { this@WinScreen.animHide { gdxGame.navigationManager.navigate(MenuScreen::class.java.name) } }
    }

}