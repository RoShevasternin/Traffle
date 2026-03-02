package com.fruiterra.maniachello.game.screens

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.fruiterra.maniachello.game.actors.ATmpGroup
import com.fruiterra.maniachello.game.actors.button.AButton
import com.fruiterra.maniachello.game.utils.Block
import com.fruiterra.maniachello.game.utils.HEIGHT_UI
import com.fruiterra.maniachello.game.utils.TIME_ANIM_SCREEN
import com.fruiterra.maniachello.game.utils.WIDTH_UI
import com.fruiterra.maniachello.game.utils.actor.HAlign
import com.fruiterra.maniachello.game.utils.actor.VAlign
import com.fruiterra.maniachello.game.utils.actor.addActorAligned
import com.fruiterra.maniachello.game.utils.actor.addActorWithConstraints
import com.fruiterra.maniachello.game.utils.actor.animDelay
import com.fruiterra.maniachello.game.utils.actor.animHide
import com.fruiterra.maniachello.game.utils.actor.animShow
import com.fruiterra.maniachello.game.utils.advanced.AdvancedScreen
import com.fruiterra.maniachello.game.utils.gdxGame
import com.badlogic.gdx.scenes.scene2d.Actor
import com.fruiterra.maniachello.game.actors.AProgress
import kotlinx.coroutines.launch

class SettingsScreen: AdvancedScreen() {

    private val aMenuBtn   = AButton(this, AButton.Type.Back)

    private val aPanelGroup  = ATmpGroup(this)
    private val aSettingsImg = Image(gdxGame.assetsAll.SETT)

    private val aMusicVolume = AProgress(this)
    private val aSoundVolume = AProgress(this)

    override fun show() {
        setBackBackground(gdxGame.assetsAll.BACKGROUND)
        super.show()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        addPanelGroup()
        addBtnMenu()

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
        addActorAligned(aPanelGroup, HAlign.CENTER, VAlign.CENTER)

        aPanelGroup.apply {
            addSettingsImg()
            addMusSou()
        }
    }

    private fun Group.addSettingsImg() {
        addActor(aSettingsImg)
        aSettingsImg.setBounds(548f, 46f, 466f, 707f)
    }

    private fun Group.addMusSou() {
        // sound
        addActor(aSoundVolume)
        aSoundVolume.setBounds(645f, 512f, 270f, 70f)

        // music
        addActor(aMusicVolume)
        aMusicVolume.setBounds(645f, 284f, 270f, 70f)

        coroutine?.launch {
            launch {
                aSoundVolume.progressPercentFlow.collect {
                    gdxGame.soundUtil.volumeLevel = it
                }
            }
            launch {
                aMusicVolume.progressPercentFlow.collect {
                    gdxGame.musicUtil.volumeLevelFlow.value = it
                }
            }
        }
    }

    private fun Group.addBtnMenu() {
        aMenuBtn.setSize(87f, 87f)
        addActorWithConstraints(aMenuBtn) {
            startToStartOf = this@addBtnMenu
            topToTopOf     = this@addBtnMenu

            marginStart = 66f
            marginTop   = 53f
        }
        aMenuBtn.setOnClickListener { this@SettingsScreen.animHide { gdxGame.navigationManager.back() } }
    }

}