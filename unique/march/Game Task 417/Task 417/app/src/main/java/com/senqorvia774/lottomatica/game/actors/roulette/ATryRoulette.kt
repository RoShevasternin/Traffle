package com.senqorvia774.lottomatica.game.actors.roulette

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.senqorvia774.lottomatica.game.utils.GameColor
import com.senqorvia774.lottomatica.game.utils.actor.addAndFillActor
import com.senqorvia774.lottomatica.game.utils.actor.animHide
import com.senqorvia774.lottomatica.game.utils.actor.animShow
import com.senqorvia774.lottomatica.game.utils.actor.disable
import com.senqorvia774.lottomatica.game.utils.actor.enable
import com.senqorvia774.lottomatica.game.utils.actor.setOnClickListener
import com.senqorvia774.lottomatica.game.utils.advanced.AdvancedGroup
import com.senqorvia774.lottomatica.game.utils.advanced.AdvancedScreen
import com.senqorvia774.lottomatica.game.utils.font.FontParameter
import com.senqorvia774.lottomatica.game.utils.gdxGame
import com.senqorvia774.lottomatica.game.utils.runGDX
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