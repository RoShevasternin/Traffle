package com.vortemika208.w1n.game.actors.slotGroup

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.vortemika208.w1n.game.actors.ATmpGroup
import com.vortemika208.w1n.game.actors.button.AButton
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
import com.vortemika208.w1n.game.utils.advanced.AdvancedGroup
import com.vortemika208.w1n.game.utils.advanced.AdvancedScreen
import com.vortemika208.w1n.game.utils.font.FontParameter
import com.vortemika208.w1n.game.utils.gdxGame
import com.vortemika208.w1n.game.utils.runGDX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AResultAdventures(override val screen: AdvancedScreen): AdvancedGroup() {

    companion object {
        val WIN_COIN_FLOW = MutableStateFlow(0)
    }

    private val params = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars + "+").setSize(233)
    private val font   = screen.fontGenerator_Bold.generateFont(params)

    private val aLeftImg  = Image(gdxGame.assetsAll.COIN_LEFT)
    private val aRightImg = Image(gdxGame.assetsAll.COIN_RIGHT)

    private val aCoinsGroup  = ATmpGroup(screen)
    private val aBigWinImg   = Image(gdxGame.assetsAll.BIG_WIN)
    private val aCoinsImg    = Image(gdxGame.assetsAll.COINS)
    private val aGainBtn     = AButton(screen, AButton.Type.GAIN)
    private val aCoinLbl     = Label("", Label.LabelStyle(font, GameColor.blue_2CCEE3))

    var blockGain = {}

    override fun addActorsOnGroup() {
        addAndFillActor(Image(screen.drawerUtil.getTexture(GameColor.black_62)))
        addCoinGroup()
        addBlueBigCoins()
        addBigWinImg()
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
        addActorAligned(aLeftImg, AlignH.LEFT, AlignV.TOP)
        addActorAligned(aRightImg, AlignH.RIGHT, AlignV.BOTTOM)
        aLeftImg.disable()
        aRightImg.disable()
    }

    private fun addBigWinImg() {
        aBigWinImg.setSize(1158f, 534f)
        addActorAligned(aBigWinImg, AlignH.CENTER, AlignV.TOP)
        aBigWinImg.y += 70f
    }

    private fun addCoinGroup() {
        aCoinsGroup.setSize(1012f, 280f)
        addActorAligned(aCoinsGroup, AlignH.CENTER, AlignV.CENTER)
        aCoinsGroup.apply { this.addActors(aCoinsImg, aCoinLbl) }

        aCoinsImg.setBounds(629f, 52f, 383f, 199f)
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

    fun animShowResultAdventures() {
        clearActions()
        enable()
        animShow(0.25f)
    }

    fun animHideResultAdventures() {
        clearActions()
        disable()
        animHide(0.25f)
    }

}