package com.jellymp.jumpem.game.screens

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.jellymp.jumpem.game.actors.ATmpGroup
import com.jellymp.jumpem.game.actors.button.AButton
import com.jellymp.jumpem.game.actors.checkbox.ACheckBox
import com.jellymp.jumpem.game.utils.Block
import com.jellymp.jumpem.game.utils.HEIGHT_UI
import com.jellymp.jumpem.game.utils.TIME_ANIM_SCREEN
import com.jellymp.jumpem.game.utils.WIDTH_UI
import com.jellymp.jumpem.game.utils.actor.HAlign
import com.jellymp.jumpem.game.utils.actor.VAlign
import com.jellymp.jumpem.game.utils.actor.addActorAligned
import com.jellymp.jumpem.game.utils.actor.addActorWithConstraints
import com.jellymp.jumpem.game.utils.actor.animDelay
import com.jellymp.jumpem.game.utils.actor.animHide
import com.jellymp.jumpem.game.utils.actor.animShow
import com.jellymp.jumpem.game.utils.advanced.AdvancedScreen
import com.jellymp.jumpem.game.utils.gdxGame
import com.jellymp.jumpem.game.utils.actor.addAndFillActor

class SettingsScreen: AdvancedScreen() {

    private val aHomeBtn = AButton(this, AButton.Type.Home)
    private val aPanelGroup = ATmpGroup(this)

    override fun show() {
        setBackBackground(gdxGame.assetsAll.BACKGROUND_DEF)
        super.show()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        addPanelGroup()
        addBtnMenu()

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
        addActorAligned(aPanelGroup, HAlign.CENTER, VAlign.CENTER)

        aPanelGroup.apply {
            addAndFillActor(Image(gdxGame.assetsAll.SETT))
            addSoundMusic()
        }
    }

    private fun Group.addSoundMusic() {
        // sound
        val aSoundBox = ACheckBox(this@SettingsScreen, ACheckBox.Type.OFF_ON)
        addActor(aSoundBox)
        aSoundBox.setBounds(229f, 281f, 290f, 129f)
        if (gdxGame.soundUtil.isPause.not()) aSoundBox.check()
        aSoundBox.setOnCheckListener { isCheck ->
            gdxGame.soundUtil.isPause = isCheck.not()
        }

        // music
        val aMusicBox = ACheckBox(this@SettingsScreen, ACheckBox.Type.OFF_ON)
        addActor(aMusicBox)
        aMusicBox.setBounds(730f, 281f, 290f, 129f)
        if (gdxGame.musicUtil.currentMusic?.isPlaying == true) aMusicBox.check()
        aMusicBox.setOnCheckListener { isCheck ->
            if (isCheck) gdxGame.musicUtil.currentMusic?.play()
            else gdxGame.musicUtil.currentMusic?.pause()
        }
    }

    private fun Group.addBtnMenu() {
        aHomeBtn.setSize(160f, 160f)
        addActorWithConstraints(aHomeBtn) {
            startToStartOf = this@addBtnMenu
            topToTopOf     = this@addBtnMenu

            marginStart = 64f
            marginTop   = 27f
        }
        aHomeBtn.setOnClickListener { animHideScreen { gdxGame.navigationManager.back() } }
    }

}