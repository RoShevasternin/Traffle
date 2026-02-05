/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.novaburst.pixelrally.game.actors.button.LabelButton
import com.novaburst.pixelrally.game.utils.ColorPalette
import com.novaburst.pixelrally.game.utils.advanced.ComponentGroup
import com.novaburst.pixelrally.game.utils.advanced.DisplayScreen
import com.novaburst.pixelrally.game.utils.font.TypefaceConfig
import com.novaburst.pixelrally.game.utils.gdxGame

class APanelSevens(override val screen: DisplayScreen): ComponentGroup() {

    private val parameter = TypefaceConfig()
        .setCharacters("PLAY")
        .setSize(62)

    private val font62 = screen.fontGenerator_Regular.generateFont(parameter)

    private val ls62 = LabelStyle(font62, ColorPalette.black_09)

    private val imgPanel = Image(gdxGame.assetsAll.PANEL_SEVENS)
    private val btnPlay  = LabelButton(screen, "PLAY", ls62)

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