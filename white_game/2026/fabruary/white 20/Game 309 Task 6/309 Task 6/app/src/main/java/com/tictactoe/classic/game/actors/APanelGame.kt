package com.tictactoe.classic.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.tictactoe.classic.game.utils.actor.addActors
import com.tictactoe.classic.game.utils.actor.addAndFillActor
import com.tictactoe.classic.game.utils.actor.setOnClickListener
import com.tictactoe.classic.game.utils.advanced.AdvancedGroup
import com.tictactoe.classic.game.utils.advanced.AdvancedScreen
import com.tictactoe.classic.game.utils.gdxGame
import com.tictactoe.classic.game.utils.runGDX
import com.tictactoe.classic.util.log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class APanelGame(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    var blockWin  = {}
    var blockLose = {}

    // ----------------------------------------------------------------
    // Actors
    // ----------------------------------------------------------------

    private val aYouImg      = Image(gdxGame.assetsAll.YOU)
    private val aOpponentImg = Image(gdxGame.assetsAll.OPPONENT)

    private val listCellImg  = List(9) { Image() }

    // ----------------------------------------------------------------
    // State
    // ----------------------------------------------------------------

    val turnFlow = MutableStateFlow(Turn.YOU)

    // ----------------------------------------------------------------
    // Game State
    // ----------------------------------------------------------------

    private val board = Array(3) { Array(3) { Cell.EMPTY } }

    // ----------------------------------------------------------------
    // Init
    // ----------------------------------------------------------------

    override fun addActorsOnGroup() {
        addAndFillActor(Image(gdxGame.assetsAll.GRID))

        addYouImg()
        addOpponentImg()
        addListCellImg()

        observeTurn()
    }

    // ----------------------------------------------------------------
    // Private UI Builders
    // ----------------------------------------------------------------

    private fun addYouImg() {
        addActor(aYouImg)
        aYouImg.setBounds(0f, 1192f, 306f, 173f)
    }

    private fun addOpponentImg() {
        addActor(aOpponentImg)
        aOpponentImg.setBounds(473f, 1192f, 409f, 173f)
    }

    private fun addListCellImg() {
        var nx = 184f
        var ny = 670f

        listCellImg.forEachIndexed { index, img ->

            val row = index / 3
            val col = index % 3

            addActor(img)
            img.setBounds(nx, ny, 160f, 160f)

            nx += 18 + 160
            if (index.inc() % 3 == 0) {
                nx = 184f
                ny -= 18 + 160
            }

            img.setOnClickListener {
                gdxGame.soundUtil.apply { play(bam) }

                if (turnFlow.value != Turn.YOU) return@setOnClickListener
                if (board[row][col] != Cell.EMPTY) return@setOnClickListener

                makeMove(row, col, Cell.X)

                if (checkWin(Cell.X)) {
                    log("USER WIN")
                    blockWin()
                    return@setOnClickListener
                }

                if (isBoardFull()) {
                    log("DRAW")
                    blockLose()
                    return@setOnClickListener
                }

                turnFlow.value = Turn.OPPONENT
                makeBotMove()
            }
        }
    }

    // ----------------------------------------------------------------
    // Logic
    // ----------------------------------------------------------------

    private fun observeTurn() {
        coroutine?.launch {
            turnFlow.collect { turn ->
                runGDX { updateTurnUI(turn) }
            }
        }
    }

    private fun updateTurnUI(turn: Turn) {
        when (turn) {
            Turn.YOU -> {
                aYouImg.color.a = 1f
                aOpponentImg.color.a = 0.7f
            }
            Turn.OPPONENT -> {
                aYouImg.color.a = 0.7f
                aOpponentImg.color.a = 1f
            }
        }
    }

    private fun makeMove(row: Int, col: Int, cell: Cell) {

        board[row][col] = cell

        val texture = when (cell) {
            Cell.X -> gdxGame.assetsAll.X
            Cell.O -> gdxGame.assetsAll.O
            else -> return
        }

        val index = row * 3 + col
        listCellImg[index].drawable = TextureRegionDrawable(texture)
    }

    private fun makeBotMove() {

        val emptyCells = mutableListOf<Pair<Int, Int>>()

        for (r in 0..2) {
            for (c in 0..2) {
                if (board[r][c] == Cell.EMPTY) {
                    emptyCells.add(r to c)
                }
            }
        }

        if (emptyCells.isEmpty()) return

        val (row, col) = emptyCells.random()

        makeMove(row, col, Cell.O)

        if (checkWin(Cell.O)) {
            log("BOT WIN")
            blockLose()
            return
        }

        if (isBoardFull()) {
            log("DRAW")
            blockWin()
            return
        }

        turnFlow.value = Turn.YOU
    }

    private fun checkWin(cell: Cell): Boolean {

        // Rows
        for (i in 0..2)
            if (board[i][0] == cell &&
                board[i][1] == cell &&
                board[i][2] == cell) return true

        // Columns
        for (i in 0..2)
            if (board[0][i] == cell &&
                board[1][i] == cell &&
                board[2][i] == cell) return true

        // Diagonals
        if (board[0][0] == cell &&
            board[1][1] == cell &&
            board[2][2] == cell) return true

        if (board[0][2] == cell &&
            board[1][1] == cell &&
            board[2][0] == cell) return true

        return false
    }

    private fun isBoardFull(): Boolean {
        for (r in 0..2)
            for (c in 0..2)
                if (board[r][c] == Cell.EMPTY)
                    return false

        return true
    }

}

// ----------------------------------------------------------------
// Classes
// ----------------------------------------------------------------

enum class Turn {
    YOU,
    OPPONENT
}

enum class Cell {
    EMPTY,
    X,
    O
}