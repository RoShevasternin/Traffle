package com.quenloria615.beton.game.actors.slotGroup

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.quenloria615.beton.game.actors.ATmpGroup
import com.quenloria615.beton.game.actors.button.AButton
import com.quenloria615.beton.game.utils.AlignH
import com.quenloria615.beton.game.utils.AlignV
import com.quenloria615.beton.game.utils.GameColor
import com.quenloria615.beton.game.utils.actor.addActorAligned
import com.quenloria615.beton.game.utils.actor.addActorWithConstraints
import com.quenloria615.beton.game.utils.actor.addActors
import com.quenloria615.beton.game.utils.actor.addAndFillActor
import com.quenloria615.beton.game.utils.actor.animHide
import com.quenloria615.beton.game.utils.actor.animShow
import com.quenloria615.beton.game.utils.actor.disable
import com.quenloria615.beton.game.utils.actor.enable
import com.quenloria615.beton.game.utils.advanced.AdvancedGroup
import com.quenloria615.beton.game.utils.advanced.AdvancedScreen
import com.quenloria615.beton.game.utils.font.FontParameter
import com.quenloria615.beton.game.utils.gdxGame
import com.quenloria615.beton.game.utils.runGDX
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
        addBlueBigCoins()
        addBigWinImg()
        addGainBtn()
        addCoinGroup()

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
        aLeftImg.setSize(981f, 777f)
        aRightImg.setSize(813f, 732f)
        addActorAligned(aLeftImg, AlignH.LEFT, AlignV.BOTTOM)
        addActorAligned(aRightImg, AlignH.RIGHT, AlignV.TOP)
        aLeftImg.disable()
        aRightImg.disable()
    }

    private fun addBigWinImg() {
        aBigWinImg.setSize(820f, 489f)
        addActorAligned(aBigWinImg, AlignH.CENTER, AlignV.TOP)
        aBigWinImg.y -= 45f
    }

    private fun addGainBtn() {
        aGainBtn.setSize(472f, 212f)
        addActorAligned(aGainBtn, AlignH.CENTER, AlignV.BOTTOM)
        aGainBtn.y += 50f

        aGainBtn.setOnClickListener { blockGain.invoke() }
    }

    private fun addCoinGroup() {
        aCoinsGroup.setSize(910f, 343f)
        addActorWithConstraints(aCoinsGroup) {
            startToStartOf = this@AResultAdventures
            endToEndOf     = this@AResultAdventures
            topToBottomOf  = aBigWinImg
            bottomToTopOf  = aGainBtn
        }
        aCoinsGroup.apply { this.addActors(aCoinsImg, aCoinLbl) }

        aCoinsImg.setBounds(565f, 0f, 345f, 343f)
        aCoinLbl.setBounds(14f, 3f, 597f, 280f)
        aCoinLbl.setAlignment(Align.right)
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