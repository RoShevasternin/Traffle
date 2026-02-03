package com.dasholy.olympusdash.game.screens

import com.dasholy.olympusdash.game.LibGDXGame
import com.dasholy.olympusdash.game.actors.ASettingsGroup
import com.dasholy.olympusdash.game.actors.button.AButton
import com.dasholy.olympusdash.game.actors.checkbox.ACheckBox
import com.dasholy.olympusdash.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.dasholy.olympusdash.game.utils.actor.animHide
import com.dasholy.olympusdash.game.utils.actor.animShow
import com.dasholy.olympusdash.game.utils.actor.setOnClickListener
import com.dasholy.olympusdash.game.utils.advanced.AdvancedScreen
import com.dasholy.olympusdash.game.utils.advanced.AdvancedStage
import com.dasholy.olympusdash.game.utils.region

class SettingsScreen(override val game: LibGDXGame) : AdvancedScreen() {

    override fun show() {
        stageUI.root.animHide()
        setUIBackground(game.gameAssets.mainB.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM_SCREEN_ALPHA)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addBack()
        addSettings()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addBack() {
        val menu = AButton(this@SettingsScreen, AButton.Static.Type.MENU)
        addActor(menu)
        menu.setBounds(249f, 81f, 150f, 150f)

        menu.setOnClickListener(game.soundUtil) {
            stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) { game.navigationManager.back() }
        }
    }

    private fun AdvancedStage.addSettings() {
        val settings = ASettingsGroup(this@SettingsScreen)
        addActor(settings)
        settings.setBounds(89f, 393f, 488f, 653f)
    }

}