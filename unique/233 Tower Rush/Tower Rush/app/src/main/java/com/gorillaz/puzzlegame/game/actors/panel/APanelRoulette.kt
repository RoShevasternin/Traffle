package com.gorillaz.puzzlegame.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.gorillaz.puzzlegame.game.actors.button.ATextButton
import com.gorillaz.puzzlegame.game.utils.GameColor
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedGroup
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedScreen
import com.gorillaz.puzzlegame.game.utils.font.FontParameter
import com.gorillaz.puzzlegame.game.utils.gdxGame

class APanelRoulette(override val screen: AdvancedScreen): AdvancedGroup() {

    private val parameter = FontParameter()
        .setCharacters("SPIN")
        .setSize(62)

    private val font62 = screen.fontGenerator_Regular.generateFont(parameter)

    private val ls62 = LabelStyle(font62, GameColor.black_09)

    private val imgPanel = Image(gdxGame.assetsAll.PANEL_ROULETTE)
    private val btnSpin  = ATextButton(screen, "SPIN", ls62)

    var blockSpin = {}

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addBtnSpin()
    }

    // Actors ------------------------------------------------------------------------

    private fun addBtnSpin() {
        addActor(btnSpin)
        btnSpin.apply {
            setBounds(40f, 108f, 213f, 151f)
            setOnClickListener { blockSpin() }
        }
    }

}