package com.gorillaz.puzzlegame.game.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.OnscreenKeyboardType
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.gorillaz.puzzlegame.game.utils.GameColor
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedGroup
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedScreen

class AInputText(
    override val screen: AdvancedScreen,
    private val onscreenKeyboardType: OnscreenKeyboardType = OnscreenKeyboardType.Default,
    val font : BitmapFont,
    val align: Int = Align.left
): AdvancedGroup() {

    private val textFieldStyle = TextField.TextFieldStyle().apply {
            this.font  = this@AInputText.font
            fontColor  = GameColor.white_FE
            cursor     = TextureRegionDrawable(screen.drawerUtil.getTexture(GameColor.white_FE))
            selection  = TextureRegionDrawable(screen.drawerUtil.getTexture(GameColor.white_FE.cpy().apply { a = 0.25f }))
        }

    val textField = TextField("", textFieldStyle)

    var blockTextFieldListener: (String) -> Unit = {}
    var blockEnter            : (String) -> Unit = {}

    override fun addActorsOnGroup() {
        addTextField()
    }

    private fun addTextField() {
        addAndFillActor(textField)

        textField.alignment = this.align

        textField.setOnscreenKeyboard { visible ->
            Gdx.input.setOnscreenKeyboardVisible(visible, onscreenKeyboardType)
        }

        textField.setTextFieldListener { _, key ->
            blockTextFieldListener(textField.text)

            if (key == '\n' || key == '\r') { // Перевіряємо Enter або Return
                blockEnter(textField.text)
                stage.keyboardFocus = null
                Gdx.input.setOnscreenKeyboardVisible(false)
            }
        }
    }

}