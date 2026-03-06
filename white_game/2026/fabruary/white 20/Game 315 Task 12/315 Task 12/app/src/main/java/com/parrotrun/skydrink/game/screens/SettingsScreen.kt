package com.parrotrun.skydrink.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.parrotrun.skydrink.game.LibGDXGame
import com.parrotrun.skydrink.game.actors.ASettingsGroup
import com.parrotrun.skydrink.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.parrotrun.skydrink.game.utils.actor.animHide
import com.parrotrun.skydrink.game.utils.actor.animShow
import com.parrotrun.skydrink.game.utils.actor.setOnClickListener
import com.parrotrun.skydrink.game.utils.advanced.AdvancedScreen
import com.parrotrun.skydrink.game.utils.advanced.AdvancedStage
import com.parrotrun.skydrink.game.utils.region

class SettingsScreen(override val game: LibGDXGame) : AdvancedScreen() {

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.assetsAll.B_BLUR.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM_SCREEN_ALPHA)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addSettings()
        addBack()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addSettings() {
        val settings = ASettingsGroup(this@SettingsScreen)
        addActor(settings)
        settings.setBounds(659f, 78f, 646f, 879f)
    }

    private fun AdvancedStage.addBack() {
        val menu = Actor()
        addActor(menu)
        menu.setBounds(1186f, 78f, 119f, 119f)

        menu.setOnClickListener(game.soundUtil) {
            stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) { game.navigationManager.back() }
        }
    }

}