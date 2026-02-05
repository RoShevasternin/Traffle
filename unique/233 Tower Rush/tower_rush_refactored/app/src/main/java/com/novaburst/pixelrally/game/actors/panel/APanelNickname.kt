/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.novaburst.pixelrally.game.actors.AInputText
import com.novaburst.pixelrally.game.utils.advanced.ComponentGroup
import com.novaburst.pixelrally.game.utils.advanced.DisplayScreen
import com.novaburst.pixelrally.game.utils.font.TypefaceConfig
import com.novaburst.pixelrally.game.utils.gdxGame

class APanelNickname(override val screen: DisplayScreen): ComponentGroup() {

    private val valueNickname = gdxGame.ds_User.flow.value.nickname

    private val parameter = TypefaceConfig()
        .setCharacters(TypefaceConfig.CharType.ALL)
        .setSize(70)

    private val font70 = screen.fontGenerator_Regular.generateFont(parameter)

    private val imgPanel  = Image(gdxGame.assetsAll.PANEL_NICKNAME)
    private val inputText = AInputText(screen, font = font70, align = Align.center)

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addInputText()
    }

    // Actors ------------------------------------------------------------------------

    private fun addInputText() {
        addActor(inputText)
        inputText.setBounds(118f, 116f, 407f, 71f)
        inputText.textField.text = if (valueNickname.isEmpty()) "NICKNAME" else valueNickname

        inputText.blockEnter = { newText -> gdxGame.ds_User.update { dataUser ->
            dataUser.nickname = newText
            dataUser
        } }

    }

}