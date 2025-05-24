package com.gorillaz.puzzlegame.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.gorillaz.puzzlegame.game.actors.button.ATextButton
import com.gorillaz.puzzlegame.game.utils.GameColor
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedGroup
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedScreen
import com.gorillaz.puzzlegame.game.utils.font.FontParameter
import com.gorillaz.puzzlegame.game.utils.gdxGame

class APanelSettingsBottom(override val screen: AdvancedScreen): AdvancedGroup() {

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.ALL).setSize(40)

    private val font40 = screen.fontGenerator_Regular.generateFont(parameter)

    private val ls40 = Label.LabelStyle(font40, GameColor.black_09)

    private val listTextBtn = listOf("SHARE\nTHE APP", "RATE THE\nAPP")

    private val imgPanel = Image(gdxGame.assetsAll.PANEL_SETTINGS_BOTTOM)
    private val listBtn  = List(2) { ATextButton(screen, listTextBtn[it], ls40) }

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addListBtn()
    }

    // Actors ------------------------------------------------------------------------

    private fun addListBtn() {
        var nx = 262f
        listBtn.onEachIndexed { index, btn ->
            addActor(btn)
            btn.setBounds(nx, 37f, 213f, 151f)
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