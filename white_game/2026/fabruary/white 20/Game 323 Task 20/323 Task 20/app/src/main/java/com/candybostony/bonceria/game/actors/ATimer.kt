package com.candybostony.bonceria.game.actors

import android.annotation.SuppressLint
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.candybostony.bonceria.game.utils.GameColor
import com.candybostony.bonceria.game.utils.actor.addAndFillActor
import com.candybostony.bonceria.game.utils.advanced.AdvancedGroup
import com.candybostony.bonceria.game.utils.advanced.AdvancedScreen

class ATimer(override val screen: AdvancedScreen, font: BitmapFont): AdvancedGroup() {

    private val labelStyle = Label.LabelStyle(font, GameColor.green)
    private val label      = Label("00:00", labelStyle)

    private var timeSeconds = 0
    private var isPaused    = true
    private var timerBuffer = 0f

    // Блок, який спрацює, коли час вийде
    var finishBlock: () -> Unit = {}

    override fun addActorsOnGroup() {
        addAndFillActor(label)
        label.setAlignment(Align.center)
    }

    override fun act(delta: Float) {
        super.act(delta)
        if (isPaused) return

        timerBuffer += delta
        if (timerBuffer >= 1f) {
            timerBuffer -= 1f
            timeSeconds--

            if (timeSeconds <= 0) {
                timeSeconds = 0
                isPaused = true
                finishBlock()
            }
            updateLabel()
        }
    }

    @SuppressLint("DefaultLocale")
    private fun updateLabel() {
        val min = timeSeconds / 60
        val sec = timeSeconds % 60
        // Форматуємо в 00:00
        label.setText(String.format("%02d:%02d", min, sec))
    }

    // --- Публічні методи для керування ---

    fun startTimer(totalSeconds: Int) {
        this.timeSeconds = totalSeconds
        this.isPaused = false
        updateLabel()
    }

    fun pause() { isPaused = true }
    fun resume() { isPaused = false }

    fun setTime(min: Int, sec: Int) {
        this.timeSeconds = (min * 60) + sec
        updateLabel()
    }
}