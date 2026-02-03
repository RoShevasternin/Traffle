package com.greciao.candyrocket.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.greciao.candyrocket.game.LibGDXGame
import com.greciao.candyrocket.game.actors.ASettingsGroup
import com.greciao.candyrocket.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.greciao.candyrocket.game.utils.actor.animHide
import com.greciao.candyrocket.game.utils.actor.animShow
import com.greciao.candyrocket.game.utils.actor.setOnClickListener
import com.greciao.candyrocket.game.utils.advanced.AdvancedScreen
import com.greciao.candyrocket.game.utils.advanced.AdvancedStage
import com.greciao.candyrocket.game.utils.region

class SettingsScreen(override val game: LibGDXGame) : AdvancedScreen() {

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.loaderAssets.background.region)
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
        settings.setBounds(482f, 54f, 369f, 492f)
    }

    private fun AdvancedStage.addBack() {
        val menu = Actor()
        addActor(menu)
        menu.setBounds(785f, 54f, 66f, 66f)

        menu.setOnClickListener(game.soundUtil) {
            stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) { game.navigationManager.back() }
        }
    }

}