package com.pixe.lkicko.perlin.game.actors

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Array
import com.pixe.lkicko.perlin.game.utils.advanced.AdvancedGroup
import com.pixe.lkicko.perlin.game.utils.advanced.AdvancedScreen
import com.pixe.lkicko.perlin.game.utils.disable
import com.pixe.lkicko.perlin.game.utils.enable

class APuzzleGroup(
    override val screen: AdvancedScreen,
    private val texture: Texture,  // Текстура для пазлів
    private val rows: Int = 10,     // Кількість рядків пазлів
    private val cols: Int = 6     // Кількість стовпців пазлів
) : AdvancedGroup() {

    private val puzzlePieces = Array<PuzzlePiece>()

    var blockPuzzleCompleted: () -> Unit = {}

    override fun addActorsOnGroup() {
        // Розділяємо текстуру на регіони пазлів
        val pieceWidth  = texture.width / cols
        val pieceHeight = texture.height / rows

        val regions     = TextureRegion.split(texture, pieceWidth, pieceHeight)

        // Створюємо об'єкти пазлів
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                val region      = regions[row][col]
                val puzzlePiece = PuzzlePiece(region)

                // Встановлюємо розмір та позицію для кожного шматка пазлу
                puzzlePiece.setBounds(col * 136f, (rows - row - 1) * 136f, 136f, 136f)
                puzzlePiece.setOrigin(136f / 2f, 136f / 2f)

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
                    screen.game.soundUtil.apply { play(soft_click) }
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

    fun useX3() {
        puzzlePieces.filter { it.rotation != 0f }.take(3).onEach {
            it.rotatePieceTo0()
        }
    }




}