package com.senqorvia774.lottomatica.game.actors.slotGroup

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.senqorvia774.lottomatica.game.actors.ATmpGroup
import com.senqorvia774.lottomatica.game.actors.button.AButton
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
import com.senqorvia774.lottomatica.game.utils.actor.setBounds
import com.senqorvia774.lottomatica.game.utils.actor.setOnClickListener
import com.senqorvia774.lottomatica.game.utils.advanced.AdvancedGroup
import com.senqorvia774.lottomatica.game.utils.advanced.AdvancedScreen
import com.senqorvia774.lottomatica.game.utils.font.FontParameter
import com.senqorvia774.lottomatica.game.utils.gdxGame
import com.senqorvia774.lottomatica.game.utils.runGDX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AResultChampions(override val screen: AdvancedScreen): AdvancedGroup() {

    companion object {
        val WIN_COIN_FLOW = MutableStateFlow(0)
    }

    private val params = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars + "+").setSize(233)
    private val font   = screen.fontGenerator_Bold.generateFont(params)

    private val aLeftImg  = Image(gdxGame.assetsAll.COIN_LEFT)
    private val aRightImg = Image(gdxGame.assetsAll.COIN_RIGHT)

    private val aCoinsGroup  = ATmpGroup(screen)
    private val aCoinsImg    = Image(gdxGame.assetsAll.COINS)
    private val aGainBtn     = AButton(screen, AButton.Type.GAIN)
    private val aCoinLbl     = Label("", Label.LabelStyle(font, GameColor.blue_2CCEE3))

    var blockGain = {}

    override fun addActorsOnGroup() {
        addAndFillActor(Image(screen.drawerUtil.getTexture(GameColor.black_62)))
        addCoinGroup()
        addBlueBigCoins()
        addGainBtn()

        coroutine?.launch {
            WIN_COIN_FLOW.collect { coin ->
                runGDX { aCoinLbl.setText("+$coin") }
            }
        }
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun addBlueBigCoins() {
        aLeftImg.setSize(2752f, 1462f)
        addActorAligned(aLeftImg, AlignH.CENTER, AlignV.CENTER)
        //addActorAligned(aRightImg, AlignH.RIGHT, AlignV.BOTTOM)
        aLeftImg.disable()
        aRightImg.disable()
    }

    private fun addCoinGroup() {
        aCoinsGroup.setSize(844f, 280f)
        addActorAligned(aCoinsGroup, AlignH.CENTER, AlignV.CENTER)
        aCoinsGroup.apply { this.addActors(aCoinsImg, aCoinLbl) }

        aCoinsImg.setBounds(629f, 43f, 215f, 194f)
        aCoinLbl.setBounds(0f, 0f, 597f, 280f)
        aCoinLbl.setAlignment(Align.right)
    }

    private fun addGainBtn() {
        aGainBtn.setSize(472f, 212f)
        addActorAligned(aGainBtn, AlignH.CENTER, AlignV.BOTTOM)
        aGainBtn.y += 50f

        aGainBtn.setOnClickListener { blockGain.invoke() }
    }

    // ------------------------------------------------------------------------
    // Animations
    // ------------------------------------------------------------------------

    fun animShowResultChampions() {
        clearActions()
        enable()
        animShow(0.25f)
    }

    fun animHideResultChampions() {
        clearActions()
        disable()
        animHide(0.25f)
    }

}