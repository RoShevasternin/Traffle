package com.gorillaz.puzzlegame.game.actors.slots

import com.gorillaz.puzzlegame.util.log
import kotlin.random.Random

class SlotFillManager(
    private val slotList: List<ASlot>,
    private val glowList: List<AGlow>,
    private val aWinShape: AWinShape,

    private val slotItemContainer: SlotItemContainer
) {

    private fun fillMix() {
        log("fillMix")

        val combinationMatrix5x3: ICombinationMatrix5x3 = if (Random.nextBoolean()) Combination5x3.Mix else Combination5x3.MixWithWild
        val matrix5x3Handler = Matrix5x3Handler(combinationMatrix5x3.getMatrixAndLog(), slotItemContainer)

        slotList.onEachIndexed { index, slot -> slot.listSlotItemWin = matrix5x3Handler.generateSlot(index) }
    }

    private fun fillWin() {
        log("fillWin")

        val combinationMatrix5x3: ICombinationMatrix5x3 = listOf<ICombinationMatrix5x3>(
            Combination5x3.Win,
            Combination5x3.WinWithWild,
        ).shuffled().first()

        val matrix5x3Handler = Matrix5x3Handler(combinationMatrix5x3.getMatrixAndLog(), slotItemContainer)

        slotList.onEachIndexed { index, slot -> slot.listSlotItemWin = matrix5x3Handler.generateSlot(index) }
        glowList.onEachIndexed { index, glow -> glow.listIndexWin = matrix5x3Handler.generateGlow(index) }

        matrix5x3Handler.matrix5x3.resultShape5x3?.let { resultShape ->
            aWinShape.updateResultShape(resultShape)
        }
    }

    private fun ICombinationMatrix5x3.getMatrixAndLog(): Matrix5x3 {
        val matrix5x3Index = (0..matrixList.lastIndex).random()
        val matrix5x3      = matrixList[matrix5x3Index]
        log("CombinationMatrix5x3: ${this::class.java.name.substringAfterLast('$')} | _${matrix5x3Index.inc()} | ${matrix5x3.resultShape5x3}")

        return matrix5x3
    }

    fun fill(fillStrategy: FillStrategy) {
        when (fillStrategy) {
            FillStrategy.MIX -> fillMix()
            FillStrategy.WIN -> fillWin()
        }
    }

}