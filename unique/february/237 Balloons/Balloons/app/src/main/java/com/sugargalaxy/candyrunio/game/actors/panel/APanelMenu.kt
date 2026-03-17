package com.sugargalaxy.candyrunio.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.sugargalaxy.candyrunio.game.actors.button.AImageButton
import com.sugargalaxy.candyrunio.game.actors.button.ATextButton
import com.sugargalaxy.candyrunio.game.utils.GameColor
import com.sugargalaxy.candyrunio.game.utils.advanced.AdvancedGroup
import com.sugargalaxy.candyrunio.game.utils.advanced.AdvancedScreen
import com.sugargalaxy.candyrunio.game.utils.font.FontParameter
import com.sugargalaxy.candyrunio.game.utils.gdxGame

class APanelMenu(override val screen: AdvancedScreen): AdvancedGroup() {

    private val parameter = FontParameter()
        .setCharacters(FontParameter.CharType.LATIN)
        .setSize(50)

    private val font50 = screen.fontGenerator_Regular.generateFont(parameter)

    private val ls50 = LabelStyle(font50, GameColor.black_09)

    private val listTextBtn = listOf("PROFILE", "SHOP", "GALLERY")

    private val imgPanel       = Image(gdxGame.assetsAll.PANEL_MENU)
    private val listTextButton = List(3) { ATextButton(screen, listTextBtn[it], ls50) }
    private val btnSetting     = AImageButton(screen, gdxGame.assetsAll.gear)

    private val listButton     = listTextButton + btnSetting

    var blockProfile  = {}
    var blockShop     = {}
    var blockGallery  = {}
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

        var nx = 48f
        listButton.onEachIndexed { index, btn ->
            btn.setBounds(nx, 121f, 213f, 151f)
            nx += 213f + 31f

            btn.setOnClickListener { listBlock[index].get().invoke() }
        }
    }

}