package com.vortemika208.w1n.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.vortemika208.w1n.game.screens.LoaderScreen
import com.vortemika208.w1n.game.utils.GameColor
import com.vortemika208.w1n.game.utils.actor.addAndFillActor
import com.vortemika208.w1n.game.utils.advanced.AdvancedGroup
import com.vortemika208.w1n.game.utils.advanced.AdvancedScreen
import com.vortemika208.w1n.game.utils.font.FontParameter
import com.vortemika208.w1n.game.utils.gdxGame
import com.vortemika208.w1n.game.utils.runGDX
import kotlinx.coroutines.launch

class APanelCoin(override val screen: AdvancedScreen): AdvancedGroup() {

    private val params = FontParameter().setCharacters(FontParameter.CharType.NUMBERS).setSize(105)
    private val font   = screen.fontGenerator_Bold.generateFont(params)

    private val aPanelImg = Image(gdxGame.assetsAll.PANEL_COIN)
    private val aCoinLbl  = Label("", Label.LabelStyle(font, GameColor.white_D4D4D4))

    override fun addActorsOnGroup() {
        addAndFillActor(aPanelImg)
        addCoinLbl()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun addCoinLbl() {
        addActor(aCoinLbl)
        aCoinLbl.setBounds(308f, 0f, 159f, 127f)

        coroutine?.launch {
            gdxGame.modelPlayer.coinFlow.collect { coin ->
                runGDX { aCoinLbl.setText(coin) }
            }
        }
    }

}