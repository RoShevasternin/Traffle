package com.quenloria615.beton.game.actors.roulette

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.quenloria615.beton.game.actors.ATmpGroup
import com.quenloria615.beton.game.utils.AlignH
import com.quenloria615.beton.game.utils.AlignV
import com.quenloria615.beton.game.utils.GameColor
import com.quenloria615.beton.game.utils.actor.addActorAligned
import com.quenloria615.beton.game.utils.actor.addActors
import com.quenloria615.beton.game.utils.actor.addAndFillActor
import com.quenloria615.beton.game.utils.actor.animHide
import com.quenloria615.beton.game.utils.actor.animShow
import com.quenloria615.beton.game.utils.actor.disable
import com.quenloria615.beton.game.utils.actor.enable
import com.quenloria615.beton.game.utils.actor.setOnClickListener
import com.quenloria615.beton.game.utils.advanced.AdvancedGroup
import com.quenloria615.beton.game.utils.advanced.AdvancedScreen
import com.quenloria615.beton.game.utils.gdxGame

class AResultTryAgain(override val screen: AdvancedScreen): AdvancedGroup() {

    private val aLeftImg  = Image(gdxGame.assetsAll.COIN_LEFT)
    private val aRightImg = Image(gdxGame.assetsAll.COIN_RIGHT)

    private val aPanelGroup  = ATmpGroup(screen)
    private val aPanelImg    = Image(gdxGame.assetsAll.PANEL_TRY_AGAIN)
    private val aTryAgainBtn = Actor()
    private val aXBtn        = Actor()

    var blockTryAgain = {}
    var blockClose    = {}

    override fun addActorsOnGroup() {
        addCoins()
        addPanelGroup()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun addCoins() {
        aLeftImg.setSize(981f, 777f)
        aRightImg.setSize(813f, 732f)
        addActorAligned(aLeftImg, AlignH.LEFT, AlignV.BOTTOM)
        addActorAligned(aRightImg, AlignH.RIGHT, AlignV.TOP)
        aLeftImg.disable()
        aRightImg.disable()
    }

    private fun addPanelGroup() {
        aPanelGroup.setSize(1372f, 836f)
        addActorAligned(aPanelGroup, AlignH.CENTER, AlignV.CENTER)
        aPanelGroup.apply {
            this.addAndFillActor(aPanelImg)
            this.addActors(aTryAgainBtn, aXBtn)
        }

        aTryAgainBtn.setBounds(297f, 303f, 777f, 229f)
        aXBtn.setBounds(1249f, 718f, 72f, 72f)

        aTryAgainBtn.setOnClickListener { blockTryAgain() }
        aXBtn.setOnClickListener { blockClose() }
    }

    // ------------------------------------------------------------------------
    // Animations
    // ------------------------------------------------------------------------

    fun animShowResultTryAgain() {
        clearActions()
        enable()
        animShow(0.25f)
    }

    fun animHideResultTryAgain() {
        clearActions()
        disable()
        animHide(0.25f)
    }

}