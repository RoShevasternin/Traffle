package com.quenloria615.beton.game.actors.roulette

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.quenloria615.beton.game.utils.actor.addAndFillActor
import com.quenloria615.beton.game.utils.actor.animDelay
import com.quenloria615.beton.game.utils.actor.setOnClickListener
import com.quenloria615.beton.game.utils.advanced.AdvancedGroup
import com.quenloria615.beton.game.utils.advanced.AdvancedScreen
import com.quenloria615.beton.game.utils.gdxGame
import com.quenloria615.beton.util.log
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

class ARoulette(override val screen: AdvancedScreen) : AdvancedGroup() {

    private val aRouletteImg = Image(gdxGame.assetsAll.ROULETTE)
    private val aSpinBtn     = Actor()

    var blockResult: (Result) -> Unit = {}

    private var isSpinning = false

    private val listItem = listOf(
        Item(Result._200,      Segment(-15f,          15f)),
        Item(Result._100,      Segment(15 * 1f,       15 + 30f * 1f)),
        Item(Result._200,      Segment(15 + 30 * 1f,  15 + 30f * 2f)),
        Item(Result._100,      Segment(15 + 30 * 2f,  15 + 30f * 3f)),
        Item(Result.TRY_AGAIN, Segment(15 + 30 * 3f,  15 + 30f * 4f)),
        Item(Result._100,      Segment(15 + 30 * 4f,  15 + 30f * 5f)),
        Item(Result._200,      Segment(15 + 30 * 5f,  15 + 30f * 6f)),
        Item(Result._300,      Segment(15 + 30 * 6f,  15 + 30f * 7f)),
        Item(Result._700,      Segment(15 + 30 * 7f,  15 + 30f * 8f)),
        Item(Result.TRY_AGAIN, Segment(15 + 30 * 8f,  15 + 30f * 9f)),
        Item(Result._200,      Segment(15 + 30 * 9f,  15 + 30f * 10f)),
        Item(Result._500,      Segment(15 + 30 * 10f, 15 + 30f * 11f)),

        Item(Result._200,      Segment(15 + 30f * 11f, 360f)),
    )

    override fun addActorsOnGroup() {
        addAndFillActor(aRouletteImg)
        addActor(aSpinBtn)

        aSpinBtn.setBounds(330f, 335f, 561f, 561f)
        aRouletteImg.setOrigin(Align.center)

        aSpinBtn.setOnClickListener {
            if (!isSpinning) {
                spin { winItem ->
                    animDelay(1f) {
                        log("result = ${winItem.result}")
                        blockResult(winItem.result)
                    }
                }
            }
        }
    }

    // Logic -------------------------------------------------------------------------

    fun spin(blockWin: (winItem: Item) -> Unit) {
        if (isSpinning) return

        isSpinning = true

        // Генеруємо випадковий кут обертання: від 720° до 1500°
        val randomRotation = (720..1500).random().toFloat()

        aRouletteImg.addAction(
            Actions.sequence(
                Actions.rotateBy(randomRotation, (3..5).random().toFloat(), Interpolation.fastSlow),
                Actions.run {
                    val degree = (aRouletteImg.rotation.roundToInt().absoluteValue) % 360f

                    calculateWinningSegment(degree).also { winItem ->
                        isSpinning = false
                        blockWin(winItem) }
                }
            )
        )
    }

    private fun calculateWinningSegment(degree: Float): Item {
        return listItem.firstOrNull { degree in (it.segment.startAngle..it.segment.endAngle) } ?: listItem.first()
    }

    data class Item(val result: Result, val segment: Segment)

    data class Segment(val startAngle: Float, val endAngle: Float)

    enum class Result(val sum: Int) {
        _100(100), _200(200), _300(300), _500(500), _700(700), TRY_AGAIN(0)
    }

}