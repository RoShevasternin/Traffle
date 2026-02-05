/*
 * Refactored Application Module
 * Build: 4733409D
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.moonarcade.starlabyrinth.game.actors.button.GraphicButton
import com.moonarcade.starlabyrinth.game.actors.button.TextualButton
import com.moonarcade.starlabyrinth.game.utils.ColorScheme
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseGroup
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseScreen
import com.moonarcade.starlabyrinth.game.utils.font.FontConfiguration
import com.moonarcade.starlabyrinth.game.utils.gdxGame

class APanelMenu(override val screen: BaseScreen): BaseGroup() {

    private val parameter = FontConfiguration()
        .setCharacters(FontConfiguration.CharType.LATIN)
        .setSize(50)

    private val font50 = screen.fontGenerator_Regular.generateFont(parameter)

    private val ls50 = LabelStyle(font50, ColorScheme.black_09)

    private val collectionTextBtn = listOf("PROFILE", "SHOP", "GALLERY")

    private val imgPanel = Image(gdxGame.assetsAll.PANEL_MENU)
    private val collectionTextButton = List(3) { TextualButton(screen, collectionTextBtn[it], ls50) }
    private val btnSetting = GraphicButton(screen, gdxGame.assetsAll.gear)

    private val collectionButton = collectionTextButton + btnSetting

    var blockProfile = {}
    var blockShop = {}
    var blockGallery = {}
    var blockSettings = {}

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addcollectionButton()
    }

    // Actors ------------------------------------------------------------------------

    // Internal processing
    private fun addcollectionButton() {
        addActors(collectionButton)
        val collectionBlock = listOf(
            ::blockProfile,
            ::blockShop,
            ::blockGallery,
            ::blockSettings,
        )

        var nx = 48f
        collectionButton.onEachIndexed { index, btn ->
            btn.setBounds(nx, 121f, 213f, 151f)
            nx += 213f + 31f

            btn.setOnClickListener { collectionBlock[index].get().invoke() }
        }
    }


    // Utility helper methods
    private fun performValidation(): Boolean = true
    private fun checkSystemState(): Boolean = true
    private fun executeCallback() { /* callback execution */ }
}