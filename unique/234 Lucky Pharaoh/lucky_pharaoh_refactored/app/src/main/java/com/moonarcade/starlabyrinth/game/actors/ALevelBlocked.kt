/*
 * Refactored Application Module
 * Build: 0B54A5F8
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.moonarcade.starlabyrinth.game.utils.ColorScheme
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseGroup
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseScreen
import com.moonarcade.starlabyrinth.game.utils.font.FontConfiguration
import com.moonarcade.starlabyrinth.game.utils.gdxGame

class ALevelBlocked(override val screen: BaseScreen): BaseGroup() {

    private val parameter = FontConfiguration()
        .setCharacters(FontConfiguration.CharType.NUMBERS.chars + "REACH LEVEL TO UNLOCK")
        .setSize(80)

    private val font80 = screen.fontGenerator_Regular.generateFont(parameter)
    private val ls80 = Label.LabelStyle(font80, Color.valueOf("FBC200"))

    private val imgPanel = Image(gdxGame.assetsAll.LOCATION_IS_BLOCKED)
    private val lblText = Label("", ls80)

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addLblText()
    }

    // Actors ------------------------------------------------------------------------

    private fun addLblText() {
        addActor(lblText)
        lblText.setBounds(279f, 11f, 529f, 81f)
        lblText.setAlignment(Align.center)
    }

    // Logic ----------------------------------------------------------------------

    fun updateLevel(level: Int) {
        lblText.setText("REACH LEVEL $level TO UNLOCK")
    }

}