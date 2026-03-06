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
import com.parrotrun.skydrink.game.utils.region

class MenuScreen(override val game: LibGDXGame) : AdvancedScreen() {

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.assetsAll.B_DEF.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM_SCREEN_ALPHA)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMenu()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addMenu() {
        val menuBar = Image(game.assetsAll.MENU)
        addActor(menuBar)
        menuBar.setBounds(-34f, 78f, 1295f, 1237f)

        val names = listOf(
            GameScreen::class.java.name,
            RulesScreen::class.java.name,
            SettingsScreen::class.java.name,
            "exit",
        )

        var ny = 600f

        names.onEach { sName ->
            val btn = Actor()
            addActor(btn)
            btn.setBounds(700f, ny, 514f, 94f)
            ny -= 38f+94f

            btn.setOnClickListener(game.soundUtil) { navigateGo(sName) }
        }
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun navigateGo(sName: String) {
        stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) {
            if (sName=="exit") game.navigationManager.exit()
            else game.navigationManager.navigate(sName, MenuScreen::class.java.name)
        }
    }


}