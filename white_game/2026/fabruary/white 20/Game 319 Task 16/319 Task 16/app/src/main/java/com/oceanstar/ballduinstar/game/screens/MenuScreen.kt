package com.oceanstar.ballduinstar.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.oceanstar.ballduinstar.game.actors.ATmpGroup
import com.oceanstar.ballduinstar.game.actors.checkbox.ACheckBox
import com.oceanstar.ballduinstar.game.utils.Block
import com.oceanstar.ballduinstar.game.utils.HEIGHT_UI
import com.oceanstar.ballduinstar.game.utils.TIME_ANIM_SCREEN
import com.oceanstar.ballduinstar.game.utils.WIDTH_UI
import com.oceanstar.ballduinstar.game.utils.actor.HAlign
import com.oceanstar.ballduinstar.game.utils.actor.VAlign
import com.oceanstar.ballduinstar.game.utils.actor.addActorAligned
import com.oceanstar.ballduinstar.game.utils.actor.addActors
import com.oceanstar.ballduinstar.game.utils.actor.animDelay
import com.oceanstar.ballduinstar.game.utils.actor.animHide
import com.oceanstar.ballduinstar.game.utils.actor.animShow
import com.oceanstar.ballduinstar.game.utils.actor.setBounds
import com.oceanstar.ballduinstar.game.utils.actor.setOnClickListener
import com.oceanstar.ballduinstar.game.utils.advanced.AdvancedScreen
import com.oceanstar.ballduinstar.game.utils.gdxGame

class MenuScreen: AdvancedScreen() {

    private val aPanelGroup = ATmpGroup(this)

    private val aBtnsImg  = Image(gdxGame.assetsAll.MENU_PAN)
    private val aMusicBox = ACheckBox(this, ACheckBox.Type.MUSIC)

    override fun show() {
        setBackBackground(gdxGame.assetsAll.B_DEF)
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
        aPanelGroup.setSize(WIDTH_UI, HEIGHT_UI)
        addActorAligned(aPanelGroup, HAlign.CENTER, VAlign.CENTER)

        aPanelGroup.apply {
            addBtnsImg()
            addMusic()
        }
    }

    private fun Group.addBtnsImg() {
        addActor(aBtnsImg)
        aBtnsImg.setBounds(153f, 527f, 774f, 994f)

        val aPlay  = Actor()
        val aRules = Actor()
        val aSett  = Actor()

        addActors(aPlay, aRules, aSett)
        aPlay.setBounds(287f, 887f, 503f, 503f)
        aRules.setBounds(199f, 527f, 206f, 206f)
        aSett.setBounds(437f, 527f, 206f, 206f)

        aPlay.setOnClickListener { this@MenuScreen.animHide { gdxGame.navigationManager.navigate(GameScreen::class.java.name, this@MenuScreen::class.java.name) } }
        aRules.setOnClickListener { this@MenuScreen.animHide { gdxGame.navigationManager.navigate(RulesScreen::class.java.name, this@MenuScreen::class.java.name) } }
        aSett.setOnClickListener { this@MenuScreen.animHide { gdxGame.navigationManager.navigate(SettScreen::class.java.name, this@MenuScreen::class.java.name) } }
    }

    private fun Group.addMusic() {
        addActor(aMusicBox)
        aMusicBox.setBounds(675f, 527f, 206f, 206f)

        if (gdxGame.musicUtil.currentMusic?.isPlaying == false) aMusicBox.check()

        aMusicBox.setOnCheckListener { isCheck ->
            if (isCheck) gdxGame.musicUtil.currentMusic?.pause() else gdxGame.musicUtil.currentMusic?.play()
        }

    }

}