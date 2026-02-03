package com.gorillaz.puzzlegame.game.actors.slots

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.gorillaz.puzzlegame.game.actors.shader.AMaskGroup
import com.gorillaz.puzzlegame.game.utils.actor.animShow
import com.gorillaz.puzzlegame.game.utils.actor.disable
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedGroup
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedScreen
import com.gorillaz.puzzlegame.game.utils.gdxGame
import com.gorillaz.puzzlegame.game.utils.runGDX
import com.gorillaz.puzzlegame.game.utils.toMS
import com.gorillaz.puzzlegame.util.log
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.gorillaz.puzzlegame.game.utils.Layout.SlotGroup as LSG

class ASlotGroup(
    override val screen  : AdvancedScreen,
    val jackpotRegion    : TextureRegion,
    val listItemRegion   : List<TextureRegion>,
    val jackpotCoff      : Int,
    val interpolationSlot: Interpolation,
    val isSpinAllOnce    : Boolean,
): AdvancedGroup() {

    companion object {
        const val SLOT_COUNT = 5
        const val GLOW_COUNT = 5

        const val TIME_SPIN                = 1f
        const val TIME_SHOW_WIN            = 1f
        const val TIME_HIDE_WIN            = 0.4f
        const val TIME_BETWEEN_SHOW_WIN    = 0.25f
        const val TIME_WAIT_AFTER_SHOW_WIN = 2f
    }

    private val imgPanel   = Image(gdxGame.assetsAll.PANEL_SLOTS)
    private val mask       = AMaskGroup(screen)
    private val slotList   = List(SLOT_COUNT) { ASlot(screen, listItemRegion + jackpotRegion, interpolationSlot) }
    private val glowList   = List(GLOW_COUNT) { AGlow(screen) }
    private val aWinShape  = AWinShape(screen)

    private var winNumber   = 1 //(1..33-jackpotCoff).shuffled().first()
    private var spinCounter = 0

    private val slotItemContainer = SlotItemContainer(jackpotRegion, listItemRegion)
    private val slotFillManager   = SlotFillManager(slotList, glowList, aWinShape, slotItemContainer)

    private var isWin = false

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------
    override fun addActorsOnGroup() {
        disable()

        addAndFillActor(imgPanel)
        addMask()
        addGlowList()
        addAWinShape()
    }

    private fun addMask() {
        addActor(mask)
        mask.setBounds(0f, 75f, 1080f, 649f)
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
//        var newX = LSG.glow.x
//
//        glowList.onEach { glow ->
//            addActor(glow)
//            glow.apply {
//                with(LSG.glow) {
//                    setBounds(newX, y, w, h)
//                    newX += w + hs
//                }
//            }
//        }
    }

    private fun addAWinShape() {
        addAndFillActor(aWinShape)

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
        val listTime        = List(SLOT_COUNT) { TIME_SPIN * if (isSpinAllOnce) 2 else it.inc() }

        slotList.onEachIndexed { index, slot ->
            CoroutineScope(Dispatchers.Main).launch {
                slot.spin(listTime[index])
                completableList[index].complete(true)
            }
        }

        //glowList.onEach { it.show(TIME_SHOW_WIN, TIME_BETWEEN_SHOW_WIN) }
        //delay(TIME_WAIT_AFTER_SHOW_WIN.toMS)
        //glowList.onEach { it.hide(TIME_SHOW_WIN, TIME_BETWEEN_SHOW_WIN) }


        completableList.onEach { it.await() }

        if (isWin) {
            runGDX { aWinShape.animShowWin(TIME_SHOW_WIN) }
            delay(TIME_WAIT_AFTER_SHOW_WIN.toMS)
            runGDX { aWinShape.animHideWin(TIME_HIDE_WIN) }
        }

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
        winNumber   = 1 //(1..33-jackpotCoff).shuffled().first()
    }

}