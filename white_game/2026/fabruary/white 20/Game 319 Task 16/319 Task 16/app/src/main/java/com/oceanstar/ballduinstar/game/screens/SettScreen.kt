package com.oceanstar.ballduinstar.game.screens

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.oceanstar.ballduinstar.game.actors.ATmpGroup
import com.oceanstar.ballduinstar.game.actors.button.AButton
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
import com.oceanstar.ballduinstar.game.utils.advanced.AdvancedScreen
import com.oceanstar.ballduinstar.game.utils.gdxGame

class SettScreen: AdvancedScreen() {

    private val aPanelGroup = ATmpGroup(this)

    private val aRulesImg  = Image(gdxGame.assetsAll.SETTINGS)
    private val aMenuBtn   = AButton(this, AButton.Type.Menu)

    override fun show() {
        setBackBackground(gdxGame.assetsAll.B_BLUR)
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
            addRulesImg()
            addBtnMenu()
            addSoundMusic()
        }
    }

    private fun Group.addRulesImg() {
        addActor(aRulesImg)
        aRulesImg.setBounds(87f, 475f, 906f, 969f)
    }

    private fun Group.addBtnMenu() {
        addActor(aMenuBtn)
        aMenuBtn.setBounds(25f, 1681f, 206f, 206f)
        aMenuBtn.setOnClickListener { this@SettScreen.animHide { gdxGame.navigationManager.back() } }
    }

    private fun Group.addSoundMusic() {
        val aSoundBox = ACheckBox(this@SettScreen, ACheckBox.Type.ON_OFF)
        val aMusicBox = ACheckBox(this@SettScreen, ACheckBox.Type.ON_OFF)
        addActors(aSoundBox, aMusicBox)
        aSoundBox.setBounds(568f, 798f, 227f, 112f)
        aMusicBox.setBounds(568f, 950f, 227f, 112f)

        if (gdxGame.soundUtil.isPause) aSoundBox.check()
        if (gdxGame.musicUtil.currentMusic?.isPlaying == false) aMusicBox.check()

        aSoundBox.setOnCheckListener { isCheck ->
            gdxGame.soundUtil.isPause = isCheck
        }
        aMusicBox.setOnCheckListener { isCheck ->
            if (isCheck) gdxGame.musicUtil.currentMusic?.pause() else gdxGame.musicUtil.currentMusic?.play()
        }

    }

}