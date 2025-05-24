package com.slotscity.official.game.actors.slots.slot6x5

import com.slotscity.official.game.actors.slots.FillStrategy
import com.slotscity.official.util.log

class SlotFillManager(
    private val slotList: List<Slot>,
    private val glowList: List<Glow>,
    private val slotItemContainer: SlotItemContainer
) {

    private fun fillMix() {
        log("fillMix")

        val combinationMatrix6x5: ICombinationMatrix6x5 = Combination6x5.Mix //if (Random().nextBoolean()) Combination5x3.Mix else Combination5x3.MixWithWild
        val matrix6x5Handler = Matrix6x5Handler(combinationMatrix6x5.getMatrixAndLog(), slotItemContainer)

        slotList.onEachIndexed { index, slot -> slot.listSlotItemWin = matrix6x5Handler.generateSlot(index) }
    }

    private fun fillWin() {
        log("fillWin")

        val combinationMatrix6x5: ICombinationMatrix6x5 = Combination6x5.Win

//            listOf<CombinationMatrix5x3>(
//            Combination5x3.WinMonochrome,
//            Combination5x3.WinMonochromeWithWild,
//        ).shuffled().first()

        val matrix6x5Handler = Matrix6x5Handler(combinationMatrix6x5.getMatrixAndLog(), slotItemContainer)

        slotList.onEachIndexed { index, slot -> slot.listSlotItemWin = matrix6x5Handler.generateSlot(index) }
        glowList.onEachIndexed { index, glow -> glow.listIndexWin    = matrix6x5Handler.generateGlow(index) }
    }

    private fun ICombinationMatrix6x5.getMatrixAndLog(): Matrix6x5 {
        val matrix6x5Index = (0..matrixList.lastIndex).random()
        val matrix6x5      = matrixList[matrix6x5Index]
        log("CombinationMatrix6x5: ${this::class.java.name.substringAfterLast('$')} | ${matrix6x5Index.inc()}")

        return matrix6x5
    }

    fun fill(fillStrategy: FillStrategy) {
        when (fillStrategy) {
            FillStrategy.MIX -> fillMix()
            FillStrategy.WIN -> fillWin()
        }
    }

}