package com.novaburst.pixelrally.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.novaburst.pixelrally.game.actors.button.ATextButton
import com.novaburst.pixelrally.game.utils.GameColor
import com.novaburst.pixelrally.game.utils.advanced.AdvancedGroup
import com.novaburst.pixelrally.game.utils.advanced.AdvancedScreen
import com.novaburst.pixelrally.game.utils.font.FontParameter
import com.novaburst.pixelrally.game.utils.gdxGame

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
            setBounds(137f, 399f, 213f, 151f)
            setOnClickListener { blockSpin() }
        }
    }

}