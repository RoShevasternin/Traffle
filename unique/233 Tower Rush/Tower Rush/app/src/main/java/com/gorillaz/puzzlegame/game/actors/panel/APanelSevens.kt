package com.gorillaz.puzzlegame.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.gorillaz.puzzlegame.game.actors.button.ATextButton
import com.gorillaz.puzzlegame.game.utils.GameColor
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedGroup
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedScreen
import com.gorillaz.puzzlegame.game.utils.font.FontParameter
import com.gorillaz.puzzlegame.game.utils.gdxGame

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
            setBounds(221f, 98f, 213f, 151f)
            setOnClickListener { blockPlay() }
        }
    }

}