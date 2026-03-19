package com.senqorvia774.lottomatica.game.actors.roulette

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.senqorvia774.lottomatica.game.actors.ATmpGroup
import com.senqorvia774.lottomatica.game.utils.AlignH
import com.senqorvia774.lottomatica.game.utils.AlignV
import com.senqorvia774.lottomatica.game.utils.GameColor
import com.senqorvia774.lottomatica.game.utils.actor.addActorAligned
import com.senqorvia774.lottomatica.game.utils.actor.addActors
import com.senqorvia774.lottomatica.game.utils.actor.addAndFillActor
import com.senqorvia774.lottomatica.game.utils.actor.animHide
import com.senqorvia774.lottomatica.game.utils.actor.animShow
import com.senqorvia774.lottomatica.game.utils.actor.disable
import com.senqorvia774.lottomatica.game.utils.actor.enable
import com.senqorvia774.lottomatica.game.utils.actor.setOnClickListener
import com.senqorvia774.lottomatica.game.utils.advanced.AdvancedGroup
import com.senqorvia774.lottomatica.game.utils.advanced.AdvancedScreen
import com.senqorvia774.lottomatica.game.utils.gdxGame

class AResultTryAgain(override val screen: AdvancedScreen): AdvancedGroup() {

    private val aLeftImg  = Image(gdxGame.assetsAll.GRAY_COIN_LEFT)
    private val aRightImg = Image(gdxGame.assetsAll.GRAY_COIN_RIGHT)

    private val aPanelGroup  = ATmpGroup(screen)
    private val aPanelImg    = Image(gdxGame.assetsAll.PANEL_TRY_AGAIN)
    private val aTryAgainBtn = Actor()
    private val aXBtn        = Actor()

    var blockTryAgain = {}
    var blockClose    = {}

    override fun addActorsOnGroup() {
        addPanelGroup()
        //addCoins()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun addCoins() {
        addActorAligned(aLeftImg, AlignH.LEFT, AlignV.TOP)
        addActorAligned(aRightImg, AlignH.RIGHT, AlignV.BOTTOM)
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