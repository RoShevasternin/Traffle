package com.slotscity.official.game.actors.carnaval_cat.slot5x3

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.slotscity.official.game.actors.masks.Mask
import com.slotscity.official.game.actors.slots.FillStrategy
import com.slotscity.official.game.utils.advanced.AdvancedGroup
import com.slotscity.official.game.utils.advanced.AdvancedScreen
import com.slotscity.official.game.utils.toMS
import com.slotscity.official.util.log
import com.slotscity.official.game.utils.Layout.CarnavalCat.SlotGroup as LSG

class SlotGroup(override val screen: AdvancedScreen): AdvancedGroup() {

    companion object {
        const val SLOT_COUNT = 5
        const val GLOW_COUNT = 5

        const val TIME_SHOW_WIN            = 0.25f
        const val TIME_BETWEEN_SHOW_WIN    = 0.25f
        const val TIME_WAIT_AFTER_SHOW_WIN = 0.25f

        var isSpeed = false
    }

    private val mask       = Mask(screen, alphaHeight = 500)
    private val slotList   = List(SLOT_COUNT) { Slot(screen) }
    private val glowList   = List(GLOW_COUNT) { Glow(screen) }

    private var winNumber   = (1..6).shuffled().first()
    private var spinCounter = 0

    private val slotItemContainer = SlotItemContainer(screen.game.carnavalCatAssets.listItem)
    private val slotFillManager   = SlotFillManager(slotList, glowList, slotItemContainer)

    private var isWin = false

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------
    override fun addActorsOnGroup() {
        addMask()
        addGlowList()
    }

    private fun addMask() {
        addAndFillActor(mask)
        mask.addSlotList()
    }

    private fun AdvancedGroup.addSlotList() {
        var newX = LSG.slot.x

        slotList.onEach { slot ->
            addActor(slot)
            with(LSG.slot) {
                slot.setBounds(newX, y, w, h)
                newX += w + hs
            }
        }
    }

    private fun addGlowList() {
        var newX = LSG.glow.x

        glowList.onEach { glow ->
            addActor(glow)
            glow.apply {
                with(LSG.glow) {
                    setBounds(newX, y, w, h)
                    newX += w + hs
                }
            }
        }
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------
    suspend fun spin() = CompletableDeferred<Boolean>().also { continuation ->
        isWin = false

        spinCounter++
        logCounterAndWinNumber()
        fillSlots()

        val completableList = List(SLOT_COUNT) { CompletableDeferred<Boolean>() }
        val listTime        = List(SLOT_COUNT) { (if (isSpeed) 0.3f else 1f) + ((if (isSpeed) 0.1f else 0.3f) * it) }

        slotList.onEachIndexed { index, slot ->
            CoroutineScope(Dispatchers.Main).launch {
                slot.spin(listTime[index])
                completableList[index].complete(true)
            }
        }

        completableList.onEach { it.await() }


        glowList.onEach { it.show(TIME_SHOW_WIN, TIME_BETWEEN_SHOW_WIN) }
        delay(TIME_WAIT_AFTER_SHOW_WIN.toMS)
        glowList.onEach { it.hide(TIME_SHOW_WIN, TIME_BETWEEN_SHOW_WIN) }

        continuation.complete(isWin)

    }.await()

    private fun logCounterAndWinNumber() {
        log("spin: $spinCounter | win: $winNumber")
    }

    private fun fillSlots() {
        when (spinCounter) {
            winNumber -> {
                isWin = true
                resetWin()
                slotFillManager.fill(FillStrategy.WIN)
            }
            else      -> {
                slotFillManager.fill(FillStrategy.MIX)
            }
        }
    }

    private fun resetWin() {
        spinCounter = 0
        winNumber   = (1..5).shuffled().first()
    }

}