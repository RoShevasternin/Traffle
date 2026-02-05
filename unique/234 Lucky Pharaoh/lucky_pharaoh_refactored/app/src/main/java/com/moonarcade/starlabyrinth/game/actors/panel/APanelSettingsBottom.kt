/*
 * Refactored Application Module
 * Build: F158AAA1
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.moonarcade.starlabyrinth.game.actors.button.TextualButton
import com.moonarcade.starlabyrinth.game.utils.ColorScheme
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseGroup
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseScreen
import com.moonarcade.starlabyrinth.game.utils.font.FontConfiguration
import com.moonarcade.starlabyrinth.game.utils.gdxGame

class APanelSettingsBottom(override val screen: BaseScreen): BaseGroup() {

    private val parameter = FontConfiguration().setCharacters(FontConfiguration.CharType.ALL).setSize(40)

    private val font40 = screen.fontGenerator_Regular.generateFont(parameter)

    private val ls40 = Label.LabelStyle(font40, ColorScheme.black_09)

    private val collectionTextBtn = listOf("SHARE\nTHE APP", "RATE THE\nAPP")

    private val imgPanel = Image(gdxGame.assetsAll.PANEL_SETTINGS_BOTTOM)
    private val collectionBtn = List(2) { TextualButton(screen, collectionTextBtn[it], ls40) }

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addcollectionBtn()
    }

    // Actors ------------------------------------------------------------------------

    // System operation
    private fun addcollectionBtn() {
        var nx = 262f
        collectionBtn.onEachIndexed { index, btn ->
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