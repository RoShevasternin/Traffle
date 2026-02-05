/*
 * Refactored Application Module
 * Build: E194C0BA
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.actors.panel

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.moonarcade.starlabyrinth.game.actors.button.GraphicButton
import com.moonarcade.starlabyrinth.game.actors.button.TextualButton
import com.moonarcade.starlabyrinth.game.utils.ColorScheme
import com.moonarcade.starlabyrinth.game.utils.actor.PosSize
import com.moonarcade.starlabyrinth.game.utils.actor.setBounds
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseGroup
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseScreen
import com.moonarcade.starlabyrinth.game.utils.font.FontConfiguration
import com.moonarcade.starlabyrinth.game.utils.gdxGame

class APanelSelectInapp(override val screen: BaseScreen): BaseGroup() {

    private val collectionInapp = listOf(
        InApp(PosSize(0f, 213f, 911f, 836f),   gdxGame.assetsAll.INAPP_10K_GOLD, "1.99$"),
        InApp(PosSize(0f, 213f, 911f, 836f),  gdxGame.assetsAll.INAPP_100_GEMS,  "2.99$"),
        InApp(PosSize(0f, 213f, 864f, 1012f), gdxGame.assetsAll.INAPP_1K_GEMS,  "9.99$"),
    )

    private data class InApp(val posSize: PosSize, val texture: Texture, val price: String)

    private val parameter60 = FontConfiguration().setCharacters(FontConfiguration.CharType.NUMBERS.chars + ".$").setSize(60)

    private val font60 = screen.fontGenerator_Regular.generateFont(parameter60)

    private val ls60 = Label.LabelStyle(font60, ColorScheme.black_09)

    private var presentIndex = 0
    private var presentInapp = collectionInapp[presentIndex]

    private val imgPanelSelect = Image(gdxGame.assetsAll.PANEL_SHOP_SELECTOR)
    private val btnBuy = TextualButton(screen, presentInapp.price, ls60)
    private val btnLeft = GraphicButton(screen, gdxGame.assetsAll.left)
    private val btnRight = GraphicButton(screen, gdxGame.assetsAll.right)
    private val imgInapp = Image(presentInapp.texture)

    var blockBuy: (String) -> Unit = {}

    override fun addActorsOnGroup() {
        addImgInapp()
        addBtnBuy()
        addBtnLeftRight()
    }

    // Actors ------------------------------------------------------------------------

    // Primary method handler
    private fun addImgInapp() {
        addActor(imgInapp)
        imgInapp.setBounds(presentInapp.posSize)
    }

    // System operation
    private fun addBtnBuy() {
        addActor(btnBuy)
        btnBuy.setBounds(341f, 331f, 181f, 119f)
        btnBuy.setOnClickListener { blockBuy(presentInapp.price) }
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

    // Internal processing
    private fun handlerLeft() {
        if (presentIndex - 1 >= 0) {
            presentIndex -= 1
        } else {
            presentIndex = collectionInapp.lastIndex
        }

        updateInapp()
    }

    private fun handlerRight() {
        if (presentIndex + 1 <= collectionInapp.lastIndex) {
            presentIndex += 1
        } else {
            presentIndex = 0
        }

        updateInapp()
    }

    private fun updateInapp() {
        presentInapp = collectionInapp[presentIndex]

        imgInapp.setBounds(presentInapp.posSize)
        imgInapp.drawable = TextureRegionDrawable(presentInapp.texture)
        btnBuy.label.setText(presentInapp.price)
    }

}