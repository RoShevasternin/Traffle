package com.quenloria615.beton.game.actors.roulette

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
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
import com.quenloria615.beton.game.utils.font.FontParameter
import com.quenloria615.beton.game.utils.gdxGame
import com.quenloria615.beton.game.utils.runGDX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AResultWin(override val screen: AdvancedScreen): AdvancedGroup() {

    companion object {
        val WIN_COIN_FLOW = MutableStateFlow(0)
    }

    private val params = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars + "+").setSize(177)
    private val font   = screen.fontGenerator_Bold.generateFont(params)

    private val aLeftImg  = Image(gdxGame.assetsAll.COIN_LEFT)
    private val aRightImg = Image(gdxGame.assetsAll.COIN_RIGHT)

    private val aPanelGroup  = ATmpGroup(screen)
    private val aPanelImg    = Image(gdxGame.assetsAll.PANEL_ROULETTE_WIN)
    private val aXBtn        = Actor()
    private val aCoinLbl     = Label("", Label.LabelStyle(font, GameColor.blue_2CCEE3))

    var blockClose = {}

    override fun addActorsOnGroup() {
        addCoins()
        addPanelGroup()

        coroutine?.launch {
            WIN_COIN_FLOW.collect { coin ->
                runGDX { aCoinLbl.setText("+$coin") }
            }
        }
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
        val tmpLBL = ATmpGroup(screen)

        aPanelGroup.setSize(1372f, 836f)
        addActorAligned(aPanelGroup, AlignH.CENTER, AlignV.CENTER)
        aPanelGroup.apply {
            this.addAndFillActor(aPanelImg)
            this.addActors(aXBtn, tmpLBL)
        }

        aXBtn.setBounds(1249f, 718f, 72f, 72f)
        tmpLBL.setBounds(397f, 267f, 295f, 212f)
        tmpLBL.addAndFillActor(aCoinLbl)

        aCoinLbl.setAlignment(Align.right)
        tmpLBL.setOrigin(Align.center)
        tmpLBL.rotation = 16.85f



        aPanelGroup.setOnClickListener { blockClose() }
        aXBtn.setOnClickListener { blockClose() }
    }

    // ------------------------------------------------------------------------
    // Animations
    // ------------------------------------------------------------------------

    fun animShowResultWin() {
        clearActions()
        enable()
        animShow(0.25f)
    }

    fun animHideResultWin() {
        clearActions()
        disable()
        animHide(0.25f)
    }

}