package com.greciao.candyrocket.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.greciao.candyrocket.game.LibGDXGame
import com.greciao.candyrocket.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.greciao.candyrocket.game.utils.actor.animHide
import com.greciao.candyrocket.game.utils.actor.animShow
import com.greciao.candyrocket.game.utils.actor.setOnClickListener
import com.greciao.candyrocket.game.utils.advanced.AdvancedScreen
import com.greciao.candyrocket.game.utils.advanced.AdvancedStage
import com.greciao.candyrocket.game.utils.region

class MenuScreen(override val game: LibGDXGame) : AdvancedScreen() {

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.loaderAssets.background.region)
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
        val menuBar = Image(game.allAssets.menu)
        addActor(menuBar)
        menuBar.setBounds(482f, 54f, 336f, 492f)

        val names = listOf(
            GameScreen::class.java.name,
            ShopScreen::class.java.name,
            RulesScreen::class.java.name,
            SettingsScreen::class.java.name,
            "exit",
        )

        var ny = 387f

        names.onEach { sName ->
            val btn = Actor()
            addActor(btn)
            btn.setBounds(505f, ny, 288f, 53f)
            ny -= 18f+53f

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