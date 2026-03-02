package com.babun.flutterdash.game.screens

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.babun.flutterdash.game.actors.ATmpGroup
import com.babun.flutterdash.game.actors.AVolume
import com.babun.flutterdash.game.actors.button.AButton
import com.babun.flutterdash.game.utils.Block
import com.babun.flutterdash.game.utils.HEIGHT_UI
import com.babun.flutterdash.game.utils.TIME_ANIM_SCREEN
import com.babun.flutterdash.game.utils.WIDTH_UI
import com.babun.flutterdash.game.utils.actor.HAlign
import com.babun.flutterdash.game.utils.actor.VAlign
import com.babun.flutterdash.game.utils.actor.addActorAligned
import com.babun.flutterdash.game.utils.actor.addActorWithConstraints
import com.babun.flutterdash.game.utils.actor.addActors
import com.babun.flutterdash.game.utils.actor.animDelay
import com.babun.flutterdash.game.utils.actor.animHide
import com.babun.flutterdash.game.utils.actor.animShow
import com.babun.flutterdash.game.utils.actor.setBounds
import com.babun.flutterdash.game.utils.actor.setOnClickListener
import com.babun.flutterdash.game.utils.advanced.AdvancedScreen
import com.babun.flutterdash.game.utils.gdxGame
import com.badlogic.gdx.scenes.scene2d.Actor

class SettingsScreen: AdvancedScreen() {

    private val aMenuBtn   = AButton(this, AButton.Type.Back)

    private val aPanelGroup = ATmpGroup(this)
    private val aRulesImg   = Image(gdxGame.assetsAll.SETTINGS)

    private val aMusicVolume = AVolume(this)
    private val aSoundVolume = AVolume(this)

    override fun show() {
        setBackBackground(gdxGame.assetsLoader.BACKGROUND)
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
        addActorAligned(aPanelGroup, HAlign.CENTER, VAlign.TOP)

        aPanelGroup.apply {
            addRulesImg()
            addMusSou()
        }
    }

    private fun Group.addRulesImg() {
        addActor(aRulesImg)
        aRulesImg.setBounds(652f, 86f, 696f, 909f)
    }

    private fun Group.addMusSou() {
        // music
        addActor(aMusicVolume)
        aMusicVolume.setBounds(832f, 581f, 312f, 64f)

        val aMinusMusic = Actor()
        val aPlusMusic  = Actor()
        addActors(aMinusMusic, aPlusMusic)
        aMinusMusic.setBounds(725f, 570f, 79f, 85f)
        aPlusMusic.setBounds(1172f, 570f, 79f, 85f)
        aMinusMusic.setOnClickListener { 
            aMusicVolume.decrease()
            gdxGame.musicUtil.volumeLevelFlow.value = aMusicVolume.getPercent().toFloat()
        }
        aPlusMusic.setOnClickListener { 
            aMusicVolume.increase()
            gdxGame.musicUtil.volumeLevelFlow.value = aMusicVolume.getPercent().toFloat()
        }
        aMusicVolume.setPercent(gdxGame.musicUtil.volumeLevelFlow.value.toInt())

        // sound
        addActor(aSoundVolume)
        aSoundVolume.setBounds(832f, 365f, 312f, 64f)

        val aMinusSound = Actor()
        val aPlusSound  = Actor()
        addActors(aMinusSound, aPlusSound)
        aMinusSound.setBounds(725f, 354f, 79f, 85f)
        aPlusSound.setBounds(1172f, 354f, 79f, 85f)
        aMinusSound.setOnClickListener {
            aSoundVolume.decrease()
            gdxGame.soundUtil.volumeLevel = aSoundVolume.getPercent().toFloat()
        }
        aPlusSound.setOnClickListener {
            aSoundVolume.increase()
            gdxGame.soundUtil.volumeLevel = aSoundVolume.getPercent().toFloat()
        }
        aSoundVolume.setPercent(gdxGame.soundUtil.volumeLevel.toInt())
    }

    private fun Group.addBtnMenu() {
        aMenuBtn.setSize(130f, 130f)
        addActorWithConstraints(aMenuBtn) {
            startToStartOf = this@addBtnMenu
            topToTopOf     = this@addBtnMenu

            marginStart = 142f
            marginTop   = 55f
        }
        aMenuBtn.setOnClickListener { this@SettingsScreen.animHide { gdxGame.navigationManager.back() } }
    }

}