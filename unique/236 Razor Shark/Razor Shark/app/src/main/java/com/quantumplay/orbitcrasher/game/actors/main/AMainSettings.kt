package com.quantumplay.orbitcrasher.game.actors.main

import com.quantumplay.orbitcrasher.game.actors.button.AButton
import com.quantumplay.orbitcrasher.game.actors.button.AImageButton
import com.quantumplay.orbitcrasher.game.actors.panel.APanelMain
import com.quantumplay.orbitcrasher.game.actors.panel.APanelSettings
import com.quantumplay.orbitcrasher.game.actors.panel.APanelSettingsBottom
import com.quantumplay.orbitcrasher.game.screens.SettingsScreen
import com.quantumplay.orbitcrasher.game.utils.Block
import com.quantumplay.orbitcrasher.game.utils.TIME_ANIM_SCREEN
import com.quantumplay.orbitcrasher.game.utils.actor.animDelay
import com.quantumplay.orbitcrasher.game.utils.actor.animHide
import com.quantumplay.orbitcrasher.game.utils.actor.animShow
import com.quantumplay.orbitcrasher.game.utils.advanced.AdvancedMainGroup
import com.quantumplay.orbitcrasher.game.utils.gdxGame

class AMainSettings(
    override val screen: SettingsScreen,
): AdvancedMainGroup() {

    private val aPanelMain           = APanelMain(screen)
    private val btnBack              = AButton(screen, AButton.Type.Back)
    private val aPanelSettings       = APanelSettings(screen)
    private val aPanelSettingsBottom = APanelSettingsBottom(screen)


    override fun addActorsOnGroup() {
        color.a = 0f

        addAPanelMain()
        addBtnBack()
        addAPanelSettings()
        addAPanelSettingsBottom()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addAPanelMain() {
        addActor(aPanelMain)
        aPanelMain.setBounds(3f, 1641f, 746f, 279f)
    }

    private fun addBtnBack() {
        addActor(btnBack)
        btnBack.setBounds(958f, 1765f, 108f, 114f)
        btnBack.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addAPanelSettings() {
        addActor(aPanelSettings)
        aPanelSettings.setBounds(101f, 707f, 890f, 662f)
    }

    private fun addAPanelSettingsBottom() {
        addActor(aPanelSettingsBottom)
        aPanelSettingsBottom.setBounds(6f, -64f, 1068f, 283f)
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
        animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

}