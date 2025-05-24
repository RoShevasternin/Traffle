package com.pinlq.esst.bloo.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.pinlq.esst.bloo.game.LibGDXGame
import com.pinlq.esst.bloo.game.actors.AButton
import com.pinlq.esst.bloo.game.utils.TIME_ANIM
import com.pinlq.esst.bloo.game.utils.actor.animHideScreen
import com.pinlq.esst.bloo.game.utils.actor.animShowScreen
import com.pinlq.esst.bloo.game.utils.actor.setOnClickListener
import com.pinlq.esst.bloo.game.utils.advanced.AdvancedScreen
import com.pinlq.esst.bloo.game.utils.advanced.AdvancedStage
import com.pinlq.esst.bloo.game.utils.region
import com.pinlq.esst.bloo.game.utils.runGDX
import kotlinx.coroutines.launch

class NumOfPlayersScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        var COUNT_USER = 1
            private set
    }

    private val imgPanel = Image(game.all.numbers)
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
        imgPanel.setBounds(93f,290f,893f,1266f)

        var ny = 1110f

        val listActor = List(4) { Actor() }
        listActor.onEachIndexed { index, actor ->
            addActor(actor)
            actor.setBounds(195f, ny,689f,180f)
            ny -= 60 + 180

            actor.setOnClickListener(game.soundUtil) {
                COUNT_USER = index.inc()
                stageUI.root.animHideScreen(TIME_ANIM) {
                    game.navigationManager.navigate(SelectPlayersScreen::class.java.name, NumOfPlayersScreen::class.java.name)
                }
            }
        }
    }

    private fun AdvancedStage.addBtnBack() {
        addActor(btnBack)
        btnBack.setBounds(298f,21f,484f,239f)
        btnBack.setOnClickListener {
            stageUI.root.animHideScreen(TIME_ANIM) {
                game.navigationManager.back()
            }
        }
    }

}