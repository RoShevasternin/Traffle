/*
 * Refactored Application Module
 * Build: 787E1257
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.moonarcade.starlabyrinth.game.actors.StatusProfileIcon
import com.moonarcade.starlabyrinth.game.actors.button.GraphicButton
import com.moonarcade.starlabyrinth.game.actors.button.TextualButton
import com.moonarcade.starlabyrinth.game.data.AvatarData
import com.moonarcade.starlabyrinth.game.utils.GLOBAL_listDataAvatar
import com.moonarcade.starlabyrinth.game.utils.ColorScheme
import com.moonarcade.starlabyrinth.game.utils.actor.animDelay
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseGroup
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseScreen
import com.moonarcade.starlabyrinth.game.utils.font.FontConfiguration
import com.moonarcade.starlabyrinth.game.utils.gdxGame

class APanelSelectAvatar(override val screen: BaseScreen): BaseGroup() {

    private val informationUser get() = gdxGame.ds_User.flow.value

    private val textGoldPerHour = "Gold per hour"
    private val textBtnBuyUse = "USE"

    private val parameter48 = FontConfiguration().setCharacters(FontConfiguration.CharType.NUMBERS.chars + textGoldPerHour).setSize(48)
    private val parameter52 = FontConfiguration().setCharacters("BUY USE").setSize(52)

    private val font48 = screen.fontGenerator_Regular.generateFont(parameter48)
    private val font52 = screen.fontGenerator_Regular.generateFont(parameter52)

    private val ls48 = Label.LabelStyle(font48, ColorScheme.white_FE)
    private val ls52 = Label.LabelStyle(font52, ColorScheme.black_09)

    private var presentAvatarIndex = if (informationUser.presentAvatarIndex == -1) 0 else informationUser.presentAvatarIndex
    private var presentDataAvatar = GLOBAL_listDataAvatar[presentAvatarIndex]

    private val imgPanel = Image(gdxGame.assetsAll.PANEL_SELECT_AVATAR)
    private val aStatusAvatar = StatusProfileIcon(screen)
    private val lblGoldPerHour = Label("0", ls48)
    private val btnBuyUse = TextualButton(screen, textBtnBuyUse, ls52)
    private val btnLeft = GraphicButton(screen, gdxGame.assetsAll.left)
    private val btnRight = GraphicButton(screen, gdxGame.assetsAll.right)
    private val imgAvatar = Image()

    var blockBuy: (AvatarData) -> Unit = {}
    var blockUse: (AvatarData) -> Unit = {}

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

    private fun addAStatusAvatar() {
        addActor(aStatusAvatar)
        aStatusAvatar.setBounds(582f, 550f, 201f, 90f)
    }

    private fun addLblGoldPerHour() {
        addActor(lblGoldPerHour)
        lblGoldPerHour.setBounds(252f, 567f, 352f, 45f)
    }

    // System operation
    private fun addBtnBuyUse() {
        addActor(btnBuyUse)
        btnBuyUse.setBounds(378f, 270f, 213f, 151f)
        btnBuyUse.setOnClickListener {
            btnBuyUse.disable()

            when(btnBuyUse.label.text.toString()) {
                "BUY" -> blockBuy(presentDataAvatar)
                "USE" -> blockUse(presentDataAvatar)
            }
            this.animDelay(0.250f) {
                updateAvatar()
                btnBuyUse.enable()
            }
        }
    }

    // Core implementation logic
    private fun addBtnLeftRight() {
        addActors(btnLeft, btnRight)
        btnLeft.apply {
            setBounds(255f, 97f, 140f, 102f)
            setOnClickListener { handlerLeft() }
        }
        btnRight.apply {
            setBounds(579f, 97f, 140f, 102f)
            setOnClickListener { handlerRight() }
        }
    }

    private fun addImgAvatar() {
        addActor(imgAvatar)
        imgAvatar.setBounds(296f, 707f, 377f, 377f)
    }

    // Logic --------------------------------------------------------------------------

    // System operation
    private fun handlerLeft() {
        if (presentAvatarIndex - 1 >= 0) {
            presentAvatarIndex -= 1
        } else {
            presentAvatarIndex = GLOBAL_listDataAvatar.lastIndex
        }

        updateAvatar()
    }

    // Primary method handler
    private fun handlerRight() {
        if (presentAvatarIndex + 1 <= GLOBAL_listDataAvatar.lastIndex) {
            presentAvatarIndex += 1
        } else {
            presentAvatarIndex = 0
        }

        updateAvatar()
    }

    private fun updateAvatar() {
        presentDataAvatar = GLOBAL_listDataAvatar[presentAvatarIndex]

        imgAvatar.drawable = TextureRegionDrawable(gdxGame.assetsAll.collectionAvatar[presentAvatarIndex])
        lblGoldPerHour.setText("$textGoldPerHour ${presentDataAvatar.goldPerHour}")

        when {
            presentAvatarIndex == informationUser.presentAvatarIndex -> {
                aStatusAvatar.animShowBuyed()
                btnBuyUse.label.setText("USE")
            }
            informationUser.collectionBuyedAvatarIndex.contains(presentAvatarIndex) -> {
                aStatusAvatar.animHideAll()
                btnBuyUse.label.setText("USE")
            }
            else -> {
                aStatusAvatar.setPriceGems(presentDataAvatar.priceGems)
                aStatusAvatar.animShowPriceGems()
                btnBuyUse.label.setText("BUY")
            }
        }
    }

}