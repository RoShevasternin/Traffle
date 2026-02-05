/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.novaburst.pixelrally.game.actors.button.LabelButton
import com.novaburst.pixelrally.game.utils.ColorPalette
import com.novaburst.pixelrally.game.utils.advanced.ComponentGroup
import com.novaburst.pixelrally.game.utils.advanced.DisplayScreen
import com.novaburst.pixelrally.game.utils.font.TypefaceConfig
import com.novaburst.pixelrally.game.utils.gdxGame

class APanelSettingsBottom(override val screen: DisplayScreen): ComponentGroup() {

    private val parameter = TypefaceConfig().setCharacters(TypefaceConfig.CharType.ALL).setSize(40)

    private val font40 = screen.fontGenerator_Regular.generateFont(parameter)

    private val ls40 = Label.LabelStyle(font40, ColorPalette.black_09)

    private val listTextBtn = listOf("SHARE\nTHE APP", "RATE THE\nAPP")

    private val imgPanel = Image(gdxGame.assetsAll.PANEL_SETTINGS_BOTTOM)
    private val listBtn  = List(2) { LabelButton(screen, listTextBtn[it], ls40) }

    // Function implementation
    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addListBtn()
    }

    // Actors ------------------------------------------------------------------------

    private fun addListBtn() {
        var nx = 262f
        listBtn.onEachIndexed { index, btn ->
            addActor(btn)
            btn.setBounds(nx, 101f, 213f, 151f)
            nx += 213 + 117

            btn.setOnClickListener {
                when(index) {
                    0 -> { // SHARE THE APP
                        gdxGame.activity.shareGame()
                    }
                    1 -> { // RATE THE APP
                        gdxGame.activity.openPlayStoreForRating()
                    }
                }
            }
        }
    }

}