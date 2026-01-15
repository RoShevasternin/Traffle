package com.skycoin.flight.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.skycoin.flight.game.LibGDXGame
import com.skycoin.flight.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.skycoin.flight.game.utils.actor.animHide
import com.skycoin.flight.game.utils.actor.animShow
import com.skycoin.flight.game.utils.actor.setOnClickListener
import com.skycoin.flight.game.utils.advanced.AdvancedScreen
import com.skycoin.flight.game.utils.advanced.AdvancedStage
import com.skycoin.flight.game.utils.region

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
        menuBar.setBounds(89f, 357f, 470f, 689f)

        val names = listOf(
            GameScreen::class.java.name,
            ShopScreen::class.java.name,
            RulesScreen::class.java.name,
            SettingsScreen::class.java.name,
            "exit",
        )

        var ny = 805f

        names.onEach { sName ->
            val btn = Actor()
            addActor(btn)
            btn.setBounds(100f, ny, 448f, 89f)
            ny -= 9f+89f

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