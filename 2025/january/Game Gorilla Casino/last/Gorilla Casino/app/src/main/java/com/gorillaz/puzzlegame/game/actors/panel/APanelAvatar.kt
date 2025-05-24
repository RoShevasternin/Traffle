package com.gorillaz.puzzlegame.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.gorillaz.puzzlegame.game.data.DataAvatar
import com.gorillaz.puzzlegame.game.utils.GLOBAL_listDataAvatar
import com.gorillaz.puzzlegame.game.utils.GameColor
import com.gorillaz.puzzlegame.game.utils.actor.setOnClickListener
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedGroup
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedScreen
import com.gorillaz.puzzlegame.game.utils.font.FontParameter
import com.gorillaz.puzzlegame.game.utils.gdxGame

class APanelAvatar(override val screen: AdvancedScreen): AdvancedGroup() {

    private val currentAvatarIndex = gdxGame.ds_User.flow.value.currentAvatarIndex

    private val textGoldPerHour = "Gold per hour"

    private val parameter = FontParameter()
        .setCharacters(FontParameter.CharType.NUMBERS.chars + textGoldPerHour)
        .setSize(48)

    private val font48 = screen.fontGenerator_Regular.generateFont(parameter)

    private val ls48 = Label.LabelStyle(font48, GameColor.white_FE)

    private val valueGoldPerHour = if (currentAvatarIndex == -1) 0 else GLOBAL_listDataAvatar[currentAvatarIndex].goldPerHour

    private val imgPanel       = Image(gdxGame.assetsAll.PANEL_AVATAR)
    private val lblGoldPerHour = Label("$textGoldPerHour $valueGoldPerHour", ls48)
    private val imgAvatar      = Image(if (currentAvatarIndex == -1) gdxGame.assetsAll.avatar else gdxGame.assetsAll.listAvatar[currentAvatarIndex])

    var blockAvatar = {}

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addLblGoldPerHour()
        addImgAvatar()
    }

    // Actors ------------------------------------------------------------------------

    private fun addLblGoldPerHour() {
        addActor(lblGoldPerHour)
        lblGoldPerHour.setBounds(56f, 122f, 352f, 45f)
    }

    private fun addImgAvatar() {
        addActor(imgAvatar)
        imgAvatar.setBounds(194f, 185f, 377f, 377f)

        imgAvatar.setOnClickListener(gdxGame.soundUtil) { blockAvatar() }
    }

    // Logic ------------------------------------------------------------------------

    fun updateAvatar(dataAvatar: DataAvatar) {
        imgAvatar.drawable = TextureRegionDrawable(gdxGame.assetsAll.listAvatar[dataAvatar.index])
        lblGoldPerHour.setText("$textGoldPerHour ${dataAvatar.goldPerHour}")
    }

}