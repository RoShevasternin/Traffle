package com.slotscity.official.game.actors.slots.slot6x5

import com.slotscity.official.game.actors.slots.SlotItem

class Matrix6x5Handler(
    private val matrix6x5        : Matrix6x5,
    private val slotItemContainer: SlotItemContainer
) {

    private val resultMatrix6x5 = matrix6x5.resultMatrix6x5

    private val slotA = listOf<Int>(matrix6x5.a1, matrix6x5.a2, matrix6x5.a3, matrix6x5.a4, matrix6x5.a5)
    private val slotB = listOf<Int>(matrix6x5.b1, matrix6x5.b2, matrix6x5.b3, matrix6x5.b4, matrix6x5.b5)
    private val slotC = listOf<Int>(matrix6x5.c1, matrix6x5.c2, matrix6x5.c3, matrix6x5.c4, matrix6x5.c5)
    private val slotD = listOf<Int>(matrix6x5.d1, matrix6x5.d2, matrix6x5.d3, matrix6x5.d4, matrix6x5.d5)
    private val slotE = listOf<Int>(matrix6x5.e1, matrix6x5.e2, matrix6x5.e3, matrix6x5.e4, matrix6x5.e5)
    private val slotF = listOf<Int>(matrix6x5.f1, matrix6x5.f2, matrix6x5.f3, matrix6x5.f4, matrix6x5.f5)

    private val glowA = listOf<Boolean>(resultMatrix6x5?.a1 ?: false, resultMatrix6x5?.a2 ?: false, resultMatrix6x5?.a3 ?: false, resultMatrix6x5?.a4 ?: false, resultMatrix6x5?.a5 ?: false)
    private val glowB = listOf<Boolean>(resultMatrix6x5?.b1 ?: false, resultMatrix6x5?.b2 ?: false, resultMatrix6x5?.b3 ?: false, resultMatrix6x5?.b4 ?: false, resultMatrix6x5?.b5 ?: false)
    private val glowC = listOf<Boolean>(resultMatrix6x5?.c1 ?: false, resultMatrix6x5?.c2 ?: false, resultMatrix6x5?.c3 ?: false, resultMatrix6x5?.c4 ?: false, resultMatrix6x5?.c5 ?: false)
    private val glowD = listOf<Boolean>(resultMatrix6x5?.d1 ?: false, resultMatrix6x5?.d2 ?: false, resultMatrix6x5?.d3 ?: false, resultMatrix6x5?.d4 ?: false, resultMatrix6x5?.d5 ?: false)
    private val glowE = listOf<Boolean>(resultMatrix6x5?.e1 ?: false, resultMatrix6x5?.e2 ?: false, resultMatrix6x5?.e3 ?: false, resultMatrix6x5?.e4 ?: false, resultMatrix6x5?.e5 ?: false)
    private val glowF = listOf<Boolean>(resultMatrix6x5?.f1 ?: false, resultMatrix6x5?.f2 ?: false, resultMatrix6x5?.f3 ?: false, resultMatrix6x5?.f4 ?: false, resultMatrix6x5?.f5 ?: false)

    private val slots = listOf(slotA, slotB, slotC, slotD, slotE, slotF)
    private val glows = listOf(glowA, glowB, glowC, glowD, glowE, glowF)

    private val shuffledSlotItemList = slotItemContainer.list.shuffled()

    fun generateSlot(slotIndex: Int): List<SlotItem> {
        val slotItemList = mutableListOf<SlotItem>()

        slots[slotIndex].onEach { slotItemIndex ->
            val slotItem = when (slotItemIndex) {
                26   -> slotItemContainer.wild
                else -> shuffledSlotItemList[slotItemIndex]
            }
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