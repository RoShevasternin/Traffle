package com.slotscity.official.game.actors.carnaval_cat.slot5x3

import com.slotscity.official.game.actors.slots.FillStrategy
import com.slotscity.official.util.log

class SlotFillManager(
    private val slotList: List<Slot>,
    private val glowList: List<Glow>,
    private val slotItemContainer: SlotItemContainer
) {

    private fun fillMix() {
        log("fillMix")

        val combinationMatrix5x3: ICombinationMatrix5x3 = Combination5x3.Mix //if (Random().nextBoolean()) Combination5x3.Mix else Combination5x3.MixWithWild
        val matrix5x3Handler = Matrix5x3Handler(combinationMatrix5x3.getMatrixAndLog(), slotItemContainer)

        slotList.onEachIndexed { index, slot -> slot.listSlotItemWin = matrix5x3Handler.generateSlot(index) }
    }

    private fun fillWin() {
        log("fillWin")

        val combinationMatrix5x3: ICombinationMatrix5x3 = Combination5x3.Win

//            listOf<CombinationMatrix5x3>(
//            Combination5x3.WinMonochrome,
//            Combination5x3.WinMonochromeWithWild,
//        ).shuffled().first()

        val matrix5x3Handler = Matrix5x3Handler(combinationMatrix5x3.getMatrixAndLog(), slotItemContainer)

        slotList.onEachIndexed { index, slot -> slot.listSlotItemWin = matrix5x3Handler.generateSlot(index) }
        glowList.onEachIndexed { index, glow -> glow.listIndexWin    = matrix5x3Handler.generateGlow(index) }
    }

    private fun ICombinationMatrix5x3.getMatrixAndLog(): Matrix5x3 {
        val matrix5x3Index = (0..matrixList.lastIndex).random()
        val matrix5x3      = matrixList[matrix5x3Index]
        log("CombinationMatrix5x3: ${this::class.java.name.substringAfterLast('$')} | ${matrix5x3Index.inc()}")

        return matrix5x3
    }

    fun fill(fillStrategy: FillStrategy) {
        when (fillStrategy) {
            FillStrategy.MIX -> fillMix()
            FillStrategy.WIN -> fillWin()
        }
    }

}