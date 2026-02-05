package com.moonarcade.starlabyrinth.game.actors.panel

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.moonarcade.starlabyrinth.game.utils.GameColor
import com.moonarcade.starlabyrinth.game.utils.SizeScaler
import com.moonarcade.starlabyrinth.game.utils.actor.setBoundsScaled
import com.moonarcade.starlabyrinth.game.utils.advanced.AdvancedGroup
import com.moonarcade.starlabyrinth.game.utils.advanced.AdvancedScreen
import com.moonarcade.starlabyrinth.game.utils.font.FontParameter
import com.moonarcade.starlabyrinth.game.utils.gdxGame

class APanelAchievement(override val screen: AdvancedScreen): AdvancedGroup() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, 1039f)

    private val dataAchievement = gdxGame.ds_Achievement.flow.value

    private val parameter = FontParameter()
        .setCharacters(FontParameter.CharType.NUMBERS)
        .setSize(70)

    private val font70 = screen.fontGenerator_Regular.generateFont(parameter)

    private val ls70 = Label.LabelStyle(font70, Color.valueOf("FBC200"))

    private val imgPanel     = Image(gdxGame.assetsAll.PANEL_ACHIEVEMENT)
    private val lblNumOfSpin = Label(dataAchievement.number_of_spins.toString(), ls70)
    private val lblNumOfWins = Label(dataAchievement.number_of_wins.toString(), ls70)
    private val lblMaxWins   = Label(dataAchievement.maximum_winnings.toString(), ls70)

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