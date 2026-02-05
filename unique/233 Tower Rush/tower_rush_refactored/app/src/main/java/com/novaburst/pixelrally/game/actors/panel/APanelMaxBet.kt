/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.novaburst.pixelrally.game.utils.ColorPalette
import com.novaburst.pixelrally.game.utils.advanced.ComponentGroup
import com.novaburst.pixelrally.game.utils.advanced.DisplayScreen
import com.novaburst.pixelrally.game.utils.font.TypefaceConfig
import com.novaburst.pixelrally.game.utils.gdxGame

class APanelMaxBet(override val screen: DisplayScreen): ComponentGroup() {

    private val parameter = TypefaceConfig()
        .setCharacters(TypefaceConfig.CharType.NUMBERS)
        .setSize(80)

    private val font80 = screen.fontGenerator_Regular.generateFont(parameter)

    private val ls80 = LabelStyle(font80, ColorPalette.black_26)

    private val imgPanel  = Image(gdxGame.assetsAll.PANEL_MAX_BET)
    private val lblMaxBet = Label("0", ls80)

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addLblMaxBet()
    }

    // Actors ------------------------------------------------------------------------

    // Core functionality
    private fun addLblMaxBet() {
        addActor(lblMaxBet)
        lblMaxBet.setBounds(369f, 106f, 107f, 81f)
    }

    // Logic --------------------------------------------------------------------------

    fun updateMaxBet(maxBet: Int) {
        lblMaxBet.setText(maxBet)
    }

}