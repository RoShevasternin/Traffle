package com.vortemika208.w1n.game.actors.roulette

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.vortemika208.w1n.game.actors.ATmpGroup
import com.vortemika208.w1n.game.utils.AlignH
import com.vortemika208.w1n.game.utils.AlignV
import com.vortemika208.w1n.game.utils.GameColor
import com.vortemika208.w1n.game.utils.actor.addActorAligned
import com.vortemika208.w1n.game.utils.actor.addActors
import com.vortemika208.w1n.game.utils.actor.addAndFillActor
import com.vortemika208.w1n.game.utils.actor.animHide
import com.vortemika208.w1n.game.utils.actor.animShow
import com.vortemika208.w1n.game.utils.actor.disable
import com.vortemika208.w1n.game.utils.actor.enable
import com.vortemika208.w1n.game.utils.actor.setOnClickListener
import com.vortemika208.w1n.game.utils.advanced.AdvancedGroup
import com.vortemika208.w1n.game.utils.advanced.AdvancedScreen
import com.vortemika208.w1n.game.utils.font.FontParameter
import com.vortemika208.w1n.game.utils.gdxGame
import com.vortemika208.w1n.game.utils.runGDX
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
        addPanelGroup()
        addCoins()

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
        addActorAligned(aLeftImg, AlignH.LEFT, AlignV.TOP)
        addActorAligned(aRightImg, AlignH.RIGHT, AlignV.BOTTOM)
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