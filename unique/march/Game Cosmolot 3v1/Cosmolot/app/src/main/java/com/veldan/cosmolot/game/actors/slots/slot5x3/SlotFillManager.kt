package com.veldan.cosmolot.game.actors.slots.slot5x3

import com.veldan.cosmolot.game.actors.slots.FillResult
import com.veldan.cosmolot.game.actors.slots.FillStrategy
import com.veldan.cosmolot.util.log
import java.util.*

class SlotFillManager(val slotList: List<Slot>) {

    var winFillResult: FillResult? = null
        private set



    private fun fillMix() {
        log("fillMix")

        val combinationMatrix5x3: CombinationMatrix5x3 = if (Random().nextBoolean()) Combination5x3.Mix else Combination5x3.MixWithWild

        val index = (0..combinationMatrix5x3.matrixList.lastIndex).shuffled().first()
        val matrix5x3 = combinationMatrix5x3.matrixList[index].initialize()
        combinationMatrix5x3.logMatrix(index.inc())

        slotList.onEachIndexed { index, slot -> slot.slotItemWinList = matrix5x3.generateSlot(index) }
    }

    private fun fillWin() {
        log("fillWin")

        val combinationMatrix5x3 = listOf<CombinationMatrix5x3>(
            Combination5x3.WinMonochrome,
            Combination5x3.WinMonochromeWithWild,
        ).shuffled().first()

        val index = (0..combinationMatrix5x3.matrixList.lastIndex).shuffled().first()
        val matrix5x3 = combinationMatrix5x3.matrixList[index].initialize()
        combinationMatrix5x3.logMatrix(index.inc())

        slotList.onEachIndexed { index, slot -> slot.slotItemWinList = matrix5x3.generateSlot(index) }

        with(matrix5x3) {
            winFillResult = winSlotItemSet?.let { FillResult(it, intersectionList!!) }

            log("""
                
                scheme           = $scheme
                winSet           = $winSlotItemSet
                intersectionList = $intersectionList
                """
            )
        }
    }

    private fun CombinationMatrix5x3.logMatrix(name: Int) {
        val combinationEnumName = when (this) {
            is Combination5x3.Mix                   -> "Mix $name"
            is Combination5x3.MixWithWild           -> "Mix WILD $name"
            is Combination5x3.WinMonochrome         -> "Win MONOCHROME $name"
            is Combination5x3.WinMonochromeWithWild -> "Win MONOCHROME WILD $name"
            is Combination5x3.WinColorful           -> "Win COLOR $name"
            is Combination5x3.WinColorfulWithWild   -> "Win COLOR WILD $name"
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