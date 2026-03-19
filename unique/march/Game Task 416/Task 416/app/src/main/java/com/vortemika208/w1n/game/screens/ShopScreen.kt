package com.vortemika208.w1n.game.screens

import com.badlogic.gdx.scenes.scene2d.Group
import com.vortemika208.w1n.game.actors.button.AButton
import com.vortemika208.w1n.game.actors.panel.APanelCoin
import com.vortemika208.w1n.game.actors.panel.APanelShop
import com.vortemika208.w1n.game.utils.AlignH
import com.vortemika208.w1n.game.utils.AlignV
import com.vortemika208.w1n.game.utils.Block
import com.vortemika208.w1n.game.utils.TIME_ANIM_SCREEN
import com.vortemika208.w1n.game.utils.actor.addActorAligned
import com.vortemika208.w1n.game.utils.actor.addActorWithConstraints
import com.vortemika208.w1n.game.utils.actor.animDelay
import com.vortemika208.w1n.game.utils.actor.animHide
import com.vortemika208.w1n.game.utils.actor.animShow
import com.vortemika208.w1n.game.utils.actor.disable
import com.vortemika208.w1n.game.utils.advanced.AdvancedScreen
import com.vortemika208.w1n.game.utils.gdxGame

class ShopScreen: AdvancedScreen() {

    private val aLobbyBtn  = AButton(this, AButton.Type.LOBBY)
    private val aShopBtn   = AButton(this, AButton.Type.SHOP)

    private val aPanelCoin = APanelCoin(this)
    private val aPanelShop = APanelShop(this)

    // ------------------------------------------------------------------------
    // Lifecycle
    // ------------------------------------------------------------------------
    override fun show() {
        setBackBackground(gdxGame.assetsAll.BACKGROUND_SHOP)
        super.show()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        addPanelShop()
        addShopBtn()
        addPanelCoin()
        addLobbyBtn()

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

    private fun Group.addShopBtn() {
        aShopBtn.disable(false)
        aShopBtn.setSize(539f, 182f)
        addActorAligned(aShopBtn, AlignH.CENTER, AlignV.TOP)
        aShopBtn.y -= 28f
    }

    private fun Group.addPanelCoin() {
        aPanelCoin.disable()
        aPanelCoin.setSize(575f, 146f)
        addActorWithConstraints(aPanelCoin) {
            startToStartOf = this@addPanelCoin
            topToTopOf     = this@addPanelCoin
            marginStart    = 40f
            marginTop      = 36f
        }
    }

    private fun Group.addPanelShop() {
        aPanelShop.setSize(1904f, 1140f)
        addActorAligned(aPanelShop, AlignH.CENTER, AlignV.TOP)
        aPanelShop.y -= 35f
    }

}