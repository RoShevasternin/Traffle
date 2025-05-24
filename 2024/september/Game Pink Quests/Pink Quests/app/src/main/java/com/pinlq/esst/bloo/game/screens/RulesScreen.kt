package com.pinlq.esst.bloo.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.pinlq.esst.bloo.game.LibGDXGame
import com.pinlq.esst.bloo.game.actors.AButton
import com.pinlq.esst.bloo.game.utils.TIME_ANIM
import com.pinlq.esst.bloo.game.utils.actor.animHideScreen
import com.pinlq.esst.bloo.game.utils.actor.animShowScreen
import com.pinlq.esst.bloo.game.utils.advanced.AdvancedScreen
import com.pinlq.esst.bloo.game.utils.advanced.AdvancedStage
import com.pinlq.esst.bloo.game.utils.region
import com.pinlq.esst.bloo.game.utils.runGDX
import kotlinx.coroutines.launch

class RulesScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val imgPanel = Image(game.all.rules)
    private val btnBack  = AButton(this, AButton.Static.Type.Back)

    override fun show() {
          setBackBackground(game.splash.listBackground[MenuScreen.BACKGROUND_INDEX].region)
        super.show()
        stageUI.root.animShowScreen(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addImgPanel()
                addBtnBack()
            }
        }
    }

    private fun AdvancedStage.addImgPanel() {
        addActor(imgPanel)
        imgPanel.setBounds(70f,490f,940f,1188f)
    }

    private fun AdvancedStage.addBtnBack() {
        addActor(btnBack)
        btnBack.setBounds(298f,114f,484f,239f)
        btnBack.setOnClickListener {
            stageUI.root.animHideScreen(TIME_ANIM) {
                game.navigationManager.back()
            }
        }
    }

}