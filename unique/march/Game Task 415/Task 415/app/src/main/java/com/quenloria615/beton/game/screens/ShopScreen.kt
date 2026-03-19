package com.quenloria615.beton.game.screens

import com.badlogic.gdx.scenes.scene2d.Group
import com.quenloria615.beton.game.actors.button.AButton
import com.quenloria615.beton.game.actors.panel.APanelCoin
import com.quenloria615.beton.game.actors.panel.APanelShop
import com.quenloria615.beton.game.utils.AlignH
import com.quenloria615.beton.game.utils.AlignV
import com.quenloria615.beton.game.utils.Block
import com.quenloria615.beton.game.utils.TIME_ANIM_SCREEN
import com.quenloria615.beton.game.utils.actor.addActorAligned
import com.quenloria615.beton.game.utils.actor.addActorWithConstraints
import com.quenloria615.beton.game.utils.actor.animDelay
import com.quenloria615.beton.game.utils.actor.animHide
import com.quenloria615.beton.game.utils.actor.animShow
import com.quenloria615.beton.game.utils.actor.disable
import com.quenloria615.beton.game.utils.advanced.AdvancedScreen
import com.quenloria615.beton.game.utils.gdxGame

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
        aPanelCoin.setSize(565f, 193f)
        addActorWithConstraints(aPanelCoin) {
            startToStartOf = this@addPanelCoin
            topToTopOf     = this@addPanelCoin
            marginStart    = 40f
            marginTop      = 36f
        }
    }

    private fun Group.addPanelShop() {
        aPanelShop.setSize(1603f, 658f)
        addActorAligned(aPanelShop, AlignH.CENTER, AlignV.CENTER)
        aPanelShop.y -= 50f
    }

}