package com.senqorvia774.lottomatica.game.actors.slotGroup

import com.senqorvia774.lottomatica.util.log

class SlotFillManager(
    private val slotList: List<ASlot>,
    private val slotItemContainer: SlotItemContainer
) {

    private fun fillMix() {
        log("fillMix")

        val combinationMatrix5x3: ICombinationMatrix5x3 = Combination5x3.Mix
        val matrix5x3Handler = Matrix5x3Handler(combinationMatrix5x3.getMatrixAndLog(), slotItemContainer)

        slotList.onEachIndexed { index, slot -> slot.listSlotItemWin = matrix5x3Handler.generateSlot(index) }
    }

    private fun fillWin() {
        log("fillWin")

        val combinationMatrix5x3: ICombinationMatrix5x3 = Combination5x3.Win
        val matrix5x3Handler = Matrix5x3Handler(combinationMatrix5x3.getMatrixAndLog(), slotItemContainer)

        slotList.onEachIndexed { index, slot -> slot.listSlotItemWin = matrix5x3Handler.generateSlot(index) }
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