package com.vortemika208.lavrix.game.actors.slotGroup

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.vortemika208.lavrix.game.actors.mask.AOldMask
import com.vortemika208.lavrix.game.utils.HEIGHT_UI
import com.vortemika208.lavrix.game.utils.WIDTH_UI
import com.vortemika208.lavrix.game.utils.actor.addAndFillActor
import com.vortemika208.lavrix.game.utils.actor.addAndFillActors
import com.vortemika208.lavrix.game.utils.actor.disable
import com.vortemika208.lavrix.game.utils.advanced.AdvancedGroup
import com.vortemika208.lavrix.game.utils.advanced.AdvancedScreen
import com.vortemika208.lavrix.game.utils.gdxGame
import com.vortemika208.lavrix.game.utils.runGDX
import com.vortemika208.lavrix.game.utils.toMS
import com.vortemika208.lavrix.util.log
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.vortemika208.lavrix.game.actors.slotGroup.Layout.SlotGroup as LSG

class ASlotGroup(
    override val screen: AdvancedScreen,
    val listItemRegion : List<TextureRegion>,
): AdvancedGroup() {

    companion object {
        const val SLOT_COUNT = 5

        const val TIME_SPIN                = 1.25f
        const val TIME_WAIT_AFTER_SHOW_WIN = 1f
    }

    private val aPanelImg  = Image(gdxGame.assetsAll.PANEL_SLOT_GROUP)
    private val aMask      = AOldMask(screen, gdxGame.assetsAll.MASK_SLOT_GROUP, WIDTH_UI.toInt(), HEIGHT_UI.toInt())
    private val listSlot   = List(SLOT_COUNT) { ASlot(screen, listItemRegion) }

    private var winNumber   = (1..5).shuffled().first()
    private var spinCounter = 0

    private val slotItemContainer = SlotItemContainer(listItemRegion)
    private val slotFillManager   = SlotFillManager(listSlot, slotItemContainer)

    private var isWin = false

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------
    override fun addActorsOnGroup() {
        disable()

        addAndFillActors(aPanelImg, aMask)
        aMask.addSlotList()
    }

    private fun AdvancedGroup.addSlotList() {
        var newX = LSG.slot.x

        listSlot.onEach { slot ->
            addActor(slot)
            with(LSG.slot) {
                slot.setBounds(newX, y, w, h)
                newX += w + hs
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
        val listTime        = List(SLOT_COUNT) { TIME_SPIN * it.inc() }

        listSlot.onEachIndexed { index, slot ->
            CoroutineScope(Dispatchers.Main).launch {
                slot.spin(listTime[index])
                completableList[index].complete(true)
            }
        }

        completableList.onEach { it.await() }

        if (isWin) delay(TIME_WAIT_AFTER_SHOW_WIN.toMS)
        continuation.complete(isWin)

    }.await()

    private fun logCounterAndWinNumber() {
        log("New Spin ---------------------------")
        log("spin: $spinCounter | win: $winNumber")
    }

    private fun fillSlots() {
        runGDX {
            when (spinCounter) {
                winNumber -> {
                    isWin = true
                    resetWin()
                    slotFillManager.fill(FillStrategy.WIN)
                }

                else -> {
                    slotFillManager.fill(FillStrategy.MIX)
                }
            }
        }
    }

    private fun resetWin() {
        spinCounter = 0
        winNumber   = (1..5).shuffled().first()
    }

}