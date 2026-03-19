package com.quenloria615.beton.game.screens

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.quenloria615.beton.game.actors.ATmpGroup
import com.quenloria615.beton.game.actors.button.AButton
import com.quenloria615.beton.game.actors.checkbox.ACheckBox
import com.quenloria615.beton.game.actors.panel.APanelCoin
import com.quenloria615.beton.game.actors.APanelStake
import com.quenloria615.beton.game.actors.slotGroup.AResultChampions
import com.quenloria615.beton.game.actors.slotGroup.ASlotGroup
import com.quenloria615.beton.game.utils.AlignH
import com.quenloria615.beton.game.utils.AlignV
import com.quenloria615.beton.game.utils.Block
import com.quenloria615.beton.game.utils.IS_NOTIFICATION
import com.quenloria615.beton.game.utils.TIME_ANIM_SCREEN
import com.quenloria615.beton.game.utils.actor.addActorAligned
import com.quenloria615.beton.game.utils.actor.addActorWithConstraints
import com.quenloria615.beton.game.utils.actor.addActors
import com.quenloria615.beton.game.utils.actor.addAndFillActor
import com.quenloria615.beton.game.utils.actor.animDelay
import com.quenloria615.beton.game.utils.actor.animHide
import com.quenloria615.beton.game.utils.actor.animShow
import com.quenloria615.beton.game.utils.actor.disable
import com.quenloria615.beton.game.utils.advanced.AdvancedScreen
import com.quenloria615.beton.game.utils.gdxGame
import com.quenloria615.beton.game.utils.region
import kotlinx.coroutines.launch

class GameChampionsScreen : AdvancedScreen() {

    private val aPanelSett = ATmpGroup(this)
    private val aLobbyBtn  = AButton(this, AButton.Type.LOBBY)

    private val aSlotGroup = ASlotGroup(this, gdxGame.assetsAll.listSlotItems1.map { it.region })
    private val aSpinBtn   = AButton(this, AButton.Type.SPIN)

    private val aPanelCoin  = APanelCoin(this)
    private val aPanelStake = APanelStake(this)

    private val aResultChampions = AResultChampions(this)

    // ------------------------------------------------------------------------
    // Lifecycle
    // ------------------------------------------------------------------------
    override fun show() {
        setBackBackground(gdxGame.assetsAll.BACKGROUND_CHAMPIONS)
        super.show()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        addPanelSett()
        addLobbyBtn()

        addSlotGroup()
        addSpinBtn()

        addPanelStake()

        addResultChampions()
        addPanelCoin()

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
                val stake = aPanelStake.currentStake
                if (gdxGame.modelPlayer.spendCoin(stake)) {
                    aSpinBtn.disable()
                    val isWin = aSlotGroup.spin()
                    aSpinBtn.enable()

                    if (isWin) {
                        val coinWin = stake * (2..5).random()
                        gdxGame.modelPlayer.addCoin(coinWin)

                        AResultChampions.WIN_COIN_FLOW.value = coinWin
                        aResultChampions.animShowResultChampions()
                    }
                }
            }
        }
    }

    private fun Group.addResultChampions() {
        aResultChampions.apply {
            color.a = 0f
            disable()
        }
        addAndFillActor(aResultChampions)

        aResultChampions.blockGain = {
            aResultChampions.animHideResultChampions()
        }
    }

    private fun Group.addPanelCoin() {
        aPanelCoin.setSize(565f, 193f)
        addActorWithConstraints(aPanelCoin) {
            startToStartOf = aSlotGroup
            endToEndOf     = aSlotGroup
            bottomToTopOf  = aSlotGroup
            marginBottom   = -109f
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