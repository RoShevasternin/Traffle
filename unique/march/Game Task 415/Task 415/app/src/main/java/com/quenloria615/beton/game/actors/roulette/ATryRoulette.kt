package com.quenloria615.beton.game.actors.roulette

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.quenloria615.beton.game.utils.GameColor
import com.quenloria615.beton.game.utils.actor.addAndFillActor
import com.quenloria615.beton.game.utils.actor.animHide
import com.quenloria615.beton.game.utils.actor.animShow
import com.quenloria615.beton.game.utils.actor.disable
import com.quenloria615.beton.game.utils.actor.enable
import com.quenloria615.beton.game.utils.actor.setOnClickListener
import com.quenloria615.beton.game.utils.advanced.AdvancedGroup
import com.quenloria615.beton.game.utils.advanced.AdvancedScreen
import com.quenloria615.beton.game.utils.font.FontParameter
import com.quenloria615.beton.game.utils.gdxGame
import com.quenloria615.beton.game.utils.runGDX
import kotlinx.coroutines.launch

class ATryRoulette(override val screen: AdvancedScreen): AdvancedGroup() {

    private val aTryImg = Image(gdxGame.assetsAll.TRY_YOUR_LUCK)
    private val aX      = Actor()

    var blockClose = {}
    var blockStart = {}

    override fun addActorsOnGroup() {
        addAndFillActor(aTryImg)
        addActor(aX)
        aX.setBounds(1141f, 678f, 72f, 72f)
        aX.setOnClickListener { blockClose.invoke() }

        setOnClickListener { blockStart.invoke() }
    }

    // ------------------------------------------------------------------------
    // Animations
    // ------------------------------------------------------------------------

    fun animShowTryRoulette() {
        clearActions()
        enable()
        animShow(0.25f)
    }

    fun animHideTryRoulette() {
        clearActions()
        disable()
        animHide(0.25f)
    }

}