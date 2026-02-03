package com.gorillaz.puzzlegame.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.gorillaz.puzzlegame.game.utils.GameColor
import com.gorillaz.puzzlegame.game.utils.TIME_ANIM_SCREEN
import com.gorillaz.puzzlegame.game.utils.actor.animHide
import com.gorillaz.puzzlegame.game.utils.actor.animShow
import com.gorillaz.puzzlegame.game.utils.actor.disable
import com.gorillaz.puzzlegame.game.utils.actor.setBounds
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedGroup
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedScreen
import com.gorillaz.puzzlegame.game.utils.font.FontParameter
import com.gorillaz.puzzlegame.game.utils.gdxGame

class AStatusAvatar(override val screen: AdvancedScreen): AdvancedGroup() {

    private val parameter60 = FontParameter().setCharacters(FontParameter.CharType.NUMBERS).setSize(60)
    private val font60      = screen.fontGenerator_Regular.generateFont(parameter60)
    private val ls60        = Label.LabelStyle(font60, GameColor.white_FE)

    private val imgBuyed       = Image(gdxGame.assetsAll.buyed)
    private val groupPriceGems = ATmpGroup(screen)
    private val lblPriceGems   = Label("0", ls60)
    private val imgPriceGems   = Image(gdxGame.assetsAll.price_gems)

    override fun addActorsOnGroup() {
        addGroupPriceGems()
        addImgBuyed()

        animHideAll()
    }

    // Actors ------------------------------------------------------------------------

    private fun addGroupPriceGems() {
        addAndFillActor(groupPriceGems)
        groupPriceGems.addAndFillActor(imgPriceGems)
        groupPriceGems.addActor(lblPriceGems)

        lblPriceGems.setBounds(110f, 36f, 91f, 58f)
        lblPriceGems.setAlignment(Align.center)
    }

    private fun addImgBuyed() {
        addActor(imgBuyed)
        imgBuyed.setBounds(115f, 18f, 80f, 80f)
    }

    // Anim ------------------------------------------------------------------------

    fun animShowBuyed() {
        imgBuyed.apply {
            clearActions()
            animShow(TIME_ANIM_SCREEN)
        }
        groupPriceGems.apply {
            clearActions()
            animHide(TIME_ANIM_SCREEN)
        }
    }

    fun animShowPriceGems() {
        groupPriceGems.apply {
            clearActions()
            animShow(TIME_ANIM_SCREEN)
        }
        imgBuyed.apply {
            clearActions()
            animHide(TIME_ANIM_SCREEN)
        }
    }

    fun animHideAll() {
        groupPriceGems.apply {
            clearActions()
            animHide(TIME_ANIM_SCREEN)
        }
        imgBuyed.apply {
            clearActions()
            animHide(TIME_ANIM_SCREEN)
        }
    }

    // Logic ----------------------------------------------------------------------

    fun setPriceGems(price: Int) {
        lblPriceGems.setText(price)
    }

}