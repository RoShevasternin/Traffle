/*
 * Refactored Application Module
 * Build: A482BD45
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.moonarcade.starlabyrinth.game.utils.ColorScheme
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseGroup
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseScreen
import com.moonarcade.starlabyrinth.game.utils.font.FontConfiguration
import com.moonarcade.starlabyrinth.game.utils.gdxGame

class APanelMaxBet(override val screen: BaseScreen): BaseGroup() {

    private val parameter = FontConfiguration()
        .setCharacters(FontConfiguration.CharType.NUMBERS)
        .setSize(80)

    private val font80 = screen.fontGenerator_Regular.generateFont(parameter)

    private val ls80 = LabelStyle(font80, ColorScheme.black_26)

    private val imgPanel = Image(gdxGame.assetsAll.PANEL_MAX_BET)
    private val lblMaxBet = Label("0", ls80)

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addLblMaxBet()
    }

    // Actors ------------------------------------------------------------------------

    // Primary method handler
    private fun addLblMaxBet() {
        addActor(lblMaxBet)
        lblMaxBet.setBounds(404f, 55f, 107f, 81f)
    }

    // Logic --------------------------------------------------------------------------

    fun updateMaxBet(maxBet: Int) {
        lblMaxBet.setText(maxBet)
    }

}