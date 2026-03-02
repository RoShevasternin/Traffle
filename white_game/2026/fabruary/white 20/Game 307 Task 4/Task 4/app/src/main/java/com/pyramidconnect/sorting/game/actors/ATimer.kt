package com.pyramidconnect.sorting.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.pyramidconnect.sorting.game.utils.GameColor
import com.pyramidconnect.sorting.game.utils.actor.HAlign
import com.pyramidconnect.sorting.game.utils.actor.VAlign
import com.pyramidconnect.sorting.game.utils.actor.addActorAligned
import com.pyramidconnect.sorting.game.utils.actor.addActors
import com.pyramidconnect.sorting.game.utils.actor.addAndFillActor
import com.pyramidconnect.sorting.game.utils.actor.addAndFillActors
import com.pyramidconnect.sorting.game.utils.actor.disable
import com.pyramidconnect.sorting.game.utils.advanced.AdvancedGroup
import com.pyramidconnect.sorting.game.utils.advanced.AdvancedScreen
import com.pyramidconnect.sorting.game.utils.font.FontParameter
import com.pyramidconnect.sorting.game.utils.gdxGame

class ATimer(
    override val screen: AdvancedScreen,
    private val initialSeconds: Int = 180, // за замовчуванням 3 хвилини
): AdvancedGroup() {

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val fontTitle = screen.fontGenerator_Regular.generateFont(parameter.setSize(44))

    private val lblTitle = Label("", Label.LabelStyle(fontTitle, Color.WHITE))

    private var timeSeconds = initialSeconds.toFloat()
    private var isPaused    = false

    // Лямбда, яка виконається, коли час вийде
    var finishBlock: () -> Unit = {}

    override fun addActorsOnGroup() {
        addAndFillActor(lblTitle)
        lblTitle.setAlignment(Align.center)
        updateLabel()
    }

    override fun act(delta: Float) {
        super.act(delta)
        if (isPaused || timeSeconds <= 0) return

        timeSeconds -= delta

        if (timeSeconds <= 0f) {
            timeSeconds = 0f
            updateLabel()
            finishBlock()
        } else {
            updateLabel()
        }
    }

    private fun updateLabel() {
        val totalSeconds = timeSeconds.toInt()
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60

        // %02d означає: ціле число, мінімум 2 символи, заповнити нулями якщо треба
        lblTitle.setText(String.format("%02d:%02d", minutes, seconds))
    }

    fun pause() { isPaused = true }
    fun resume() { isPaused = false }

    fun setTime(seconds: Int) {
        timeSeconds = seconds.toFloat()
        updateLabel()
    }
}