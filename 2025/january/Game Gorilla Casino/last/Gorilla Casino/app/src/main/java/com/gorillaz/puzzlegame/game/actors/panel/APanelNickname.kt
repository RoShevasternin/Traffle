package com.gorillaz.puzzlegame.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.gorillaz.puzzlegame.game.actors.AInputText
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedGroup
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedScreen
import com.gorillaz.puzzlegame.game.utils.font.FontParameter
import com.gorillaz.puzzlegame.game.utils.gdxGame

class APanelNickname(override val screen: AdvancedScreen): AdvancedGroup() {

    private val valueNickname = gdxGame.ds_User.flow.value.nickname

    private val parameter = FontParameter()
        .setCharacters(FontParameter.CharType.ALL)
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