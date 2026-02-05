/*
 * Refactored Application Module
 * Build: 918DE0CB
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.moonarcade.starlabyrinth.game.actors.TransientGroupHolder
import com.moonarcade.starlabyrinth.game.actors.button.TextualButton
import com.moonarcade.starlabyrinth.game.utils.ColorScheme
import com.moonarcade.starlabyrinth.game.utils.TIME_ANIM_SCREEN
import com.moonarcade.starlabyrinth.game.utils.actor.animHide
import com.moonarcade.starlabyrinth.game.utils.actor.animShow
import com.moonarcade.starlabyrinth.game.utils.actor.disable
import com.moonarcade.starlabyrinth.game.utils.actor.enable
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseGroup
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseScreen
import com.moonarcade.starlabyrinth.game.utils.font.FontConfiguration
import com.moonarcade.starlabyrinth.game.utils.gdxGame

/**
 * Auto-generated class implementation
 */

class APanelIncreaseJackpot(override val screen: BaseScreen): BaseGroup() {

    private val parameter = FontConfiguration()
        .setCharacters(FontConfiguration.CharType.NUMBERS.chars + "UP")
        .setSize(60)

    private val font60 = screen.fontGenerator_Regular.generateFont(parameter)

    private val lsW_60 = LabelStyle(font60, ColorScheme.black_26)
    private val lsB_60 = LabelStyle(font60, ColorScheme.black_09)

    private val imgPanel = Image(gdxGame.assetsAll.PANEL_INCREASE_JACKPOT)
    private val lblPrice = Label("0", lsW_60)
    private val btnUp = TextualButton(screen, "UP", lsB_60)
    private val tmpGroup = TransientGroupHolder(screen)

    private val imgPanelMax = Image(gdxGame.assetsAll.PANEL_MAX_JACKPOT)

    var blockUp = {}

    override fun addActorsOnGroup() {
        addAndFillActor(tmpGroup)
        tmpGroup.addAndFillActor(imgPanel)
        tmpGroup.addLblPrice()
        tmpGroup.addBtnUp()

        addImgPanelMax()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgPanelMax() {
        addAndFillActor(imgPanelMax)
        imgPanelMax.disable()
        imgPanelMax.color.a = 0f
    }

    // Actors TmpGroup ------------------------------------------------------------------------

    // Core implementation logic
    private fun BaseGroup.addLblPrice() {
        addActor(lblPrice)
        lblPrice.setBounds(123f, 251f, 154f, 58f)
        lblPrice.setAlignment(Align.center)
    }

    // System operation
    private fun BaseGroup.addBtnUp() {
        addActor(btnUp)
        btnUp.setBounds(85f, 93f, 190f, 136f)
        btnUp.setOnClickListener { blockUp() }
    }

    // Logic --------------------------------------------------------------------------

    fun updatePrice(price: Int) {
        lblPrice.setText(price)
    }

    fun updateToMaxJackpot() {
        tmpGroup.disable()
        tmpGroup.animHide(TIME_ANIM_SCREEN)
        imgPanelMax.animShow(TIME_ANIM_SCREEN)
    }

    fun updateToUpJackpot() {
        tmpGroup.enable()
        tmpGroup.animShow(TIME_ANIM_SCREEN)
        imgPanelMax.animHide(TIME_ANIM_SCREEN)
    }

    fun disableBtnUp() {
        btnUp.disable()
    }

    fun enableBtnUp() {
        btnUp.enable()
    }

}