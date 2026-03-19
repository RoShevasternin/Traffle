package com.veldan.cosmolot.game.actors.slots.slot3x3

import com.veldan.cosmolot.game.actors.slots.FillResult
import com.veldan.cosmolot.game.actors.slots.FillStrategy
import com.veldan.cosmolot.game.actors.slots.slot3x3.Slot
import com.veldan.cosmolot.util.log

class SlotFillManager(val slotList: List<Slot>) {

    var winFillResult: FillResult? = null
        private set



    private fun fillMix() {
        log("fillMix")

        val combinationMatrix3x3: CombinationMatrix3x3 = Combination3x3.Mix

        val index = (0..combinationMatrix3x3.matrixList.lastIndex).shuffled().first()
        val matrix3x3 = combinationMatrix3x3.matrixList[index].initialize()
        combinationMatrix3x3.logMatrix(index.inc())

        slotList.onEachIndexed { index, slot -> slot.slotItemWinList = matrix3x3.generateSlot(index) }
    }

    private fun fillWin() {
        log("fillWin")

        val combinationMatrix3x3 = listOf<CombinationMatrix3x3>(
            Combination3x3.WinMonochrome,
            Combination3x3.WinMonochromeWithWild,
        ).shuffled().first()

        val index = (0..combinationMatrix3x3.matrixList.lastIndex).shuffled().first()
        val matrix3x3 = combinationMatrix3x3.matrixList[index].initialize()
        combinationMatrix3x3.logMatrix(index.inc())

        slotList.onEachIndexed { index, slot -> slot.slotItemWinList = matrix3x3.generateSlot(index) }

        with(matrix3x3) {
            winFillResult = winSlotItemSet?.let { FillResult(it, intersectionList!!) }

            log("""
                
                scheme           = $scheme
                winSet           = $winSlotItemSet
                intersectionList = $intersectionList
                """
            )
        }
    }

    private fun CombinationMatrix3x3.logMatrix(name: Int) {
        val combinationEnumName = when (this) {
            is Combination3x3.Mix                   -> "Mix $name"
            is Combination3x3.WinMonochrome         -> "Win MONOCHROME $name"
            is Combination3x3.WinMonochromeWithWild -> "Win MONOCHROME WILD $name"
            else                                    -> "Noname"
        }
        log(combinationEnumName)
    }



    fun fill(fillStrategy: FillStrategy) {
        winFillResult = null

        when (fillStrategy) {
            FillStrategy.MIX -> fillMix()
            FillStrategy.WIN -> fillWin()
        }
    }

}