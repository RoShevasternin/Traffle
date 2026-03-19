package com.senqorvia774.lottomatica.game.actors.roulette

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.senqorvia774.lottomatica.game.actors.mask.AOldMask
import com.senqorvia774.lottomatica.game.utils.AlignH
import com.senqorvia774.lottomatica.game.utils.AlignV
import com.senqorvia774.lottomatica.game.utils.HEIGHT_UI
import com.senqorvia774.lottomatica.game.utils.WIDTH_UI
import com.senqorvia774.lottomatica.game.utils.actor.addActorAligned
import com.senqorvia774.lottomatica.game.utils.actor.addAndFillActor
import com.senqorvia774.lottomatica.game.utils.actor.animHide
import com.senqorvia774.lottomatica.game.utils.actor.animShow
import com.senqorvia774.lottomatica.game.utils.actor.disable
import com.senqorvia774.lottomatica.game.utils.actor.enable
import com.senqorvia774.lottomatica.game.utils.actor.setOnClickListener
import com.senqorvia774.lottomatica.game.utils.advanced.AdvancedGroup
import com.senqorvia774.lottomatica.game.utils.advanced.AdvancedScreen
import com.senqorvia774.lottomatica.game.utils.gdxGame

class AGameRoulette(override val screen: AdvancedScreen): AdvancedGroup() {

    private val aPanelRouletteImg = Image(gdxGame.assetsAll.PANEL_ROUETTE)
    private val aMask             = AOldMask(screen, gdxGame.assetsAll.MASK, alphaWidth = WIDTH_UI.toInt(), alphaHeight = HEIGHT_UI.toInt())
    private val aRoulette         = ARoulette(screen)
    private val aXBtn             = Actor()

    var blockResult: (ARoulette.Result) -> Unit = {}
    var blockClose = {}

    override fun addActorsOnGroup() {
        addAndFillActor(aPanelRouletteImg)
        addRoulette()
        addXBtn()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun addXBtn() {
        addActor(aXBtn)
        aXBtn.setBounds(1249f, 718f, 72f, 72f)
        aXBtn.setOnClickListener { blockClose() }
    }

    private fun addRoulette() {
        addAndFillActor(aMask)

        aRoulette.setSize(1234f, 1234f)
        aMask.addActor(aRoulette)
        aRoulette.x += 69f
        aRoulette.y -= 492f

        aRoulette.blockResult = { result ->  blockResult.invoke(result) }
    }

    // ------------------------------------------------------------------------
    // Animations
    // ------------------------------------------------------------------------

    fun animShowGameRoulette() {
        clearActions()
        enable()
        animShow(0.25f)
    }

    fun animHideGameRoulette() {
        clearActions()
        disable()
        animHide(0.25f)
    }

}