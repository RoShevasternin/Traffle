package com.senqorvia774.lottomatica.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.senqorvia774.lottomatica.game.screens.LoaderScreen
import com.senqorvia774.lottomatica.game.utils.GameColor
import com.senqorvia774.lottomatica.game.utils.actor.addAndFillActor
import com.senqorvia774.lottomatica.game.utils.advanced.AdvancedGroup
import com.senqorvia774.lottomatica.game.utils.advanced.AdvancedScreen
import com.senqorvia774.lottomatica.game.utils.font.FontParameter
import com.senqorvia774.lottomatica.game.utils.gdxGame
import com.senqorvia774.lottomatica.game.utils.runGDX
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
        aCoinLbl.setBounds(203f, 2f, 159f, 127f)

        coroutine?.launch {
            gdxGame.modelPlayer.coinFlow.collect { coin ->
                runGDX { aCoinLbl.setText(coin) }
            }
        }
    }

}