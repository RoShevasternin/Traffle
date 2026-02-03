package com.dasholy.olympusdash.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.dasholy.olympusdash.game.LibGDXGame
import com.dasholy.olympusdash.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.dasholy.olympusdash.game.utils.actor.animHide
import com.dasholy.olympusdash.game.utils.actor.animShow
import com.dasholy.olympusdash.game.utils.actor.setOnClickListener
import com.dasholy.olympusdash.game.utils.advanced.AdvancedScreen
import com.dasholy.olympusdash.game.utils.advanced.AdvancedStage
import com.dasholy.olympusdash.game.utils.region

class MenuScreen(override val game: LibGDXGame) : AdvancedScreen() {

    override fun show() {
        stageUI.root.animHide()
        setUIBackground(game.gameAssets.mainB.region)
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
        val menuBar = Image(game.gameAssets.menuPA)
        addActor(menuBar)
        menuBar.setBounds(89f, 393f, 488f, 653f)

        val names = listOf(
            GameScreen::class.java.name,
            ShopScreen::class.java.name,
            RulesScreen::class.java.name,
            SettingsScreen::class.java.name,
            "exit",
        )

        var ny = 858f

        names.onEach { sName ->
            val btn = Actor()
            addActor(btn)
            btn.setBounds(201f, ny, 265f, 75f)
            ny -= 75f + 27f

            btn.setOnClickListener(game.soundUtil) { navigateGo(sName) }
        }
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun navigateGo(sName: String) {
        stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) {
            if (sName == "exit") game.navigationManager.exit()
            else game.navigationManager.navigate(sName, MenuScreen::class.java.name)
        }
    }


}