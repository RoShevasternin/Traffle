package com.skyplane.puzzleflight.game.utils.puzzle

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Disposable
import com.skyplane.puzzleflight.game.actors.puzzle.APuzzle
import com.skyplane.puzzleflight.game.utils.advanced.AdvancedScreen
import com.skyplane.puzzleflight.util.log
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

object Puzzles : Disposable {

    private val puzzles = mutableMapOf<APuzzle, PuzzleState>()
    var doAfterAssembledPuzzles: () -> Unit = { }

    // =======================================================================================
    //                                  Override
    // =======================================================================================
    override fun dispose() {
        puzzles.clear()
    }

    // =======================================================================================
    //                                  Private
    // =======================================================================================
    private fun determinePuzzleState(angle: Float): PuzzleState {
        return if (angle.roundToInt().absoluteValue in listOf(0, 360)) PuzzleState.ASSEMBLED else PuzzleState.NOT_ASSEMBLED
    }

    private fun checkAssembledPuzzles() {
        if (puzzles.values.all { it == PuzzleState.ASSEMBLED }) {
            doAfterAssembledPuzzles()
            log("HHHH")
        }
    }

    // =======================================================================================
    //                                  Public
    // =======================================================================================
    fun generatePuzzles(screen: AdvancedScreen, region: TextureRegion, numberPuzzles: NumberPuzzles): List<APuzzle> = with(region) {
        val puzzleList = mutableListOf<APuzzle>()
        val tileWidth = regionWidth / numberPuzzles.horizontalNumberPuzzles
        val tileHeight = regionHeight / numberPuzzles.verticalNumberPuzzles
        split(tileWidth, tileHeight).flatten().onEach { puzzleList.add(APuzzle(screen, it)) }

        puzzleList
    }

    fun add(puzzle: APuzzle) {
        puzzles[puzzle] = determinePuzzleState(puzzle.rotation)

        puzzle.doAfterRotate = { angle ->
            puzzles[puzzle] = determinePuzzleState(angle)
            checkAssembledPuzzles()
        }
    }

}