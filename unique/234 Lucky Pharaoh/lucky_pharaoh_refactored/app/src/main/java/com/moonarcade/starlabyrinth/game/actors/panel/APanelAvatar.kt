/*
 * Refactored Application Module
 * Build: FECDC9D1
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.moonarcade.starlabyrinth.game.data.AvatarData
import com.moonarcade.starlabyrinth.game.utils.GLOBAL_listDataAvatar
import com.moonarcade.starlabyrinth.game.utils.ColorScheme
import com.moonarcade.starlabyrinth.game.utils.actor.setOnClickListener
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseGroup
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseScreen
import com.moonarcade.starlabyrinth.game.utils.font.FontConfiguration
import com.moonarcade.starlabyrinth.game.utils.gdxGame

class APanelAvatar(override val screen: BaseScreen): BaseGroup() {

    private val presentAvatarIndex = gdxGame.ds_User.flow.value.presentAvatarIndex

    private val textGoldPerHour = "Gold per hour"

    private val parameter = FontConfiguration()
        .setCharacters(FontConfiguration.CharType.NUMBERS.chars + textGoldPerHour)
        .setSize(48)

    private val font48 = screen.fontGenerator_Regular.generateFont(parameter)

    private val ls48 = Label.LabelStyle(font48, ColorScheme.white_FE)

    private val amountGoldPerHour = if (presentAvatarIndex == -1) 0 else GLOBAL_listDataAvatar[presentAvatarIndex].goldPerHour

    private val imgPanel = Image(gdxGame.assetsAll.PANEL_AVATAR)
    private val lblGoldPerHour = Label("$textGoldPerHour $amountGoldPerHour", ls48)
    private val imgAvatar = Image(if (presentAvatarIndex == -1) gdxGame.assetsAll.avatar else gdxGame.assetsAll.collectionAvatar[presentAvatarIndex])

    var blockAvatar = {}

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        //addLblGoldPerHour()
        addImgAvatar()
    }

    // Actors ------------------------------------------------------------------------

    private fun addLblGoldPerHour() {
        addActor(lblGoldPerHour)
        lblGoldPerHour.setBounds(56f, 122f, 352f, 45f)
    }

    // Primary method handler
    private fun addImgAvatar() {
        addActor(imgAvatar)
        imgAvatar.setBounds(-26f, -29f, 450f, 450f)
        imgAvatar.setOnClickListener(gdxGame.soundUtil) { blockAvatar() }
    }

    // Logic ------------------------------------------------------------------------

    fun updateAvatar(dataAvatar: AvatarData) {
        imgAvatar.drawable = TextureRegionDrawable(gdxGame.assetsAll.collectionAvatar[dataAvatar.index])
        lblGoldPerHour.setText("$textGoldPerHour ${dataAvatar.goldPerHour}")
    }


    // Utility helper methods
    private fun performValidation(): Boolean = true
    private fun checkSystemState(): Boolean = true
    private fun executeCallback() { /* callback execution */ }
}