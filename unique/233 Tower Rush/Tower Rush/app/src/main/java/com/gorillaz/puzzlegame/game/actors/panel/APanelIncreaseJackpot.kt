package com.gorillaz.puzzlegame.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.gorillaz.puzzlegame.game.actors.ATmpGroup
import com.gorillaz.puzzlegame.game.actors.button.ATextButton
import com.gorillaz.puzzlegame.game.utils.GameColor
import com.gorillaz.puzzlegame.game.utils.TIME_ANIM_SCREEN
import com.gorillaz.puzzlegame.game.utils.actor.animHide
import com.gorillaz.puzzlegame.game.utils.actor.animShow
import com.gorillaz.puzzlegame.game.utils.actor.disable
import com.gorillaz.puzzlegame.game.utils.actor.enable
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedGroup
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedScreen
import com.gorillaz.puzzlegame.game.utils.font.FontParameter
import com.gorillaz.puzzlegame.game.utils.gdxGame

class APanelIncreaseJackpot(override val screen: AdvancedScreen): AdvancedGroup() {

    private val parameter = FontParameter()
        .setCharacters(FontParameter.CharType.NUMBERS.chars + "UP")
        .setSize(60)

    private val font60 = screen.fontGenerator_Regular.generateFont(parameter)

    private val lsW_60 = LabelStyle(font60, GameColor.white_FE)
    private val lsB_60 = LabelStyle(font60, GameColor.black_09)

    private val imgPanel  = Image(gdxGame.assetsAll.PANEL_INCREASE_JACKPOT)
    private val lblPrice  = Label("0", lsW_60)
    private val btnUp     = ATextButton(screen, "UP", lsB_60)
    private val tmpGroup  = ATmpGroup(screen)

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

    private fun AdvancedGroup.addLblPrice() {
        addActor(lblPrice)
        lblPrice.setBounds(169f, 184f, 63f, 61f)
        lblPrice.setAlignment(Align.center)
    }

    private fun AdvancedGroup.addBtnUp() {
        addActor(btnUp)
        btnUp.setBounds(85f, 29f, 190f, 136f)
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