package com.quenloria615.beton.game.actors

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.quenloria615.beton.game.utils.GameColor
import com.quenloria615.beton.game.utils.actor.addActors
import com.quenloria615.beton.game.utils.actor.addAndFillActor
import com.quenloria615.beton.game.utils.actor.setOnClickListener
import com.quenloria615.beton.game.utils.advanced.AdvancedGroup
import com.quenloria615.beton.game.utils.advanced.AdvancedScreen
import com.quenloria615.beton.game.utils.font.FontParameter
import com.quenloria615.beton.game.utils.gdxGame

class APanelStake(override val screen: AdvancedScreen): AdvancedGroup() {

    private val listStake = listOf(50, 100, 250, 400, 500, 700, 999)

    private var currentStakeIndex = 0
        set(value) {
            currentStake = listStake[value]
            field = value
        }

    var currentStake = listStake[currentStakeIndex]
        private set(value) {
            aStakeLbl.setText(value)
            field = value
        }

    private val params = FontParameter().setCharacters(FontParameter.CharType.NUMBERS).setSize(190)
    private val font   = screen.fontGenerator_Bold.generateFont(params)

    private val aPanelImg = Image(gdxGame.assetsAll.PANEL_STAKE)
    private val aStakeLbl = Label(currentStake.toString(), Label.LabelStyle(font, GameColor.white_D4D4D4))
    private val aPlusBtn  = Actor()
    private val aMinusBtn = Actor()
    private val aMaxBtn   = Actor()

    override fun addActorsOnGroup() {
        addAndFillActor(aPanelImg)
        addStakeLbl()
        addBtns()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun addStakeLbl() {
        addActor(aStakeLbl)
        aStakeLbl.setBounds(38f, 294f, 225f, 113f)
        aStakeLbl.setAlignment(Align.center)
    }

    private fun addBtns() {
        addActors(aPlusBtn, aMinusBtn, aMaxBtn)
        aPlusBtn.setBounds(97f, 516f, 93f, 93f)
        aMinusBtn.setBounds(97f, 174f, 93f, 93f)
        aMaxBtn.setBounds(18f, 22f, 252f, 118f)

        aPlusBtn.setOnClickListener {
            currentStakeIndex = if (currentStakeIndex + 1 > listStake.lastIndex) 0 else currentStakeIndex + 1
        }
        aMinusBtn.setOnClickListener {
            currentStakeIndex = if (currentStakeIndex - 1 < 0) listStake.lastIndex else currentStakeIndex - 1
        }
        aMaxBtn.setOnClickListener {
            currentStakeIndex = listStake.lastIndex
        }
    }

}