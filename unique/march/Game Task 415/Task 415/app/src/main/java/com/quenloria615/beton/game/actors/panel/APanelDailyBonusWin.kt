package com.quenloria615.beton.game.actors.panel

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