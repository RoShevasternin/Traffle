package com.quantumplay.orbitcrasher.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.quantumplay.orbitcrasher.game.actors.button.ATextButton
import com.quantumplay.orbitcrasher.game.utils.GameColor
import com.quantumplay.orbitcrasher.game.utils.advanced.AdvancedGroup
import com.quantumplay.orbitcrasher.game.utils.advanced.AdvancedScreen
import com.quantumplay.orbitcrasher.game.utils.font.FontParameter
import com.quantumplay.orbitcrasher.game.utils.gdxGame

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
            setBounds(129f, 521f, 213f, 151f)
            setOnClickListener { blockSpin() }
        }
    }

}