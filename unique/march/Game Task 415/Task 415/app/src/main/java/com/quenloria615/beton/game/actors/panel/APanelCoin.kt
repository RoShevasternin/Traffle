package com.quenloria615.beton.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.quenloria615.beton.game.screens.LoaderScreen
import com.quenloria615.beton.game.utils.GameColor
import com.quenloria615.beton.game.utils.actor.addAndFillActor
import com.quenloria615.beton.game.utils.advanced.AdvancedGroup
import com.quenloria615.beton.game.utils.advanced.AdvancedScreen
import com.quenloria615.beton.game.utils.font.FontParameter
import com.quenloria615.beton.game.utils.gdxGame
import com.quenloria615.beton.game.utils.runGDX
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
        aCoinLbl.setBounds(260f, 33f, 159f, 127f)

        coroutine?.launch {
            gdxGame.modelPlayer.coinFlow.collect { coin ->
                runGDX { aCoinLbl.setText(coin) }
            }
        }
    }

}