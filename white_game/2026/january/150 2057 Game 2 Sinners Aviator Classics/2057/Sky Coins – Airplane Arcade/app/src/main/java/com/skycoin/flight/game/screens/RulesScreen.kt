package com.skycoin.flight.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.skycoin.flight.game.LibGDXGame
import com.skycoin.flight.game.actors.button.AButton
import com.skycoin.flight.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.skycoin.flight.game.utils.actor.animHide
import com.skycoin.flight.game.utils.actor.animShow
import com.skycoin.flight.game.utils.actor.setOnClickListener
import com.skycoin.flight.game.utils.advanced.AdvancedScreen
import com.skycoin.flight.game.utils.advanced.AdvancedStage
import com.skycoin.flight.game.utils.region

class RulesScreen(override val game: LibGDXGame) : AdvancedScreen() {

    override fun show() {
        stageUI.root.animHide()
        setUIBackground(game.gameAssets.mainB.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM_SCREEN_ALPHA)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addBack()
        addRules()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addBack() {
        val menu = AButton(this@RulesScreen, AButton.Static.Type.MENU)
        addActor(menu)
        menu.setBounds(249f, 81f, 150f, 150f)

        menu.setOnClickListener(game.soundUtil) {
            stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) { game.navigationManager.back() }
        }
    }

    private fun AdvancedStage.addRules() {
        val rules = Image(game.gameAssets.rils)
        addActor(rules)
        rules.setBounds(89f, 357f, 470f, 689f)
    }

}