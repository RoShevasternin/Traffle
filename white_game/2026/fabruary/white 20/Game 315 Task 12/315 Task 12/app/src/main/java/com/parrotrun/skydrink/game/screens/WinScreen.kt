package com.parrotrun.skydrink.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.parrotrun.skydrink.game.LibGDXGame
import com.parrotrun.skydrink.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.parrotrun.skydrink.game.utils.actor.animHide
import com.parrotrun.skydrink.game.utils.actor.animShow
import com.parrotrun.skydrink.game.utils.actor.setOnClickListener
import com.parrotrun.skydrink.game.utils.advanced.AdvancedScreen
import com.parrotrun.skydrink.game.utils.advanced.AdvancedStage
import com.parrotrun.skydrink.game.utils.gdxGame
import com.parrotrun.skydrink.game.utils.region

class WinScreen(override val game: LibGDXGame) : AdvancedScreen() {

    override fun show() {
        gdxGame.soundUtil.apply { play(win) }
        stageUI.root.animHide()
        setBackBackground(game.assetsAll.B_WIN.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM_SCREEN_ALPHA)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        val imgPanel = Image(gdxGame.assetsAll.RESULT_PANEL)
        val aMenu    = Actor()
        val aNext    = Actor()

        addActors(imgPanel, aMenu, aNext)

        imgPanel.setBounds(770f, 110f, 381f, 91f)
        aMenu.setBounds(770f, 110f, 91f, 91f)
        aNext.setBounds(903f, 117f, 248f, 75f)

        aMenu.setOnClickListener(gdxGame.soundUtil) {
            gdxGame.navigationManager.back()
        }
        aNext.setOnClickListener(gdxGame.soundUtil) {
            gdxGame.navigationManager.navigate(GameScreen::class.java.name)
        }
    }

}