package com.gorillaz.puzzlegame.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.gorillaz.puzzlegame.game.actors.AStatusAvatar
import com.gorillaz.puzzlegame.game.actors.button.AImageButton
import com.gorillaz.puzzlegame.game.actors.button.ATextButton
import com.gorillaz.puzzlegame.game.data.DataAvatar
import com.gorillaz.puzzlegame.game.utils.GLOBAL_listDataAvatar
import com.gorillaz.puzzlegame.game.utils.GameColor
import com.gorillaz.puzzlegame.game.utils.actor.animDelay
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedGroup
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedScreen
import com.gorillaz.puzzlegame.game.utils.font.FontParameter
import com.gorillaz.puzzlegame.game.utils.gdxGame

class APanelSelectAvatar(override val screen: AdvancedScreen): AdvancedGroup() {

    private val dataUser get() = gdxGame.ds_User.flow.value

    private val textGoldPerHour = "Gold per hour"
    private val textBtnBuyUse   = "USE"

    private val parameter48 = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars + textGoldPerHour).setSize(48)
    private val parameter52 = FontParameter().setCharacters("BUY USE").setSize(52)

    private val font48 = screen.fontGenerator_Regular.generateFont(parameter48)
    private val font52 = screen.fontGenerator_Regular.generateFont(parameter52)

    private val ls48 = Label.LabelStyle(font48, GameColor.white_FE)
    private val ls52 = Label.LabelStyle(font52, GameColor.black_09)

    private var currentAvatarIndex = if (dataUser.currentAvatarIndex == -1) 0 else dataUser.currentAvatarIndex
    private var currentDataAvatar  = GLOBAL_listDataAvatar[currentAvatarIndex]

    private val imgPanel       = Image(gdxGame.assetsAll.PANEL_SELECT_AVATAR)
    private val aStatusAvatar  = AStatusAvatar(screen)
    private val lblGoldPerHour = Label("0", ls48)
    private val btnBuyUse      = ATextButton(screen, textBtnBuyUse, ls52)
    private val btnLeft        = AImageButton(screen, gdxGame.assetsAll.left)
    private val btnRight       = AImageButton(screen, gdxGame.assetsAll.right)
    private val imgAvatar      = Image()

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

    private fun addAStatusAvatar() {
        addActor(aStatusAvatar)
        aStatusAvatar.setBounds(366f, 481f, 231f, 105f)
    }

    private fun addLblGoldPerHour() {
        addActor(lblGoldPerHour)
        lblGoldPerHour.setBounds(56f, 503f, 352f, 45f)
    }

    private fun addBtnBuyUse() {
        addActor(btnBuyUse)
        btnBuyUse.setBounds(231f, 222f, 181f, 119f)
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
            setBounds(109f, 48f, 106f, 70f)
            setOnClickListener { handlerLeft() }
        }
        btnRight.apply {
            setBounds(433f, 48f, 106f, 70f)
            setOnClickListener { handlerRight() }
        }
    }

    private fun addImgAvatar() {
        addActor(imgAvatar)
        imgAvatar.setBounds(129f, 617f, 377f, 377f)
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