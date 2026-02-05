package com.novaburst.pixelrally.game.actors.panel

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.novaburst.pixelrally.game.actors.button.AImageButton
import com.novaburst.pixelrally.game.actors.button.ATextButton
import com.novaburst.pixelrally.game.utils.GameColor
import com.novaburst.pixelrally.game.utils.actor.PosSize
import com.novaburst.pixelrally.game.utils.actor.setBounds
import com.novaburst.pixelrally.game.utils.advanced.AdvancedGroup
import com.novaburst.pixelrally.game.utils.advanced.AdvancedScreen
import com.novaburst.pixelrally.game.utils.font.FontParameter
import com.novaburst.pixelrally.game.utils.gdxGame

class APanelSelectInapp(override val screen: AdvancedScreen): AdvancedGroup() {

    private val listInapp = listOf(
        InApp(PosSize(0f, 213f, 911f, 836f),   gdxGame.assetsAll.INAPP_10K_GOLD, "1.99$"),
        InApp(PosSize(0f, 213f, 911f, 836f),  gdxGame.assetsAll.INAPP_100_GEMS,  "2.99$"),
        InApp(PosSize(0f, 213f, 864f, 1012f), gdxGame.assetsAll.INAPP_1K_GEMS,  "9.99$"),
    )

    private data class InApp(val posSize: PosSize, val texture: Texture, val price: String)

    private val parameter60 = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars + ".$").setSize(60)

    private val font60 = screen.fontGenerator_Regular.generateFont(parameter60)

    private val ls60 = Label.LabelStyle(font60, GameColor.black_09)

    private var currentIndex = 0
    private var currentInapp = listInapp[currentIndex]

    private val imgPanelSelect = Image(gdxGame.assetsAll.PANEL_SHOP_SELECTOR)
    private val btnBuy         = ATextButton(screen, currentInapp.price, ls60)
    private val btnLeft        = AImageButton(screen, gdxGame.assetsAll.left)
    private val btnRight       = AImageButton(screen, gdxGame.assetsAll.right)
    private val imgInapp       = Image(currentInapp.texture)

    var blockBuy: (String) -> Unit = {}

    override fun addActorsOnGroup() {
        addImgInapp()
        addBtnBuy()
        addBtnLeftRight()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgInapp() {
        addActor(imgInapp)
        imgInapp.setBounds(currentInapp.posSize)
    }

    private fun addBtnBuy() {
        addActor(btnBuy)
        btnBuy.setBounds(341f, 331f, 181f, 119f)
        btnBuy.setOnClickListener { blockBuy(currentInapp.price) }
    }

    private fun addBtnLeftRight() {
        addActors(imgPanelSelect, btnLeft, btnRight)
        imgPanelSelect.setBounds(161f, 0f, 542f, 224f)
        btnLeft.apply {
            setBounds(202f, 97f, 140f, 102f)
            setOnClickListener { handlerLeft() }
        }
        btnRight.apply {
            setBounds(526f, 97f, 140f, 102f)
            setOnClickListener { handlerRight() }
        }
    }

    // Logic --------------------------------------------------------------------------

    private fun handlerLeft() {
        if (currentIndex - 1 >= 0) {
            currentIndex -= 1
        } else {
            currentIndex = listInapp.lastIndex
        }

        updateInapp()
    }

    private fun handlerRight() {
        if (currentIndex + 1 <= listInapp.lastIndex) {
            currentIndex += 1
        } else {
            currentIndex = 0
        }

        updateInapp()
    }

    private fun updateInapp() {
        currentInapp = listInapp[currentIndex]

        imgInapp.setBounds(currentInapp.posSize)
        imgInapp.drawable = TextureRegionDrawable(currentInapp.texture)
        btnBuy.label.setText(currentInapp.price)
    }

}