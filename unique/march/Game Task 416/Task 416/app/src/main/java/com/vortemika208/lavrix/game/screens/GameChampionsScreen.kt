package com.vortemika208.lavrix.game.screens

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.vortemika208.lavrix.game.actors.ATmpGroup
import com.vortemika208.lavrix.game.actors.button.AButton
import com.vortemika208.lavrix.game.actors.checkbox.ACheckBox
import com.vortemika208.lavrix.game.actors.panel.APanelCoin
import com.vortemika208.lavrix.game.actors.APanelStake
import com.vortemika208.lavrix.game.actors.slotGroup.ASlotGroup
import com.vortemika208.lavrix.game.utils.AlignH
import com.vortemika208.lavrix.game.utils.AlignV
import com.vortemika208.lavrix.game.utils.Block
import com.vortemika208.lavrix.game.utils.IS_NOTIFICATION
import com.vortemika208.lavrix.game.utils.TIME_ANIM_SCREEN
import com.vortemika208.lavrix.game.utils.actor.addActorAligned
import com.vortemika208.lavrix.game.utils.actor.addActorWithConstraints
import com.vortemika208.lavrix.game.utils.actor.addActors
import com.vortemika208.lavrix.game.utils.actor.animDelay
import com.vortemika208.lavrix.game.utils.actor.animHide
import com.vortemika208.lavrix.game.utils.actor.animShow
import com.vortemika208.lavrix.game.utils.advanced.AdvancedScreen
import com.vortemika208.lavrix.game.utils.gdxGame
import com.vortemika208.lavrix.game.utils.region
import kotlinx.coroutines.launch

class GameChampionsScreen : AdvancedScreen() {

    private val aPanelSett = ATmpGroup(this)
    private val aLobbyBtn  = AButton(this, AButton.Type.LOBBY)

    private val aSlotGroup = ASlotGroup(this, gdxGame.assetsAll.listSlotItems1.map { it.region })
    private val aSpinBtn   = AButton(this, AButton.Type.SPIN)

    private val aPanelCoin  = APanelCoin(this)
    private val aPanelStake = APanelStake(this)

    // ------------------------------------------------------------------------
    // Lifecycle
    // ------------------------------------------------------------------------
    override fun show() {
        setBackBackground(gdxGame.assetsAll.BACKGROUND_DEF)
        super.show()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        addPanelSett()
        addLobbyBtn()

        addSlotGroup()
        addSpinBtn()

        addPanelCoin()
        addPanelStake()

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

    private fun Group.addPanelSett() {
        aPanelSett.setSize(274f, 119f)
        addActorWithConstraints(aPanelSett) {
            endToEndOf     = this@addPanelSett
            topToTopOf     = this@addPanelSett
            marginEnd      = 119f
            marginTop      = 57f
        }

        val aNotifyBox = ACheckBox(this@GameChampionsScreen, ACheckBox.Type.NOTIFICATION)
        val aSettBtn   = AButton(this@GameChampionsScreen, AButton.Type.SETT)
        aPanelSett.addActors(aNotifyBox, aSettBtn)
        aNotifyBox.setBounds(0f, 0f, 119f, 119f)
        aSettBtn.setBounds(155f, 0f, 119f, 119f)

        if (!IS_NOTIFICATION) aNotifyBox.check()

        aNotifyBox.setOnCheckListener { IS_NOTIFICATION = !it }
        aSettBtn.setOnClickListener { animHideScreen { gdxGame.navigationManager.navigate(SettingsScreen::class.java.name, MenuScreen::class.java.name) } }
    }

    private fun Group.addLobbyBtn() {
        aLobbyBtn.setSize(252f, 118f)
        addActorWithConstraints(aLobbyBtn) {
            startToStartOf = this@addLobbyBtn
            topToTopOf     = this@addLobbyBtn
            marginStart    = 99f
            marginTop      = 57f
        }

        aLobbyBtn.setOnClickListener { animHideScreen { gdxGame.navigationManager.back() } }
    }

    private fun Group.addSlotGroup() {
        aSlotGroup.setSize(1121f, 790f)
        addActorAligned(aSlotGroup, AlignH.CENTER, AlignV.CENTER)
    }

    private fun Group.addSpinBtn() {
        aSpinBtn.setSize(372f, 372f)
        addActorWithConstraints(aSpinBtn) {
            startToEndOf     = aSlotGroup
            bottomToBottomOf = aSlotGroup
            marginStart      = -84f
            marginBottom     = -106f
        }
        aSpinBtn.setOnClickListener {
            coroutine?.launch {
                aSlotGroup.spin()
            }
        }
    }

    private fun Group.addPanelCoin() {
        aPanelCoin.setSize(575f, 146f)
        addActorWithConstraints(aPanelCoin) {
            startToStartOf = aSlotGroup
            endToEndOf     = aSlotGroup
            bottomToTopOf  = aSlotGroup
            marginBottom   = -60f
        }
    }

    private fun Group.addPanelStake() {
        aPanelStake.setSize(288f, 637f)
        addActorWithConstraints(aPanelStake) {
            endToStartOf     = aSlotGroup
            bottomToBottomOf = aSlotGroup
            marginEnd        = 22f
            marginBottom     = 93f
        }
    }

}