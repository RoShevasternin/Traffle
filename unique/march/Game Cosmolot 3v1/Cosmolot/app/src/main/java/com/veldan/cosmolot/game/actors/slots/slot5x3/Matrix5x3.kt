package com.veldan.cosmolot.game.actors.slots.slot5x3

import com.veldan.cosmolot.game.actors.slots.Intersection
import com.veldan.cosmolot.game.actors.slots.SlotItem

class Matrix5x3(
    private val winItemList: List<Item>? = null,
    val scheme: String = "",

    a1: Item,
    a2: Item,
    a3: Item,

    b1: Item,
    b2: Item,
    b3: Item,

    c1: Item,
    c2: Item,
    c3: Item,

    d1: Item,
    d2: Item,
    d3: Item,

    e1: Item,
    e2: Item,
    e3: Item,

    ) {

    private val slotA = listOf<Item>(a1, a2, a3)
    private val slotB = listOf<Item>(b1, b2, b3)
    private val slotC = listOf<Item>(c1, c2, c3)
    private val slotD = listOf<Item>(d1, d2, d3)
    private val slotE = listOf<Item>(e1, e2, e3)

    private val slotList = listOf(slotA, slotB, slotC, slotD, slotE)

    private lateinit var shuffledSlotItemList: List<SlotItem>

    var winSlotItemSet: Set<SlotItem>? = null
        private set
    var intersectionList: List<Intersection>? = null
        private set



    private fun generateAndSetData() {
        if (this::shuffledSlotItemList.isInitialized.not()) throw Exception("Используй метод initialize")

        val tmpWinSlotItemSet   = mutableSetOf<SlotItem>()
        val tmpIntersectionList = mutableListOf<Intersection>()

        winItemList?.let { winList ->
            slotList.onEachIndexed { slotIndex, slot ->
                slot.onEachIndexed { itemIndex, item ->
                    if (winList.contains(item)) {
                        when (item) {
                            Item.W -> SlotItemContainer.wild
                            else   -> shuffledSlotItemList[item.index]
                        }.also { slotItem -> tmpWinSlotItemSet.add(slotItem) }
                        tmpIntersectionList.add(Intersection(slotIndex = slotIndex, rowIndex = itemIndex))
                    }
                }
            }
        }

        winSlotItemSet   = tmpWinSlotItemSet.ifEmpty { null }
        intersectionList = tmpIntersectionList.ifEmpty { null }

    }



    fun initialize(): Matrix5x3 {
        shuffledSlotItemList = SlotItemContainer.list.shuffled()
        generateAndSetData()
        return this
    }

    fun generateSlot(slotIndex: Int): List<SlotItem> = mutableListOf<SlotItem>().apply {
        if (this@Matrix5x3::shuffledSlotItemList.isInitialized.not()) throw Exception("Используй метод initialize")

        slotList[slotIndex].onEach { item ->
            when (item) {
                Item.W -> SlotItemContainer.wild
                else   -> shuffledSlotItemList[item.index]
            }.also { slotItem -> add(slotItem) }
        }
    }



    enum class Item(val index: Int) {
        W(SlotItemContainer.SLOT_ITEM_WILD_ID),

        a1(0),
        a2(1),
        a3(2),
        a4(3),
        a5(4),
        a6(5),
        a7(6),
        a8(7),
    }

}
