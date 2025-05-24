package com.viade.bepuzzle.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.viade.bepuzzle.game.actors.ACircles
import com.viade.bepuzzle.game.actors.button.AButton
import com.viade.bepuzzle.game.utils.Block
import com.viade.bepuzzle.game.utils.TIME_ANIM_SCREEN
import com.viade.bepuzzle.game.utils.actor.animHideSuspend
import com.viade.bepuzzle.game.utils.actor.animShowSuspend
import com.viade.bepuzzle.game.utils.advanced.AdvancedGroup
import com.viade.bepuzzle.game.utils.advanced.AdvancedScreen
import com.viade.bepuzzle.game.utils.runGDX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AMainInApp(override val screen: AdvancedScreen): AdvancedGroup() {

    private val aCircles = ACircles(screen)

    private val imgPanelCoins = Image(screen.game.assetsAll.panel_coins)
    private val listBtn       = List(3) { AButton(screen, AButton.listTypeCoins[it]) }

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                addAndFillActor(aCircles)
                addImgPanelCoins()
                addBtns()

                children.onEach { it.color.a = 0f }
            }

            delay(100)
            animShowMain()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgPanelCoins() {
        addActor(imgPanelCoins)
        imgPanelCoins.setBounds(0f, 0f, 1082f, 1663f)
    }

    private fun addBtns() {
        var ny = 1121f
        listBtn.onEachIndexed { index, aButton ->
            addActor(aButton)
            aButton.setBounds(678f, ny, 368f, 216f)
            ny -= (260 + 216)

            aButton.setOnClickListener { buy(index) }
        }

        screen.game.activity.blockPurchase = { purchase ->
            when(purchase) {
                "100_coins" -> {
                    screen.game.dataStoreBalanceUtil.update { it + 100 }
                }
                "300_coins" -> {
                    screen.game.dataStoreBalanceUtil.update { it + 300 }
                }
                "700_coins" -> {
                    screen.game.dataStoreBalanceUtil.update { it + 700 }
                }
            }
        }
    }

    // Anim Main ------------------------------------------------

    private suspend fun animShowMain() {
        withContext(Dispatchers.Default) {
            children.onEach {
                it.animShowSuspend(0.4f)
            }
        }
    }

    // Anim ------------------------------------------------

    suspend fun animHideMain(block: Block = Block {  }) {
        withContext(Dispatchers.Default) {
            animHideSuspend(TIME_ANIM_SCREEN)
        }
        block.invoke()
    }

    // Logic -----------------------------------------------------

    private fun buy(index: Int) {
        screen.game.activity.apply {
            productDetailsList?.let { listDetails ->
                launchBilling(listDetails[index])
            }
        }

    }

}