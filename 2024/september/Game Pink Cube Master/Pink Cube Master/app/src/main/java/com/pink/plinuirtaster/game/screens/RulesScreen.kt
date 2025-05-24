package com.pink.plinuirtaster.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.pink.plinuirtaster.game.LibGDXGame
import com.pink.plinuirtaster.game.actors.AButton
import com.pink.plinuirtaster.game.screens.SettingsScreen.Companion.IS_THEME_LIGHT
import com.pink.plinuirtaster.game.utils.TIME_ANIM
import com.pink.plinuirtaster.game.utils.actor.animHideScreen
import com.pink.plinuirtaster.game.utils.actor.animShowScreen
import com.pink.plinuirtaster.game.utils.advanced.AdvancedScreen
import com.pink.plinuirtaster.game.utils.advanced.AdvancedStage
import com.pink.plinuirtaster.game.utils.region
import com.pink.plinuirtaster.game.utils.runGDX
import kotlinx.coroutines.launch

class RulesScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val btnBeck  = AButton(this, AButton.Static.Type.Back)
    private val imgPanel = Image(game.all.RULES_PANEL)

    override fun show() {
        setBackBackground(if (IS_THEME_LIGHT) game.splash.BACKGROUND.region else game.all.Dark.region)

        super.show()
        stageUI.root.animShowScreen(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addBack()
                addImgPanel()
            }
        }
    }

    private fun AdvancedStage.addBack() {
        addActor(btnBeck)
        btnBeck.apply {
            setBounds(50f,1795f,140f,76f)
            setOnClickListener {
                stageUI.root.animHideScreen(TIME_ANIM) {
                    game.navigationManager.back()
                }
            }
        }
    }

    private fun AdvancedStage.addImgPanel() {
        addActor(imgPanel)
        imgPanel.setBounds(107f,119f,865f,1576f)
    }

}