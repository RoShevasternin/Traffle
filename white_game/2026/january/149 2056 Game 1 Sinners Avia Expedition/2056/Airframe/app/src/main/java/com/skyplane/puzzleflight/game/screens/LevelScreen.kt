package com.skyplane.puzzleflight.game.screens

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.skyplane.puzzleflight.game.LibGDXGame
import com.skyplane.puzzleflight.game.actors.button.AButton
import com.skyplane.puzzleflight.game.utils.actor.setBounds
import com.skyplane.puzzleflight.game.utils.actor.setOnClickListener
import com.skyplane.puzzleflight.game.utils.advanced.AdvancedScreen
import com.skyplane.puzzleflight.game.utils.advanced.AdvancedStage
import com.skyplane.puzzleflight.game.utils.region

class LevelScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        var PUZZLE_INDEX = 0
    }

    private val puzzleImg = Image(game.allAssets.puzzle)

    override fun show() {
        setBackgrounds(game.loadAssets.background.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addPuzzles()
        addMenu()
        addBtns()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addPuzzles() {
        addActor(puzzleImg)
        puzzleImg.setBounds(41f, 142f, 559f, 1124f)
    }

    private fun AdvancedStage.addMenu() {
        val back = AButton(this@LevelScreen, AButton.Static.Type.MENU)
        addActor(back)
        back.apply {
            setBounds(249f, 531f, 150f, 150f)
            setOnClickListener(game.soundUtil) { animHideScreen { game.navigationManager.back() } }
        }
    }

    private fun AdvancedStage.addBtns() {
        listOf(
            Vector2(105f, 875f),
            Vector2(41f, 631f),
            Vector2(41f, 386f),
            Vector2(101f, 142f),

            Vector2(349f, 875f),
            Vector2(405f, 631f),
            Vector2(405f, 386f),
            Vector2(345f, 142f),
        ).onEachIndexed { index, pos ->
            val a = Actor()
            addActor(a)
            a.setBounds(pos, Vector2(194f, 194f))

            a.setOnClickListener(game.soundUtil) {
                PUZZLE_INDEX = index

                animHideScreen {
                    game.navigationManager.navigate(PuzzleScreen::class.java.name, LevelScreen::class.java.name)
                }
            }
        }
    }


}