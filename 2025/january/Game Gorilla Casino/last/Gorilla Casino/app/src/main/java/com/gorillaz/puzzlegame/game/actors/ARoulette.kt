package com.gorillaz.puzzlegame.game.actors

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.gorillaz.puzzlegame.game.utils.actor.animDelay
import com.gorillaz.puzzlegame.game.utils.actor.disable
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedGroup
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedScreen
import com.gorillaz.puzzlegame.game.utils.gdxGame
import com.gorillaz.puzzlegame.util.log
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

class ARoulette(override val screen: AdvancedScreen): AdvancedGroup() {

    private val listItem = listOf<Item>(
        Item(1000,   WinType.GOLD, Segment(45f * 0f, 45f * 1f)),
        Item(10,     WinType.GEMS, Segment(45f * 1f, 45f * 2f)),
        Item(10,     WinType.GEMS, Segment(45f * 2f, 45f * 3f)),
        Item(1000,   WinType.GOLD, Segment(45f * 3f, 45f * 4f)),
        Item(1000,   WinType.GEMS, Segment(45f * 4f, 45f * 5f)),
        Item(10_000, WinType.GOLD, Segment(45f * 5f, 45f * 6f)),
        Item(100,    WinType.GEMS, Segment(45f * 6f, 45f * 7f)),
        Item(10,     WinType.GEMS, Segment(45f * 7f, 45f * 8f)),
    )

    private val imgRoulette = Image(gdxGame.assetsAll.ROULETTE)

    override fun addActorsOnGroup() {
        addRoulette()
    }

    // Actors ------------------------------------------------------------------------

    private fun addRoulette() {
        disable()
        addAndFillActor(imgRoulette)
        imgRoulette.setOrigin(Align.center)
    }

    // Logic -------------------------------------------------------------------------

    fun spin(blockWin: (winItem: Item) -> Unit) {
        // Генеруємо випадковий кут обертання: від 720° до 1500°
        val randomRotation = -(720..1500).random().toFloat()
        //log("random = $randomRotation")

        gdxGame.soundUtil.apply { play(boom) }
        gdxGame.soundUtil.apply { play(spin) }

        val spinTime = (4..7).random().toFloat()
        this.animDelay(spinTime * 0.6f) { gdxGame.soundUtil.spin.sound.pause() }

        imgRoulette.addAction(
            Actions.sequence(
                Actions.rotateBy(randomRotation, spinTime, Interpolation.pow5Out),
                Actions.run {
                    val degree = (imgRoulette.rotation.roundToInt().absoluteValue + 206f) % 360f
                    //log("angle = ${imgRoulette.rotation} ||| $degree")

                    calculateWinningSegment(degree).also { winItem ->
                        //log("result = $winItem")

                        gdxGame.soundUtil.apply { play(win_roulette) }
                        blockWin(winItem)
                    }
                }
            )
        )
    }

    private fun calculateWinningSegment(degree: Float): Item {
        return listItem.firstOrNull { degree in (it.segment.startAngle..it.segment.endAngle) } ?: listItem.first()
    }

    data class Item(val count: Int, val type: WinType, val segment: Segment)

    data class Segment(val startAngle: Float, val endAngle: Float)

    enum class WinType {
        GOLD, GEMS
    }
}