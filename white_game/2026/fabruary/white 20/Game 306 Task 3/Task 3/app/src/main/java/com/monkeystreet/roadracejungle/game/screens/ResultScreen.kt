package com.monkeystreet.roadracejungle.game.screens

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.monkeystreet.roadracejungle.game.actors.ATmpGroup
import com.monkeystreet.roadracejungle.game.actors.button.AButton
import com.monkeystreet.roadracejungle.game.utils.Block
import com.monkeystreet.roadracejungle.game.utils.HEIGHT_UI
import com.monkeystreet.roadracejungle.game.utils.TIME_ANIM_SCREEN
import com.monkeystreet.roadracejungle.game.utils.WIDTH_UI
import com.monkeystreet.roadracejungle.game.utils.actor.HAlign
import com.monkeystreet.roadracejungle.game.utils.actor.VAlign
import com.monkeystreet.roadracejungle.game.utils.actor.addActorAligned
import com.monkeystreet.roadracejungle.game.utils.actor.addActors
import com.monkeystreet.roadracejungle.game.utils.actor.animDelay
import com.monkeystreet.roadracejungle.game.utils.actor.animHide
import com.monkeystreet.roadracejungle.game.utils.actor.animShow
import com.monkeystreet.roadracejungle.game.utils.advanced.AdvancedScreen
import com.monkeystreet.roadracejungle.game.utils.gdxGame

var GDX_IS_WIN = true

class ResultScreen: AdvancedScreen() {

    private val aPanelGroup = ATmpGroup(this)

    override fun show() {
        gdxGame.soundUtil.apply { if (GDX_IS_WIN) {
            gdxGame.ds_Record.update { it + 1 }
            play(game_win_jungle_monk)
        } else play(game_fail_jungle) }

        setBackBackground(
            if (GDX_IS_WIN) gdxGame.assetsAll.WIN
            else gdxGame.assetsAll.LOSE
        )
        super.show()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        addPanelGroup()

        animShow()
    }

    override fun animHide(blockEnd: Block) {
        stageUI.root.animHide(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    override fun animShow(blockEnd: Block) {
        //stageUI.root.children.onEach { it.clearActions() }

        stageUI.root.animShow(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    // Actors ------------------------------------------------------------------------

    private fun Group.addPanelGroup() {
        aPanelGroup.setSize(WIDTH_UI, HEIGHT_UI)
        addActorAligned(aPanelGroup, HAlign.CENTER, VAlign.CENTER)

        aPanelGroup.apply {
            addRulesImg()
        }
    }

    private fun Group.addRulesImg() {
        val r = AButton(this@ResultScreen, AButton.Type.Restart)
        val m = AButton(this@ResultScreen, AButton.Type.ToMenu)

        addActors(r, m)

        r.setBounds(152f, 223f, 380f, 145f)
        m.setBounds(547f, 223f, 380f, 145f)

        r.setOnClickListener { this@ResultScreen.animHide { gdxGame.navigationManager.navigate(GameScreen::class.java.name) } }
        m.setOnClickListener { this@ResultScreen.animHide { gdxGame.navigationManager.navigate(MenuScreen::class.java.name) } }
    }

}