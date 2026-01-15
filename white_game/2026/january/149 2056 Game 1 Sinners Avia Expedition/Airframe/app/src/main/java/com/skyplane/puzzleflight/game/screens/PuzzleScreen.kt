package com.skyplane.puzzleflight.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.skyplane.puzzleflight.game.LibGDXGame
import com.skyplane.puzzleflight.game.actors.ATimerGroup
import com.skyplane.puzzleflight.game.actors.button.AButton
import com.skyplane.puzzleflight.game.actors.puzzle.APuzzlePanel
import com.skyplane.puzzleflight.game.utils.actor.setOnClickListener
import com.skyplane.puzzleflight.game.utils.advanced.AdvancedScreen
import com.skyplane.puzzleflight.game.utils.advanced.AdvancedStage
import com.skyplane.puzzleflight.game.utils.puzzle.Puzzles
import com.skyplane.puzzleflight.game.utils.region

class PuzzleScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val puzzleRegion = game.allAssets.listPuzzle[LevelScreen.PUZZLE_INDEX]

    private val panelImg     = Image(game.allAssets.panel)
    private val timer        = ATimerGroup(this)
    private val puzzlesPanel = APuzzlePanel(this, puzzleRegion)

    override fun show() {
        setBackgrounds(game.loadAssets.background.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addPanel()
        addMenuBtn()
        addPuzzlePanel()
        addPuzzleImg()
        addTimer()

        timer.startTimer { animHideScreen {
            ResultScreen.IS_WIN = false
            game.navigationManager.navigate(ResultScreen::class.java.name)
        } }
    }

    override fun dispose() {
        Puzzles.dispose()
        super.dispose()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addPanel() {
        addActor(panelImg)
        panelImg.setBounds(41f, 316f, 567f, 601f)
    }

    private fun AdvancedStage.addMenuBtn() {
        val back = AButton(this@PuzzleScreen, AButton.Static.Type.MENU)
        addActor(back)
        back.setBounds(249f, 81f, 150f, 150f)
        back.setOnClickListener(game.soundUtil) { animHideScreen { game.navigationManager.back() } }
    }


    private fun AdvancedStage.addPuzzlePanel() {
        addActor(puzzlesPanel)
        puzzlesPanel.setBounds(60f, 353f, 528f, 528f)

        puzzlesPanel.finishBlock = {
            animHideScreen {
                ResultScreen.IS_WIN = true
                game.navigationManager.navigate(ResultScreen::class.java.name)
            }
        }
    }

    private fun AdvancedStage.addPuzzleImg() {
        val maska = Image(puzzleRegion)
        addActor(maska)
        maska.setBounds(227f, 979f, 194f, 194f)
    }

    private fun AdvancedStage.addTimer() {
        val tim = Image(game.allAssets.timer)
        addActor(tim)
        tim.setBounds(205f, 1237f, 239f, 92f)

        addActor(timer)
        timer.setBounds(314f, 1249f, 104f, 55f)
    }

}