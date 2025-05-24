package com.viade.bepuzzle.game.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Array
import com.viade.bepuzzle.game.actors.main.AMainSettings
import com.viade.bepuzzle.game.utils.actor.disable
import com.viade.bepuzzle.game.utils.actor.enable
import com.viade.bepuzzle.game.utils.advanced.AdvancedGroup
import com.viade.bepuzzle.game.utils.advanced.AdvancedScreen

class APuzzleGroup(
    override val screen: AdvancedScreen,
    private val texture: Texture,  // Текстура для пазлів
    diff: Diff,
) : AdvancedGroup() {

    enum class Diff(val rows: Int, val cols: Int) {
        Easy(7,4),
        Medium(14,8),
        Hard(17,10)
    }

    private val rows: Int = diff.rows // Кількість рядків пазлів
    private val cols: Int = diff.cols // Кількість стовпців пазлів

    private val puzzlePieces = Array<PuzzlePiece>()

    var blockPuzzleCompleted: () -> Unit = {}

    override fun addActorsOnGroup() {
        // Розділяємо текстуру на регіони пазлів
        val tPieceWidth  = texture.width / cols
        val tPieceHeight = texture.height / rows

        val regions = TextureRegion.split(texture, tPieceWidth, tPieceHeight)

        val pieceWidth  = width / cols
        val pieceHeight = height / rows

        // Створюємо об'єкти пазлів
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                val region      = regions[row][col]
                val puzzlePiece = PuzzlePiece(region)

                // Встановлюємо розмір та позицію для кожного шматка пазлу
                puzzlePiece.setBounds(col * pieceWidth, (rows - row - 1) * pieceHeight, pieceWidth, pieceHeight)
                puzzlePiece.setOrigin(pieceWidth / 2f, pieceHeight / 2f)

                // Додаємо пазл в групу
                puzzlePieces.add(puzzlePiece)
                addActor(puzzlePiece)
            }
        }
    }

    // Перевіряє, чи всі шматки повернуті на 0 градусів
    private fun isPuzzleSolved(): Boolean {
        return puzzlePieces.all { it.rotation == 0f }
    }

    // Клас для шматків пазлу
    inner class PuzzlePiece(region: TextureRegion) : Image(region) {

        init {
            rotation = listOf(0f, 90f, 180f, 270f).random()

            addListener(object : InputListener() {
                override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                    screen.game.soundUtil.apply { play(puzzle_click) }
                    if (AMainSettings.IS_VIBRO) Gdx.input.vibrate(50)
                    rotatePiece()
                    return true
                }
            })
        }

        // Обертає шматок пазлу на 90 градусів
        private fun rotatePiece() {
            disable()

            addAction(Actions.sequence(
                Actions.rotateBy(90f, 0.15f, Interpolation.fade),
                Actions.run {
                    rotation = Math.round(rotation) % 360.toFloat()

                    enable()
                    if (isPuzzleSolved()) {
                        blockPuzzleCompleted()  // Приклад: виклик повідомлення про завершення пазлу
                    }
                }
            ))
        }

        fun rotatePieceTo0() {
            disable()

            addAction(Actions.sequence(
                Actions.rotateTo(0f, 0.15f, Interpolation.fade),
                Actions.run {
                    enable()
                    if (isPuzzleSolved()) {
                        blockPuzzleCompleted()  // Приклад: виклик повідомлення про завершення пазлу
                    }
                }
            ))
        }
    }

    // Logic ----------------------------------------------------------------

    fun useX2() {
        puzzlePieces.filter { it.rotation != 0f }.take(2).onEach {
            it.rotatePieceTo0()
        }
    }

    fun useX5() {
        puzzlePieces.filter { it.rotation != 0f }.take(5).onEach {
            it.rotatePieceTo0()
        }
    }




}