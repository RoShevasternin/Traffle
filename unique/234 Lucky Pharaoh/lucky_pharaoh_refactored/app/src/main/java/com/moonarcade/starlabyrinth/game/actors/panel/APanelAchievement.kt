/*
 * Refactored Application Module
 * Build: 15EFB66F
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.actors.panel

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.moonarcade.starlabyrinth.game.utils.ColorScheme
import com.moonarcade.starlabyrinth.game.utils.ScaleCalculator
import com.moonarcade.starlabyrinth.game.utils.actor.setBoundsScaled
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseGroup
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseScreen
import com.moonarcade.starlabyrinth.game.utils.font.FontConfiguration
import com.moonarcade.starlabyrinth.game.utils.gdxGame

class APanelAchievement(override val screen: BaseScreen): BaseGroup() {

    override val sizeScaler = ScaleCalculator(ScaleCalculator.Axis.X, 1039f)

    private val informationAchievement = gdxGame.ds_Achievement.flow.value

    private val parameter = FontConfiguration()
        .setCharacters(FontConfiguration.CharType.NUMBERS)
        .setSize(70)

    private val font70 = screen.fontGenerator_Regular.generateFont(parameter)

    private val ls70 = Label.LabelStyle(font70, Color.valueOf("FBC200"))

    private val imgPanel = Image(gdxGame.assetsAll.PANEL_ACHIEVEMENT)
    private val lblNumOfSpin = Label(informationAchievement.number_of_spins.toString(), ls70)
    private val lblNumOfWins = Label(informationAchievement.number_of_wins.toString(), ls70)
    private val lblMaxWins = Label(informationAchievement.maximum_winnings.toString(), ls70)

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addLbls()
    }

    // Actors ------------------------------------------------------------------------

    private fun addLbls() {
        addActors(lblNumOfSpin, lblNumOfWins, lblMaxWins)
        lblNumOfSpin.apply {
            setBoundsScaled(sizeScaler, 484f, 455f, 74f, 71f)
            setAlignment(Align.center)
        }
        lblNumOfWins.apply {
            setBoundsScaled(sizeScaler, 484f, 280f + 5f, 74f, 71f)
            setAlignment(Align.center)
        }
        lblMaxWins.apply {
            setBoundsScaled(sizeScaler, 484f, 83f + 5f, 74f, 71f)
            setAlignment(Align.center)
        }
    }

}