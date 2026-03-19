package com.quenloria615.beton.game.screens

import com.badlogic.gdx.scenes.scene2d.Group
import com.quenloria615.beton.game.actors.ATmpGroup
import com.quenloria615.beton.game.actors.button.AButton
import com.quenloria615.beton.game.actors.checkbox.ACheckBox
import com.quenloria615.beton.game.actors.panel.APanelCoin
import com.quenloria615.beton.game.actors.panel.APanelDailyBonus
import com.quenloria615.beton.game.actors.panel.APanelDailyBonusWin
import com.quenloria615.beton.game.actors.panel.APanelMenu
import com.quenloria615.beton.game.actors.roulette.APanelRoulette
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
import com.quenloria615.beton.util.log

class MenuScreen: AdvancedScreen() {

    private val aPanelSett = ATmpGroup(this)
    private val aPlayBtn   = AButton(this, AButton.Type.PLAY)
    private val aShopBtn   = AButton(this, AButton.Type.SHOP)

    private val aPanelCoin = APanelCoin(this)
    private val aPanelMenu = APanelMenu(this)

    private val aPanelDailyBonus = APanelDailyBonus(this)

    private val aPanelRoulette      = APanelRoulette(this)
    private val aPanelDailyBonusWin = APanelDailyBonusWin(this)

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
        addShopBtn()

        addPanelCoin()
        addPanelMenu()
        addPlayBtn()

        addPanelDailyBonus()

        addPanelRoulette()
        addPanelDailyBonusWin()

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


        val aNotifyBox = ACheckBox(this@MenuScreen, ACheckBox.Type.NOTIFICATION)
        val aSettBtn   = AButton(this@MenuScreen, AButton.Type.SETT)
        aPanelSett.addActors(aNotifyBox, aSettBtn)
        aNotifyBox.setBounds(0f, 0f, 119f, 119f)
        aSettBtn.setBounds(155f, 0f, 119f, 119f)

        if (!IS_NOTIFICATION) aNotifyBox.check()

        aNotifyBox.setOnCheckListener { IS_NOTIFICATION = !it }
        aSettBtn.setOnClickListener { animHideScreen { gdxGame.navigationManager.navigate(SettingsScreen::class.java.name, MenuScreen::class.java.name) } }

    }

    private fun Group.addPlayBtn() {
        aPlayBtn.setSize(656f, 258f)
        addActorAligned(aPlayBtn, AlignH.RIGHT, AlignV.BOTTOM)
        aPlayBtn.setOnClickListener {
            when(APanelMenu.SELECTED_INDEX) {
                0 -> {
                    aPanelRoulette.setState(APanelRoulette.State.SHOW)
                }
                1 -> {
                    animHideScreen { gdxGame.navigationManager.navigate(GameChampionsScreen::class.java.name, MenuScreen::class.java.name) }
                }
                2 -> {
                    animHideScreen { gdxGame.navigationManager.navigate(GameAdventuresScreen::class.java.name, MenuScreen::class.java.name) }
                }
                else -> {
                    log("НЕВІДОМО")
                }
            }

        }
    }

    private fun Group.addShopBtn() {
        aShopBtn.setSize(539f, 182f)
        addActorAligned(aShopBtn, AlignH.CENTER, AlignV.TOP)
        aShopBtn.y -= 28f
        aShopBtn.setOnClickListener { animHideScreen { gdxGame.navigationManager.navigate(ShopScreen::class.java.name, MenuScreen::class.java.name) } }
    }

    private fun Group.addPanelCoin() {
        aPanelCoin.disable()
        aPanelCoin.setSize(565f, 193f)
        addActorWithConstraints(aPanelCoin) {
            startToStartOf = this@addPanelCoin
            topToTopOf     = this@addPanelCoin
            marginStart    = 40f
            marginTop      = 36f
        }
    }

    private fun Group.addPanelMenu() {
        aPanelMenu.setSize(1986f, 679f)
        addActorWithConstraints(aPanelMenu) {
            startToStartOf = this@addPanelMenu
            endToEndOf     = this@addPanelMenu
            topToBottomOf  = aShopBtn
            marginTop      = 26f
        }
    }

    private fun Group.addPanelDailyBonus() {
        aPanelDailyBonus.disable()
        aPanelDailyBonus.setSize(782f, 497f)
        addActorAligned(aPanelDailyBonus, AlignH.LEFT, AlignV.BOTTOM)

        aPanelDailyBonus.blockBonusAvailable = {
            aPanelDailyBonusWin.animShowBonus()
        }
    }


    private fun Group.addPanelRoulette() {
        aPanelRoulette.apply {
            color.a = 0f
            disable()
        }
        addAndFillActor(aPanelRoulette)
    }

    private fun Group.addPanelDailyBonusWin() {
        aPanelDailyBonusWin.apply {
            color.a = 0f
            disable()
        }
        addAndFillActor(aPanelDailyBonusWin)

        aPanelDailyBonusWin.blockGain = {
            gdxGame.modelPlayer.claimDailyBonus(600)
            aPanelDailyBonusWin.animHideBonus()
        }
        aPanelDailyBonusWin.blockX = {
            aPanelDailyBonusWin.animHideBonus()
        }
    }


}