package com.vortemika208.w1n.game.actors.roulette

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.vortemika208.w1n.game.actors.mask.AOldMask
import com.vortemika208.w1n.game.utils.AlignH
import com.vortemika208.w1n.game.utils.AlignV
import com.vortemika208.w1n.game.utils.HEIGHT_UI
import com.vortemika208.w1n.game.utils.WIDTH_UI
import com.vortemika208.w1n.game.utils.actor.addActorAligned
import com.vortemika208.w1n.game.utils.actor.addAndFillActor
import com.vortemika208.w1n.game.utils.actor.animHide
import com.vortemika208.w1n.game.utils.actor.animShow
import com.vortemika208.w1n.game.utils.actor.disable
import com.vortemika208.w1n.game.utils.actor.enable
import com.vortemika208.w1n.game.utils.actor.setOnClickListener
import com.vortemika208.w1n.game.utils.advanced.AdvancedGroup
import com.vortemika208.w1n.game.utils.advanced.AdvancedScreen
import com.vortemika208.w1n.game.utils.gdxGame

class AGameRoulette(override val screen: AdvancedScreen): AdvancedGroup() {

    private val aPanelRouletteImg = Image(gdxGame.assetsAll.PANEL_ROUETTE)
    private val aMask             = AOldMask(screen, alphaWidth = WIDTH_UI.toInt(), alphaHeight = HEIGHT_UI.toInt())
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
        aMask.setSize(1234f, 744f)
        addActorAligned(aMask, AlignH.CENTER, AlignV.BOTTOM)
        aMask.y += 10f

        aRoulette.setSize(1234f, 1234f)
        aMask.addActor(aRoulette)
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