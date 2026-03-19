package com.senqorvia774.lottomatica.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.senqorvia774.lottomatica.game.actors.ATmpGroup
import com.senqorvia774.lottomatica.game.actors.button.AButton
import com.senqorvia774.lottomatica.game.actors.checkbox.ACheckBox
import com.senqorvia774.lottomatica.game.actors.panel.APanelCoin
import com.senqorvia774.lottomatica.game.actors.APanelStake
import com.senqorvia774.lottomatica.game.actors.slotGroup.AResultAdventures
import com.senqorvia774.lottomatica.game.actors.slotGroup.AResultChampions
import com.senqorvia774.lottomatica.game.actors.slotGroup.ASlotGroup
import com.senqorvia774.lottomatica.game.utils.AlignH
import com.senqorvia774.lottomatica.game.utils.AlignV
import com.senqorvia774.lottomatica.game.utils.Block
import com.senqorvia774.lottomatica.game.utils.IS_NOTIFICATION
import com.senqorvia774.lottomatica.game.utils.TIME_ANIM_SCREEN
import com.senqorvia774.lottomatica.game.utils.actor.addActorAligned
import com.senqorvia774.lottomatica.game.utils.actor.addActorWithConstraints
import com.senqorvia774.lottomatica.game.utils.actor.addActors
import com.senqorvia774.lottomatica.game.utils.actor.addAndFillActor
import com.senqorvia774.lottomatica.game.utils.actor.animDelay
import com.senqorvia774.lottomatica.game.utils.actor.animHide
import com.senqorvia774.lottomatica.game.utils.actor.animShow
import com.senqorvia774.lottomatica.game.utils.actor.disable
import com.senqorvia774.lottomatica.game.utils.advanced.AdvancedScreen
import com.senqorvia774.lottomatica.game.utils.gdxGame
import com.senqorvia774.lottomatica.game.utils.region
import kotlinx.coroutines.launch

class GameAdventuresScreen : AdvancedScreen() {

    private val aPanelSett = ATmpGroup(this)
    private val aLobbyBtn  = AButton(this, AButton.Type.LOBBY)

    private val aSlotGroup = ASlotGroup(this, gdxGame.assetsAll.listSlotItems2.map { it.region }, Interpolation.pow5)
    private val aSpinBtn   = AButton(this, AButton.Type.SPIN)

    private val aShіeldImg = Image(gdxGame.assetsAll.SHIELD)

    private val aPanelCoin  = APanelCoin(this)
    private val aPanelStake = APanelStake(this)

    private val aResultChampions = AResultAdventures(this)

    // ------------------------------------------------------------------------
    // Lifecycle
    // ------------------------------------------------------------------------
    override fun show() {
        setBackBackground(gdxGame.assetsAll.BACKGROUND_ADVENTURES)
        super.show()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        addPanelSett()
        addLobbyBtn()

        addSlotGroup()
        addSpinBtn()

        //addShieldImg()
        addPanelStake()

        addPanelCoin()
        addResultAdventures()

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

        val aNotifyBox = ACheckBox(this@GameAdventuresScreen, ACheckBox.Type.NOTIFICATION)
        val aSettBtn   = AButton(this@GameAdventuresScreen, AButton.Type.SETT)
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
                        val coinWin = stake * (3..7).random()
                        gdxGame.modelPlayer.addCoin(coinWin)

                        AResultAdventures.WIN_COIN_FLOW.value = coinWin
                        aResultChampions.animShowResultAdventures()
                    }
                }
            }
        }
    }

    private fun Group.addShieldImg() {
        aShіeldImg.setSize(664f, 842f)
        addActorWithConstraints(aShіeldImg) {
            endToStartOf   = aSlotGroup
            topToBottomOf  = aSlotGroup
            marginEnd      = -198f
            marginTop      = -342f
        }
    }

    private fun Group.addResultAdventures() {
        aResultChampions.apply {
            color.a = 0f
            disable()
        }
        addAndFillActor(aResultChampions)

        aResultChampions.blockGain = {
            aResultChampions.animHideResultAdventures()
        }
    }

    private fun Group.addPanelCoin() {
        aPanelCoin.setSize(539f, 140f)
        addActorWithConstraints(aPanelCoin) {
            startToStartOf = aSlotGroup
            endToEndOf     = aSlotGroup
            bottomToTopOf  = aSlotGroup
            marginBottom   = -65f
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