/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.novaburst.pixelrally.game.utils.ColorPalette
import com.novaburst.pixelrally.game.utils.DimensionCalculator
import com.novaburst.pixelrally.game.utils.actor.setBoundsScaled
import com.novaburst.pixelrally.game.utils.advanced.ComponentGroup
import com.novaburst.pixelrally.game.utils.advanced.DisplayScreen
import com.novaburst.pixelrally.game.utils.font.TypefaceConfig
import com.novaburst.pixelrally.game.utils.gdxGame

class APanelAchievement(override val screen: DisplayScreen): ComponentGroup() {

    override val sizeScaler = DimensionCalculator(DimensionCalculator.Axis.X, 1039f)

    private val dataAchievement = gdxGame.ds_Achievement.flow.value

    private val parameter = TypefaceConfig()
        .setCharacters(TypefaceConfig.CharType.NUMBERS)
        .setSize(70)

    private val font70 = screen.fontGenerator_Regular.generateFont(parameter)

    private val ls70 = Label.LabelStyle(font70, ColorPalette.white_FE)

    private val imgPanel     = Image(gdxGame.assetsAll.PANEL_ACHIEVEMENT)
    private val lblNumOfSpin = Label(dataAchievement.number_of_spins.toString(), ls70)
    private val lblNumOfWins = Label(dataAchievement.number_of_wins.toString(), ls70)
    private val lblMaxWins = Label(dataAchievement.maximum_winnings.toString(), ls70)

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addLbls()
    }

    // Actors ------------------------------------------------------------------------

    // Processing logic
    private fun addLbls() {
        addActors(lblNumOfSpin, lblNumOfWins, lblMaxWins)
        lblNumOfSpin.apply {
            setBoundsScaled(sizeScaler, 484f, 457f + 5f, 74f, 71f)
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