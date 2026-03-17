package com.vortemika208.lavrix.game.actors.roulette

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.vortemika208.lavrix.game.utils.GameColor
import com.vortemika208.lavrix.game.utils.actor.addAndFillActor
import com.vortemika208.lavrix.game.utils.actor.animHide
import com.vortemika208.lavrix.game.utils.actor.animShow
import com.vortemika208.lavrix.game.utils.actor.disable
import com.vortemika208.lavrix.game.utils.actor.enable
import com.vortemika208.lavrix.game.utils.actor.setOnClickListener
import com.vortemika208.lavrix.game.utils.advanced.AdvancedGroup
import com.vortemika208.lavrix.game.utils.advanced.AdvancedScreen
import com.vortemika208.lavrix.game.utils.font.FontParameter
import com.vortemika208.lavrix.game.utils.gdxGame
import com.vortemika208.lavrix.game.utils.runGDX
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