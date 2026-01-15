package com.skyplane.puzzleflight.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.skyplane.puzzleflight.game.LibGDXGame
import com.skyplane.puzzleflight.game.utils.actor.setOnClickListener
import com.skyplane.puzzleflight.game.utils.advanced.AdvancedScreen
import com.skyplane.puzzleflight.game.utils.advanced.AdvancedStage
import com.skyplane.puzzleflight.game.utils.region

class MenuScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val menuImg  = Image(game.allAssets.menu)


    override fun show() {
        setBackgrounds(game.loadAssets.background.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMenu()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addMenu() {
        addActor(menuImg)
        menuImg.setBounds(88f, 403f, 472f, 643f)

        val selections = listOf(
            LevelScreen   ::class.java.name,
            RulesScreen   ::class.java.name,
            SettingsScreen::class.java.name,
        )

        var ny = 804f
        selections.onEach { screenName ->
            addActor(Actor().apply {
                setBounds(100f, ny, 448f, 89f)
                ny -= 23f+89f
                setOnClickListener(game.soundUtil) { navigateGo(screenName) }
            })
        }

        val exit = Actor()
        addActor(exit)
        exit.apply {
            setBounds(100f, 456f, 448f, 89f)
            setOnClickListener(game.soundUtil) { game.navigationManager.exit() }
        }
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun navigateGo(id: String) {
        animHideScreen { game.navigationManager.navigate(id, this::class.java.name) }
    }


}