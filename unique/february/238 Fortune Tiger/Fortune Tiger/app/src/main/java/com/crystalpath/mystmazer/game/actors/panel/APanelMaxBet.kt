package com.crystalpath.mystmazer.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.crystalpath.mystmazer.game.utils.GameColor
import com.crystalpath.mystmazer.game.utils.advanced.AdvancedGroup
import com.crystalpath.mystmazer.game.utils.advanced.AdvancedScreen
import com.crystalpath.mystmazer.game.utils.font.FontParameter
import com.crystalpath.mystmazer.game.utils.gdxGame

class APanelMaxBet(override val screen: AdvancedScreen): AdvancedGroup() {

    private val parameter = FontParameter()
        .setCharacters(FontParameter.CharType.NUMBERS)
        .setSize(80)

    private val font80 = screen.fontGenerator_Regular.generateFont(parameter)

    private val ls80 = LabelStyle(font80, GameColor.white_FE)

    private val imgPanel  = Image(gdxGame.assetsAll.PANEL_MAX_BET)
    private val lblMaxBet = Label("0", ls80)

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addLblMaxBet()
    }

    // Actors ------------------------------------------------------------------------

    private fun addLblMaxBet() {
        addActor(lblMaxBet)
        lblMaxBet.setBounds(350f, 76f, 107f, 81f)
    }

    // Logic --------------------------------------------------------------------------

    fun updateMaxBet(maxBet: Int) {
        lblMaxBet.setText(maxBet)
    }

}