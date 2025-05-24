package com.viade.bepuzzle.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.viade.bepuzzle.game.actors.ACircles
import com.viade.bepuzzle.game.actors.button.AButton
import com.viade.bepuzzle.game.utils.Block
import com.viade.bepuzzle.game.utils.GameColor
import com.viade.bepuzzle.game.utils.TIME_ANIM_SCREEN
import com.viade.bepuzzle.game.utils.actor.animHideSuspend
import com.viade.bepuzzle.game.utils.actor.animShowSuspend
import com.viade.bepuzzle.game.utils.advanced.AdvancedGroup
import com.viade.bepuzzle.game.utils.advanced.AdvancedScreen
import com.viade.bepuzzle.game.utils.font.FontParameter
import com.viade.bepuzzle.game.utils.runGDX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AMainShop(override val screen: AdvancedScreen): AdvancedGroup() {

    private val parameter = FontParameter()
        .setCharacters(FontParameter.CharType.NUMBERS)
        .setSize(120)

    private val font120 = screen.fontGenerator_Karantina.generateFont(parameter)

    private val ls120 = LabelStyle(font120, GameColor.da)

    private val aCircles = ACircles(screen)

    private val imgPanelShop = Image(screen.game.assetsAll.panel_shop)
    private val listBtn      = List(3) { AButton(screen, AButton.listTypeShop[it]) }
    private val listLbl      = List(3) { Label("", ls120) }

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                addAndFillActor(aCircles)
                addImgPanelShop()
                addBtns()
                addLbls()

                children.onEach { it.color.a = 0f }
            }

            delay(100)
            animShowMain()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgPanelShop() {
        addActor(imgPanelShop)
        imgPanelShop.setBounds(118f, 75f, 759f, 1638f)
    }

    private fun addBtns() {
        val listY   = listOf(1338f, 798f, 178f)
        val listSum = listOf(2000, 5000, 1000)

        val balanceUtil = screen.game.dataStoreBalanceUtil

        listBtn.onEachIndexed { index, aButton ->
            addActor(aButton)
            aButton.setBounds(816f, listY[index], 202f, 128f)

            aButton.setOnClickListener {
                if (balanceUtil.balanceFlow.value >= listSum[index]) {
                    screen.game.soundUtil.apply { play(buy) }
                    balanceUtil.update { it - listSum[index] }

                    when(index) {
                        0 -> screen.game.dataStoreK2Util.update { it + 1 }
                        1 -> screen.game.dataStoreK5Util.update { it + 1 }
                        2 -> screen.game.dataStoreK1Util.update { it + 1 }
                    }
                } else {
                    screen.game.soundUtil.apply { play(fail_buy) }
                }
            }
        }
    }

    private fun addLbls() {
        val listY = listOf(1449f, 902f, 284f)

        listLbl.onEachIndexed { index, lbl ->
            addActor(lbl)
            lbl.setBounds(674f, listY[index], 32f, 120f)
            coroutine?.launch {
                when(index) {
                    0 -> { screen.game.dataStoreK2Util.k2Flow.collect { runGDX { lbl.setText(it) } } }
                    1 -> { screen.game.dataStoreK5Util.k5Flow.collect { runGDX { lbl.setText(it) } } }
                    2 -> { screen.game.dataStoreK1Util.k1Flow.collect { runGDX { lbl.setText(it) } } }
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

}