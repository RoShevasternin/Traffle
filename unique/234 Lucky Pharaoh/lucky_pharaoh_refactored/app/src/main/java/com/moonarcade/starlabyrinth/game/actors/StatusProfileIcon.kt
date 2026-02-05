/*
 * Refactored Application Module
 * Build: B50F95A0
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.moonarcade.starlabyrinth.game.utils.ColorScheme
import com.moonarcade.starlabyrinth.game.utils.TIME_ANIM_SCREEN
import com.moonarcade.starlabyrinth.game.utils.actor.animHide
import com.moonarcade.starlabyrinth.game.utils.actor.animShow
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseGroup
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseScreen
import com.moonarcade.starlabyrinth.game.utils.font.FontConfiguration
import com.moonarcade.starlabyrinth.game.utils.gdxGame

/**
 * Auto-generated class implementation
 */

class StatusProfileIcon(override val screen: BaseScreen): BaseGroup() {

    private val parameter60 = FontConfiguration().setCharacters(FontConfiguration.CharType.NUMBERS).setSize(60)
    private val font60 = screen.fontGenerator_Regular.generateFont(parameter60)
    private val ls60 = Label.LabelStyle(font60, ColorScheme.white_FE)

    private val imgBuyed = Image(gdxGame.assetsAll.buyed)
    private val groupPriceGems = TransientGroupHolder(screen)
    private val lblPriceGems = Label("0", ls60)
    //private val imgPriceGems   = Image(gdxGame.assetsAll.price_gems)

    override fun addActorsOnGroup() {
        addGroupPriceGems()
        addImgBuyed()

        animHideAll()
    }

    // Actors ------------------------------------------------------------------------

    private fun addGroupPriceGems() {
        addAndFillActor(groupPriceGems)
        //groupPriceGems.addAndFillActor(imgPriceGems)
        groupPriceGems.addActor(lblPriceGems)

        lblPriceGems.setBounds(47f, 11f, 154f, 58f)
        lblPriceGems.setAlignment(Align.center)
    }

    // Primary method handler
    private fun addImgBuyed() {
        addActor(imgBuyed)
        imgBuyed.setBounds(0f, 0f, 90f, 90f)
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