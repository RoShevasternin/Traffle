package com.senqorvia774.lottomatica.game.actors.panel

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

class APanelDailyBonusWin(override val screen: AdvancedScreen): AdvancedGroup() {

    //private val params = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars + ":").setSize(58)
    //private val font   = screen.fontGenerator_Regular.generateFont(params)
    //private val aTimeLbl  = Label("00:00:00", Label.LabelStyle(font, Color.WHITE))

    private val aLeftImg  = Image(gdxGame.assetsAll.COIN_LEFT)
    private val aRightImg = Image(gdxGame.assetsAll.COIN_RIGHT)

    private val aPanelGroup = ATmpGroup(screen)
    private val aPanelImg   = Image(gdxGame.assetsAll.DAILY_BONUS_600)
    private val aGainBtn    = Actor()
    private val aXBtn       = Actor()

    var blockGain = {}
    var blockX    = {}

    override fun addActorsOnGroup() {
        addAndFillActor(Image(screen.drawerUtil.getTexture(GameColor.black_62)))
        addPanelGroup()
        addCoins()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun addCoins() {
        aLeftImg.setSize(2752f, 1462f)
        addActorAligned(aLeftImg, AlignH.CENTER, AlignV.CENTER)
        //addActorAligned(aRightImg, AlignH.RIGHT, AlignV.BOTTOM)
        aLeftImg.disable()
        aRightImg.disable()
    }

    private fun addPanelGroup() {
        aPanelGroup.setSize(1022f, 658f)
        addActorAligned(aPanelGroup, AlignH.CENTER, AlignV.CENTER)
        aPanelGroup.apply {
            this.addAndFillActor(aPanelImg)
            this.addActors(aGainBtn, aXBtn)
        }

        aGainBtn.setBounds(314f, 36f, 393f, 134f)
        aXBtn.setBounds(894f, 536f, 112f, 112f)

        aGainBtn.setOnClickListener { blockGain() }
        aXBtn.setOnClickListener { blockX() }
    }

    // ------------------------------------------------------------------------
    // Animations
    // ------------------------------------------------------------------------

    fun animShowBonus() {
        clearActions()
        enable()
        animShow(0.25f)
    }

    fun animHideBonus() {
        clearActions()
        disable()
        animHide(0.25f)
    }

}