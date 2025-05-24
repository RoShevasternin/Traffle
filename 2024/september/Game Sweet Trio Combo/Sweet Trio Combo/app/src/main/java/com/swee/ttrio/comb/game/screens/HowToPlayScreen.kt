package com.swee.ttrio.comb.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.swee.ttrio.comb.game.LibGDXGame
import com.swee.ttrio.comb.game.utils.TIME_ANIM
import com.swee.ttrio.comb.game.utils.actor.animHide
import com.swee.ttrio.comb.game.utils.actor.animShow
import com.swee.ttrio.comb.game.utils.actor.setOnClickListener
import com.swee.ttrio.comb.game.utils.advanced.AdvancedScreen
import com.swee.ttrio.comb.game.utils.advanced.AdvancedStage
import com.swee.ttrio.comb.game.utils.region
import com.swee.ttrio.comb.game.utils.runGDX
import kotlinx.coroutines.launch

class HowToPlayScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val btnMenu = Actor()
    private val imgHTPW  = Image(game.all.HOW_TO_PLAY_WELCOME)
    private val imgHTP   = Image(game.all.HTP).apply { setSize(360f, 1754f) }
    private val scroll   = ScrollPane(imgHTP)

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.all.BACKGROUND_3.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addImgHTP()
                addMenu()
            }
        }
    }

    private fun AdvancedStage.addMenu() {
        addActor(btnMenu)
        btnMenu.apply {
            setBounds(63f,745f,47f,61f)
            setOnClickListener(game.soundUtil) {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.back()
                }
            }
        }
    }

    private fun AdvancedStage.addImgHTP() {
        addActor(imgHTPW)
        imgHTPW.setBounds(59f,617f,273f,201f)

        addActor(scroll)
        scroll.setBounds(17f,0f,360f,572f)
    }

}