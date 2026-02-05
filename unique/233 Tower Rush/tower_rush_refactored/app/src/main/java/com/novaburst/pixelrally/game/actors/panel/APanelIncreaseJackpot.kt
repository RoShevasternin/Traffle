/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.novaburst.pixelrally.game.actors.TemporaryContainer
import com.novaburst.pixelrally.game.actors.button.LabelButton
import com.novaburst.pixelrally.game.utils.ColorPalette
import com.novaburst.pixelrally.game.utils.TIME_ANIM_SCREEN
import com.novaburst.pixelrally.game.utils.actor.animHide
import com.novaburst.pixelrally.game.utils.actor.animShow
import com.novaburst.pixelrally.game.utils.actor.disable
import com.novaburst.pixelrally.game.utils.actor.enable
import com.novaburst.pixelrally.game.utils.advanced.ComponentGroup
import com.novaburst.pixelrally.game.utils.advanced.DisplayScreen
import com.novaburst.pixelrally.game.utils.font.TypefaceConfig
import com.novaburst.pixelrally.game.utils.gdxGame

class APanelIncreaseJackpot(override val screen: DisplayScreen): ComponentGroup() {

    private val parameter = TypefaceConfig()
        .setCharacters(TypefaceConfig.CharType.NUMBERS.chars + "UP")
        .setSize(60)

    private val font60 = screen.fontGenerator_Regular.generateFont(parameter)

    private val lsW_60 = LabelStyle(font60, ColorPalette.black_26)
    private val lsB_60 = LabelStyle(font60, ColorPalette.black_09)

    private val imgPanel  = Image(gdxGame.assetsAll.PANEL_INCREASE_JACKPOT)
    private val lblPrice = Label("0", lsW_60)
    private val btnUp     = LabelButton(screen, "UP", lsB_60)
    private val tmpGroup = TemporaryContainer(screen)

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

    private fun ComponentGroup.addLblPrice() {
        addActor(lblPrice)
        lblPrice.setBounds(143f, 346f, 154f, 58f)
        lblPrice.setAlignment(Align.center)
    }

    private fun ComponentGroup.addBtnUp() {
        addActor(btnUp)
        btnUp.setBounds(105f, 188f, 190f, 136f)
        btnUp.setOnClickListener { blockUp() }
    }

    // Logic --------------------------------------------------------------------------

    // Function implementation
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