package com.senqorvia774.lottomatica.game.screens

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.senqorvia774.lottomatica.game.actors.ATmpGroup
import com.senqorvia774.lottomatica.game.actors.button.AButton
import com.senqorvia774.lottomatica.game.actors.checkbox.ACheckBox
import com.senqorvia774.lottomatica.game.actors.progress.AProgress
import com.senqorvia774.lottomatica.game.utils.AlignH
import com.senqorvia774.lottomatica.game.utils.AlignV
import com.senqorvia774.lottomatica.game.utils.Block
import com.senqorvia774.lottomatica.game.utils.IS_NOTIFICATION
import com.senqorvia774.lottomatica.game.utils.IS_TURBO
import com.senqorvia774.lottomatica.game.utils.TIME_ANIM_SCREEN
import com.senqorvia774.lottomatica.game.utils.actor.addActorAligned
import com.senqorvia774.lottomatica.game.utils.actor.addActorWithConstraints
import com.senqorvia774.lottomatica.game.utils.actor.addActors
import com.senqorvia774.lottomatica.game.utils.actor.addAndFillActor
import com.senqorvia774.lottomatica.game.utils.actor.animDelay
import com.senqorvia774.lottomatica.game.utils.actor.animHide
import com.senqorvia774.lottomatica.game.utils.actor.animShow
import com.senqorvia774.lottomatica.game.utils.actor.disable
import com.senqorvia774.lottomatica.game.utils.actor.setBounds
import com.senqorvia774.lottomatica.game.utils.advanced.AdvancedScreen
import com.senqorvia774.lottomatica.game.utils.gdxGame
import kotlinx.coroutines.launch

class SettingsScreen: AdvancedScreen() {

    private val aSettingsImg = Image(gdxGame.assetsAll.PANEL_SETTINGS)
    private val aLobbyBtn    = AButton(this, AButton.Type.LOBBY)

    private val aPanelSettings    = ATmpGroup(this)
    private val aPanelSettingsImg = Image(gdxGame.assetsAll.SETTINGS_GROUP)
    private val aSoundProgress    = AProgress(this)
    private val aMusicProgress    = AProgress(this)
    private val aSoundBox         = ACheckBox(this, ACheckBox.Type.ON_OFF)
    private val aMusicBox         = ACheckBox(this, ACheckBox.Type.ON_OFF)
    private val aTurboBox         = ACheckBox(this, ACheckBox.Type.ON_OFF)
    private val aNotificationBox  = ACheckBox(this, ACheckBox.Type.ON_OFF)

    // ------------------------------------------------------------------------
    // Lifecycle
    // ------------------------------------------------------------------------
    override fun show() {
        setBackBackground(gdxGame.assetsAll.BACKGROUND_SETTINGS)
        super.show()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        addSettingsImg()
        addLobbyBtn()
        addPanelSettings()

        animShowScreen()
    }

    // ------------------------------------------------------------------------
    // Screen Animations
    // ------------------------------------------------------------------------
    override fun animHideScreen(blockEnd: Block) {
        stageUI.root.animHide(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animShowScreen(blockEnd: Block) {
        stageUI.root.animShow(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun Group.addLobbyBtn() {
        aLobbyBtn.setSize(252f, 118f)
        addActorAligned(aLobbyBtn, AlignH.RIGHT, AlignV.TOP)
        aLobbyBtn.x -= 120f
        aLobbyBtn.y -= 57f
        aLobbyBtn.setOnClickListener { animHideScreen { gdxGame.navigationManager.back() } }
    }

    private fun Group.addSettingsImg() {
        aSettingsImg.setSize(774f, 204f)
        addActorAligned(aSettingsImg, AlignH.CENTER, AlignV.TOP)
    }

    private fun Group.addPanelSettings() {
        aPanelSettings.setSize(1640f, 705f)
        addActorWithConstraints(aPanelSettings) {
            startToStartOf = this@addPanelSettings
            endToEndOf     = this@addPanelSettings
            topToBottomOf  = aSettingsImg
            marginTop      = 86f
        }

        aPanelSettings.apply {
            addAndFillActor(aPanelSettingsImg)
            addActors(
                aSoundProgress, aMusicProgress,
                aSoundBox, aMusicBox, aTurboBox, aNotificationBox,
            )
        }

        aSoundProgress.setBounds(1021f, 619f, 619f, 29f)
        aMusicProgress.setBounds(1021f, 424f, 619f, 29f)

        aSoundBox.setBounds(345f, 566f, 331f, 134f)
        aMusicBox.setBounds(429f, 371f, 331f, 134f)
        aTurboBox.setBounds(0f, 0f, 331f, 134f)
        aNotificationBox.setBounds(1021f, 0f, 331f, 134f)

        // INIT VALUE -------------------

        aSoundProgress.setProgressPercent(gdxGame.soundUtil.volumeLevel)
        aMusicProgress.setProgressPercent(gdxGame.musicUtil.volumeLevelFlow.value)

        coroutine?.launch {
            launch {
                aSoundProgress.progressPercentFlow.collect {
                    gdxGame.soundUtil.volumeLevel = it
                }
            }
            launch {
                aMusicProgress.progressPercentFlow.collect {
                    gdxGame.musicUtil.volumeLevelFlow.value = it
                }
            }
        }

        if (gdxGame.soundUtil.isPause) aSoundBox.check()
        if (gdxGame.musicUtil.currentMusic?.isPlaying == false) aMusicBox.check()
        if (!IS_TURBO) aTurboBox.check()
        if (!IS_NOTIFICATION) aNotificationBox.check()

        aSoundBox.setOnCheckListener { isCheck ->
            gdxGame.soundUtil.isPause = isCheck
        }
        aMusicBox.setOnCheckListener { isCheck ->
            if (isCheck) gdxGame.musicUtil.currentMusic?.pause() else gdxGame.musicUtil.currentMusic?.play()
        }
        aTurboBox.setOnCheckListener { isCheck ->
            IS_TURBO = !isCheck
        }
        aNotificationBox.setOnCheckListener { isCheck ->
            IS_NOTIFICATION = !isCheck
        }

    }

}