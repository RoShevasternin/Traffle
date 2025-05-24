package com.slotscity.official.game.actors.treasure_snipes.slot5x3

import com.slotscity.official.game.actors.slots.SlotItem

class Matrix5x3Handler(
    private val matrix5x3        : Matrix5x3,
    private val slotItemContainer: SlotItemContainer
) {

    private val resultMatrix5x3 = matrix5x3.resultMatrix5x3

    private val slotA = listOf<Int>(matrix5x3.a1, matrix5x3.a2, matrix5x3.a3)
    private val slotB = listOf<Int>(matrix5x3.b1, matrix5x3.b2, matrix5x3.b3)
    private val slotC = listOf<Int>(matrix5x3.c1, matrix5x3.c2, matrix5x3.c3)
    private val slotD = listOf<Int>(matrix5x3.d1, matrix5x3.d2, matrix5x3.d3)
    private val slotE = listOf<Int>(matrix5x3.e1, matrix5x3.e2, matrix5x3.e3)

    private val glowA = listOf<Boolean>(resultMatrix5x3?.a1 ?: false, resultMatrix5x3?.a2 ?: false, resultMatrix5x3?.a3 ?: false)
    private val glowB = listOf<Boolean>(resultMatrix5x3?.b1 ?: false, resultMatrix5x3?.b2 ?: false, resultMatrix5x3?.b3 ?: false)
    private val glowC = listOf<Boolean>(resultMatrix5x3?.c1 ?: false, resultMatrix5x3?.c2 ?: false, resultMatrix5x3?.c3 ?: false)
    private val glowD = listOf<Boolean>(resultMatrix5x3?.d1 ?: false, resultMatrix5x3?.d2 ?: false, resultMatrix5x3?.d3 ?: false)
    private val glowE = listOf<Boolean>(resultMatrix5x3?.e1 ?: false, resultMatrix5x3?.e2 ?: false, resultMatrix5x3?.e3 ?: false)

    private val slots = listOf(slotA, slotB, slotC, slotD, slotE)
    private val glows = listOf(glowA, glowB, glowC, glowD, glowE)

    private val shuffledSlotItemList = slotItemContainer.list.shuffled()

    fun generateSlot(slotIndex: Int): List<SlotItem> {
        val slotItemList = mutableListOf<SlotItem>()

        slots[slotIndex].onEach { slotItemIndex ->
            val slotItem = shuffledSlotItemList[slotItemIndex]
            slotItemList.add(slotItem)
        }

        return slotItemList
    }

    fun generateGlow(glowIndex: Int): List<Int> {
        val indexList = mutableListOf<Int>()
        glows[glowIndex].onEachIndexed { index, isWin -> if (isWin) indexList.add(index) }
        return indexList
    }

}