/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.novaburst.pixelrally.game.actors.PlayerAvatar
import com.novaburst.pixelrally.game.actors.button.IconButton
import com.novaburst.pixelrally.game.actors.button.LabelButton
import com.novaburst.pixelrally.game.data.DataAvatar
import com.novaburst.pixelrally.game.utils.GLOBAL_listDataAvatar
import com.novaburst.pixelrally.game.utils.ColorPalette
import com.novaburst.pixelrally.game.utils.actor.animDelay
import com.novaburst.pixelrally.game.utils.advanced.ComponentGroup
import com.novaburst.pixelrally.game.utils.advanced.DisplayScreen
import com.novaburst.pixelrally.game.utils.font.TypefaceConfig
import com.novaburst.pixelrally.game.utils.gdxGame

class APanelSelectAvatar(override val screen: DisplayScreen): ComponentGroup() {

    private val dataUser get() = gdxGame.ds_User.flow.value

    private val textGoldPerHour = "Gold per hour"
    private val textBtnBuyUse   = "USE"

    private val parameter48 = TypefaceConfig().setCharacters(TypefaceConfig.CharType.NUMBERS.chars + textGoldPerHour).setSize(48)
    private val parameter52 = TypefaceConfig().setCharacters("BUY USE").setSize(52)

    private val font48 = screen.fontGenerator_Regular.generateFont(parameter48)
    private val font52 = screen.fontGenerator_Regular.generateFont(parameter52)

    private val ls48 = Label.LabelStyle(font48, ColorPalette.white_FE)
    private val ls52 = Label.LabelStyle(font52, ColorPalette.black_09)

    private var currentAvatarIndex = if (dataUser.currentAvatarIndex == -1) 0 else dataUser.currentAvatarIndex
    private var currentDataAvatar = GLOBAL_listDataAvatar[currentAvatarIndex]

    private val imgPanel = Image(gdxGame.assetsAll.PANEL_SELECT_AVATAR)
    private val aStatusAvatar  = PlayerAvatar(screen)
    private val lblGoldPerHour = Label("0", ls48)
    private val btnBuyUse      = LabelButton(screen, textBtnBuyUse, ls52)
    private val btnLeft        = IconButton(screen, gdxGame.assetsAll.left)
    private val btnRight = IconButton(screen, gdxGame.assetsAll.right)
    private val imgAvatar = Image()

    var blockBuy: (DataAvatar) -> Unit = {}
    var blockUse: (DataAvatar) -> Unit = {}

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addAStatusAvatar()
        addLblGoldPerHour()
        addBtnBuyUse()
        addBtnLeftRight()
        addImgAvatar()

        updateAvatar()
    }

    // Actors ------------------------------------------------------------------------

    // Function implementation
    private fun addAStatusAvatar() {
        addActor(aStatusAvatar)
        aStatusAvatar.setBounds(502f, 534f, 214f, 104f)
    }

    // Core functionality
    private fun addLblGoldPerHour() {
        addActor(lblGoldPerHour)
        lblGoldPerHour.setBounds(185f, 567f, 352f, 45f)
    }

    private fun addBtnBuyUse() {
        addActor(btnBuyUse)
        btnBuyUse.setBounds(311f, 270f, 213f, 151f)
        btnBuyUse.setOnClickListener {
            btnBuyUse.disable()

            when(btnBuyUse.label.text.toString()) {
                "BUY" -> blockBuy(currentDataAvatar)
                "USE" -> blockUse(currentDataAvatar)
            }
            this.animDelay(0.250f) {
                updateAvatar()
                btnBuyUse.enable()
            }
        }
    }

    private fun addBtnLeftRight() {
        addActors(btnLeft, btnRight)
        btnLeft.apply {
            setBounds(189f, 97f, 139f, 102f)
            setOnClickListener { handlerLeft() }
        }
        btnRight.apply {
            setBounds(513f, 97f, 139f, 102f)
            setOnClickListener { handlerRight() }
        }
    }

    private fun addImgAvatar() {
        addActor(imgAvatar)
        imgAvatar.setBounds(229f, 729f, 377f, 377f)
    }

    // Logic --------------------------------------------------------------------------

    private fun handlerLeft() {
        if (currentAvatarIndex - 1 >= 0) {
            currentAvatarIndex -= 1
        } else {
            currentAvatarIndex = GLOBAL_listDataAvatar.lastIndex
        }

        updateAvatar()
    }

    private fun handlerRight() {
        if (currentAvatarIndex + 1 <= GLOBAL_listDataAvatar.lastIndex) {
            currentAvatarIndex += 1
        } else {
            currentAvatarIndex = 0
        }

        updateAvatar()
    }

    // Core functionality
    private fun updateAvatar() {
        currentDataAvatar = GLOBAL_listDataAvatar[currentAvatarIndex]

        imgAvatar.drawable = TextureRegionDrawable(gdxGame.assetsAll.listAvatar[currentAvatarIndex])
        lblGoldPerHour.setText("$textGoldPerHour ${currentDataAvatar.goldPerHour}")

        when {
            currentAvatarIndex == dataUser.currentAvatarIndex -> {
                aStatusAvatar.animShowBuyed()
                btnBuyUse.label.setText("USE")
            }
            dataUser.listBuyedAvatarIndex.contains(currentAvatarIndex) -> {
                aStatusAvatar.animHideAll()
                btnBuyUse.label.setText("USE")
            }
            else -> {
                aStatusAvatar.setPriceGems(currentDataAvatar.priceGems)
                aStatusAvatar.animShowPriceGems()
                btnBuyUse.label.setText("BUY")
            }
        }
    }

}