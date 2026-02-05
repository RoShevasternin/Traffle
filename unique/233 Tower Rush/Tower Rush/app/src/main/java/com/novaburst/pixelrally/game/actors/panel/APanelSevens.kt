package com.novaburst.pixelrally.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.novaburst.pixelrally.game.actors.button.ATextButton
import com.novaburst.pixelrally.game.utils.GameColor
import com.novaburst.pixelrally.game.utils.advanced.AdvancedGroup
import com.novaburst.pixelrally.game.utils.advanced.AdvancedScreen
import com.novaburst.pixelrally.game.utils.font.FontParameter
import com.novaburst.pixelrally.game.utils.gdxGame

class APanelSevens(override val screen: AdvancedScreen): AdvancedGroup() {

    private val parameter = FontParameter()
        .setCharacters("PLAY")
        .setSize(62)

    private val font62 = screen.fontGenerator_Regular.generateFont(parameter)

    private val ls62 = LabelStyle(font62, GameColor.black_09)

    private val imgPanel = Image(gdxGame.assetsAll.PANEL_SEVENS)
    private val btnPlay  = ATextButton(screen, "PLAY", ls62)

    var blockPlay = {}

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addBtnPlay()
    }

    // Actors ------------------------------------------------------------------------

    private fun addBtnPlay() {
        addActor(btnPlay)
        btnPlay.apply {
            setBounds(246f, 98f, 207f, 151f)
            setOnClickListener { blockPlay() }
        }
    }

}