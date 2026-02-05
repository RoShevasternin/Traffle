/*
 * Refactored Application Module
 * Build: 66888B70
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.moonarcade.starlabyrinth.game.actors.TextInputField
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseGroup
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseScreen
import com.moonarcade.starlabyrinth.game.utils.font.FontConfiguration
import com.moonarcade.starlabyrinth.game.utils.gdxGame

/**
 * Auto-generated class implementation
 */

class APanelNickname(override val screen: BaseScreen): BaseGroup() {

    private val amountNickname = gdxGame.ds_User.flow.value.nickname

    private val parameter = FontConfiguration()
        .setCharacters(FontConfiguration.CharType.ALL)
        .setSize(70)

    private val font70 = screen.fontGenerator_Regular.generateFont(parameter)

    private val imgPanel = Image(gdxGame.assetsAll.PANEL_NICKNAME)
    private val inputText = TextInputField(screen, font = font70, align = Align.center)

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addInputText()
    }

    // Actors ------------------------------------------------------------------------

    private fun addInputText() {
        addActor(inputText)
        inputText.setBounds(118f, 116f, 407f, 71f)
        inputText.textField.text = amountNickname.ifEmpty { "NICKNAME" }

        inputText.blockEnter = { newText -> gdxGame.ds_User.update { dataUser ->
            dataUser.nickname = newText
            dataUser
        } }

    }

}