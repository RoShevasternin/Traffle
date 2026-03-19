package com.veldan.cosmolot.game.actors.slots.slot6x4

import com.veldan.cosmolot.game.actors.slots.FillResult
import com.veldan.cosmolot.game.actors.slots.FillStrategy
import com.veldan.cosmolot.util.log
import java.util.*

class SlotFillManager(val slotList: List<Slot>) {

    var winFillResult: FillResult? = null
        private set



    private fun fillMix() {
        log("fillMix")

        val combinationMatrix6x4: CombinationMatrix6x4 = if (Random().nextBoolean()) Combination6x4.Mix else Combination6x4.MixWithWild

        val index = (0..combinationMatrix6x4.matrixList.lastIndex).shuffled().first()
        val matrix6x4 = combinationMatrix6x4.matrixList[index].initialize()
        combinationMatrix6x4.logMatrix(index.inc())

        slotList.onEachIndexed { index, slot -> slot.slotItemWinList = matrix6x4.generateSlot(index) }
    }

    private fun fillWin() {
        log("fillWin")

        val combinationMatrix6x4 = listOf<CombinationMatrix6x4>(
            Combination6x4.WinMonochrome,
            Combination6x4.WinMonochromeWithWild,
        ).shuffled().first()

        val index = (0..combinationMatrix6x4.matrixList.lastIndex).shuffled().first()
        val matrix6x4 = combinationMatrix6x4.matrixList[index].initialize()
        combinationMatrix6x4.logMatrix(index.inc())

        slotList.onEachIndexed { index, slot -> slot.slotItemWinList = matrix6x4.generateSlot(index) }

        with(matrix6x4) {
            winFillResult = winSlotItemSet?.let { FillResult(it, intersectionList!!) }

            log("""
                
                scheme           = $scheme
                winSet           = $winSlotItemSet
                intersectionList = $intersectionList
                """
            )
        }
    }

    private fun CombinationMatrix6x4.logMatrix(name: Int) {
        val combinationEnumName = when (this) {
            is Combination6x4.Mix                   -> "Mix $name"
            is Combination6x4.MixWithWild           -> "Mix WILD $name"
            is Combination6x4.WinMonochrome         -> "Win MONOCHROME $name"
            is Combination6x4.WinMonochromeWithWild -> "Win MONOCHROME WILD $name"
            is Combination6x4.WinColorful           -> "Win COLOR $name"
            is Combination6x4.WinColorfulWithWild   -> "Win COLOR WILD $name"
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