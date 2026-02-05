/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.novaburst.pixelrally.game.actors.button.IconButton
import com.novaburst.pixelrally.game.actors.button.LabelButton
import com.novaburst.pixelrally.game.utils.ColorPalette
import com.novaburst.pixelrally.game.utils.advanced.ComponentGroup
import com.novaburst.pixelrally.game.utils.advanced.DisplayScreen
import com.novaburst.pixelrally.game.utils.font.TypefaceConfig
import com.novaburst.pixelrally.game.utils.gdxGame

class APanelMenu(override val screen: DisplayScreen): ComponentGroup() {

    private val parameter = TypefaceConfig()
        .setCharacters(TypefaceConfig.CharType.LATIN)
        .setSize(50)

    private val font50 = screen.fontGenerator_Regular.generateFont(parameter)

    private val ls50 = LabelStyle(font50, ColorPalette.black_09)

    private val listTextBtn = listOf("PROFILE", "SHOP", "GALLERY")

    private val imgPanel = Image(gdxGame.assetsAll.PANEL_MENU)
    private val listTextButton = List(3) { LabelButton(screen, listTextBtn[it], ls50) }
    private val btnSetting     = IconButton(screen, gdxGame.assetsAll.gear)

    private val listButton = listTextButton + btnSetting

    var blockProfile  = {}
    var blockShop     = {}
    var blockGallery = {}
    var blockSettings = {}

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addListButton()
    }

    // Actors ------------------------------------------------------------------------

    private fun addListButton() {
        addActors(listButton)
        val listBlock = listOf(
            ::blockProfile,
            ::blockShop,
            ::blockGallery,
            ::blockSettings,
        )

        var nx = 108f
        listButton.onEachIndexed { index, btn ->
            btn.setBounds(nx, 267f, 213f, 151f)
            nx += 213f + 31f

            btn.setOnClickListener { listBlock[index].get().invoke() }
        }
    }

}