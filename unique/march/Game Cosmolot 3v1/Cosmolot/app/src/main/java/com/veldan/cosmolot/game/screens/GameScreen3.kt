package com.veldan.cosmolot.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.veldan.cosmolot.game.actors.button.AButton
import com.veldan.cosmolot.game.actors.button.AButtonStyle
import com.veldan.cosmolot.game.actors.label.ALabelStyle
import com.veldan.cosmolot.game.actors.label.spinning.SpinningLabel
import com.veldan.cosmolot.game.actors.slots.SpinResult
import com.veldan.cosmolot.game.actors.slots.slot5x3.SlotGroup
import com.veldan.cosmolot.game.manager.NavigationManager
import com.veldan.cosmolot.game.manager.SpriteManager
import com.veldan.cosmolot.game.util.*
import com.veldan.cosmolot.game.util.advanced.AdvancedScreen
import com.veldan.cosmolot.game.util.advanced.AdvancedStage
import com.veldan.cosmolot.game.util.listeners.toClickable
import com.veldan.cosmolot.util.Once
import com.veldan.cosmolot.util.toMS
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import com.veldan.cosmolot.game.util.Layout.Game3 as LG3

class GameScreen3 : AdvancedScreen(1280f, 863f) {

    companion object {
        private const val TIME_WAIT_AFTER_AUTOSPIN = 1f

        private const val BET_STEP = 1_000L
        private const val BET_MIN  = BET_STEP
        private const val BET_MAX  = 10_000L

        private var isPause = false
    }

    private val betFlow           = MutableStateFlow(BET_MIN)
    private val autoSpinStateFlow = MutableStateFlow(AutospinState.DEFAULT)

    private val onceStartAutoSpin = Once()

    private val balanceLabel = SpinningLabel("", ALabelStyle.bowler_40_white)
    private val menuButton   = AButton(AButtonStyle.Game_3.menu)
    private val musicButton  = AButton(AButtonStyle.Game_3.music)
    private val spinButton   = AButton(AButtonStyle.Game_3.spin)
    private val autoButton   = AButton(AButtonStyle.Game_3.auto)
    private val plusActor    = Actor()
    private val minusActor   = Actor()
    private val betText      = Label("", ALabelStyle.bowler_25_white)
    private val lastWinText  = SpinningLabel("0$FUN", ALabelStyle.bowler_25_white)
    private val slotGroup    = SlotGroup()



    override fun show() {
        super.show()
        setUIBackground(SpriteManager.GameRegion_3.BACKGROUND.region)
        if (isPause) musicButton.press() else MusicUtil.GAME_3.apply {
            isLooping = true
        }.play()
        onBackBlock = { MusicUtil.GAME_3.stop() }
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addBalance()
        addMenu()
        addMusic()
        addAuto()
        addBet()
        addLastWin()
        addSlotGroup()
        addSpin()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addBalance() {
        addActor(balanceLabel)
        balanceLabel.setBounds(LG3.balance)
        updateBalance()
    }

    private fun AdvancedStage.addMenu() {
        addActor(menuButton)
        menuButton.apply {
            setBounds(LG3.menu)
            setOnClickListener {
                MusicUtil.GAME_3.stop()
                NavigationManager.back()
            }
        }
    }

    private fun AdvancedStage.addMusic() {
        val area = Actor()
        addActors(musicButton, area)
        musicButton.apply {
            setBounds(LG3.music)
            area.setBounds(LG3.musicArea)
            addArea(area)
            setOnClickListener {
                isPause = if (isPause) {
                    MusicUtil.GAME_3.play()
                    unpress()
                    false
                } else {
                    MusicUtil.GAME_3.pause()
                    press()
                    true
                }
            }
        }
    }

    private fun AdvancedStage.addSpin() {
        addActor(spinButton)
        spinButton.apply {
            setBounds(LG3.spin)
            setOnClickListener { spinHandler() }
        }
    }

    private fun AdvancedStage.addAuto() {
        addActor(autoButton)
        autoButton.apply {
            setBounds(LG3.auto)
            setOnClickListener { autoHandler() }
        }
    }

    private fun AdvancedStage.addBet() {
        addActors(betText, plusActor, minusActor)
        betText.apply {
            setBounds(LG3.betText)
            setAlignment(Align.center)
        }
        plusActor.apply {
            setBounds(LG3.plus)
            toClickable().setOnClickListener { plusHandler() }
        }
        minusActor.apply {
            setBounds(LG3.minus)
            toClickable().setOnClickListener { minusHandler() }
        }
        updateBet()
    }

    private fun AdvancedStage.addLastWin() {
        addActor(lastWinText)
        lastWinText.setBounds(LG3.lastWin)
    }

    private fun AdvancedStage.addSlotGroup() {
        addActor(slotGroup)
        slotGroup.setBounds(LG3.slotGroup)
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------
    private fun updateBalance() {
        coroutine.launch {
            Balance.balanceFlow.collect { balance -> balanceLabel.setText("БАЛАНС: " + balance.transformToBalanceFormat() + FUN) }
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

    private fun spinHandler() {
        spinButton.pressAndDisable()
        autoButton.pressAndDisable(true)
        plusActor.disable()
        minusActor.disable()
        coroutine.launch {
            if (checkBalance()) spinAndSetResult()
            spinButton.unpressAndEnable()
            autoButton.unpressAndEnable()
            plusActor.enable()
            minusActor.enable()
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

    private fun autoHandler() {
        autoSpinStateFlow.apply {
            value = if (value == AutospinState.DEFAULT) AutospinState.GO else AutospinState.DEFAULT
        }
        onceStartAutoSpin.once { startAutospin() }
    }

    private fun startAutospin() {
        coroutine.launch {
            autoSpinStateFlow.collect { state ->
                when (state) {
                    AutospinState.GO      -> {
                        autoButton.press()
                        spinButton.pressAndDisable()
                        plusActor.disable()
                        minusActor.disable()

                        coroutine.launch {
                            while (autoSpinStateFlow.value == AutospinState.GO) {
                                if (checkBalance()) {
                                    spinAndSetResult()
                                    delay(TIME_WAIT_AFTER_AUTOSPIN.toMS)
                                } else autoSpinStateFlow.value = AutospinState.DEFAULT
                            }

                            autoButton.unpressAndEnable()
                            spinButton.unpressAndEnable()
                            plusActor.enable()
                            minusActor.enable()
                        }
                    }

                    AutospinState.DEFAULT -> autoButton.pressAndDisable(true)
                }
            }
        }
    }

}

