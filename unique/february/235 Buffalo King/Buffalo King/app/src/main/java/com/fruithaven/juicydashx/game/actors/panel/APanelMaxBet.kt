package com.fruithaven.juicydashx.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.fruithaven.juicydashx.game.utils.GameColor
import com.fruithaven.juicydashx.game.utils.advanced.AdvancedGroup
import com.fruithaven.juicydashx.game.utils.advanced.AdvancedScreen
import com.fruithaven.juicydashx.game.utils.font.FontParameter
import com.fruithaven.juicydashx.game.utils.gdxGame

class APanelMaxBet(override val screen: AdvancedScreen): AdvancedGroup() {

    private val parameter = FontParameter()
        .setCharacters(FontParameter.CharType.NUMBERS)
        .setSize(80)

    private val font80 = screen.fontGenerator_Regular.generateFont(parameter)

    private val ls80 = LabelStyle(font80, GameColor.black_09)

    private val imgPanel  = Image(gdxGame.assetsAll.PANEL_MAX_BET)
    private val lblMaxBet = Label("0", ls80)

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addLblMaxBet()
    }

    // Actors ------------------------------------------------------------------------

    private fun addLblMaxBet() {
        addActor(lblMaxBet)
        lblMaxBet.setBounds(432f, 74f, 107f, 81f)
    }

    // Logic --------------------------------------------------------------------------

    fun updateMaxBet(maxBet: Int) {
        lblMaxBet.setText(maxBet)
    }

}