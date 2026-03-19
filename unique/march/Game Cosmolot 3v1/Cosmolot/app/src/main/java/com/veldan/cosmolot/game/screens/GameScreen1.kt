package com.veldan.cosmolot.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.veldan.cosmolot.game.actors.button.AButton
import com.veldan.cosmolot.game.actors.button.AButtonStyle
import com.veldan.cosmolot.game.actors.label.ALabelStyle
import com.veldan.cosmolot.game.actors.label.spinning.SpinningLabel
import com.veldan.cosmolot.game.actors.slots.SpinResult
import com.veldan.cosmolot.game.actors.slots.slot3x3.SlotGroup
import com.veldan.cosmolot.game.manager.NavigationManager
import com.veldan.cosmolot.game.manager.SpriteManager
import com.veldan.cosmolot.game.util.*
import com.veldan.cosmolot.game.util.advanced.AdvancedScreen
import com.veldan.cosmolot.game.util.advanced.AdvancedStage
import com.veldan.cosmolot.game.util.listeners.toClickable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import com.veldan.cosmolot.game.util.Layout.Game1 as LG1

class GameScreen1: AdvancedScreen(1280f, 727f) {

    companion object {
        private const val BET_STEP = 1_000L
        private const val BET_MIN  = BET_STEP
        private const val BET_MAX  = 10_000L

        private var isPause = false
    }

    private val betFlow = MutableStateFlow(BET_MIN)

    private val balanceLabel  = SpinningLabel("", ALabelStyle.bowler_20_white)
    private val menuButton    = AButton(AButtonStyle.Game_1.menu)
    private val plusImage     = Image(SpriteManager.GameRegion_1.PLUS.region)
    private val minusImage    = Image(SpriteManager.GameRegion_1.MINUS.region)
    private val betText       = Label("", ALabelStyle.bowler_20_white)
    private val betMaxButton  = AButton(AButtonStyle.Game_1.betMax)
    private val spinButton    = AButton(AButtonStyle.Game_1.spin)
    private val slotGroup     = SlotGroup()
    private val lastWinText   = SpinningLabel("0$FUN", ALabelStyle.bowler_20_white)
    private val musicButton   = AButton(AButtonStyle.Game_1.music)



    override fun show() {
        super.show()
        setBackgrounds(SpriteManager.GameRegion_1.BACKGROUND.region)
        if (isPause) musicButton.press() else MusicUtil.GAME_1.apply {
            isLooping = true
        }.play()
        onBackBlock = { MusicUtil.GAME_1.stop() }
    }



    override fun AdvancedStage.addActorsOnStageUI() {
        addBalance()
        addMenu()
        addBet()
        addSpin()
        addSlotGroup()
        addLastWin()
        addMusic()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addBalance() {
        addActor(balanceLabel)
        balanceLabel.setBounds(LG1.balance)
        updateBalance()
    }

    private fun AdvancedStage.addMenu() {
        addActor(menuButton)
        menuButton.apply {
            setBounds(LG1.menu)
            setOnClickListener {
                MusicUtil.GAME_1.stop()
                NavigationManager.back()
            }
        }
    }

    private fun AdvancedStage.addBet() {
        addActors(betText, plusImage, minusImage, betMaxButton)
        betText.apply {
            setBounds(LG1.betText)
            setAlignment(Align.center)
        }
        plusImage.apply {
            setBounds(LG1.plus)
            toClickable().setOnClickListener { plusHandler() }
        }
        minusImage.apply {
            setBounds(LG1.minus)
            toClickable().setOnClickListener { minusHandler() }
        }
        betMaxButton.apply {
            setBounds(LG1.betMax)
            setOnClickListener { betMaxHandler() }
        }
        updateBet()
    }

    private fun AdvancedStage.addSpin() {
        addActor(spinButton)
        spinButton.apply {
            setBounds(LG1.spin)
            setOnClickListener { spinHandler() }
        }
    }

    private fun AdvancedStage.addSlotGroup() {
        addActor(slotGroup)
        slotGroup.setBounds(LG1.slotGroup)
    }

    private fun AdvancedStage.addLastWin() {
        addActor(lastWinText)
        lastWinText.setBounds(LG1.lastWin)
    }

    private fun AdvancedStage.addMusic() {
        addActor(musicButton)
        musicButton.apply {
            setBounds(LG1.music)
            setOnClickListener {
                isPause = if (isPause) {
                    MusicUtil.GAME_1.play()
                    unpress()
                    false
                } else {
                    MusicUtil.GAME_1.pause()
                    press()
                    true
                }
            }
        }
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------
    private fun updateBalance() {
        coroutine.launch {
            Balance.balanceFlow.collect { balance -> balanceLabel.setText(balance.transformToBalanceFormat() + FUN) }
        }
    }

    private fun updateBet() {
        coroutine.launch {
            betFlow.collect { bet -> betText.setText(bet.transformToBalanceFormat() + FUN) }
        }
    }

    private fun plusHandler() {
        with(betFlow) {
            value = if ((value + BET_STEP) < BET_MAX) value + BET_STEP else BET_MAX
        }
    }

    private fun minusHandler() {
        with(betFlow) {
            value = if ((value - BET_STEP) > BET_MIN) value - BET_STEP else BET_MIN
        }
    }

    private fun betMaxHandler() {
        betFlow.value = BET_MAX
    }

    private fun spinHandler() {
        listOf(plusImage, minusImage, betMaxButton).onEach { (it as Actor).disable() }
        spinButton.pressAndDisable()
        coroutine.launch {
            if (checkBalance()) spinAndSetResult()
            listOf(plusImage, minusImage, betMaxButton).onEach { (it as Actor).enable() }
            spinButton.unpressAndEnable()
        }
    }

    private fun checkBalance() = if ((Balance.balanceFlow.value - betFlow.value) >= 0) {
        Balance.balanceFlow.value -= betFlow.value
        true
    } else false

    private suspend fun spinAndSetResult() {
        slotGroup.spin().apply { calculateAndSetResult() }
    }

    private fun SpinResult.calculateAndSetResult() {
        val slotItemPriceFactor: Float = winSlotItemSet?.map { it.priceCoff }?.sum() ?: 0f
        val sumPrice = (betFlow.value * slotItemPriceFactor).toLong()
        Balance.balanceFlow.value += sumPrice
        if (sumPrice > 0) lastWinText.setText("${sumPrice.transformToBalanceFormat()}$FUN")
    }
}