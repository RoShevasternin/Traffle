package com.veldan.cosmolot.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.veldan.cosmolot.game.actors.button.AButton
import com.veldan.cosmolot.game.actors.button.AButtonStyle
import com.veldan.cosmolot.game.actors.checkbox.ACheckBox
import com.veldan.cosmolot.game.actors.checkbox.ACheckBoxGroup
import com.veldan.cosmolot.game.actors.checkbox.ACheckBoxStyle
import com.veldan.cosmolot.game.actors.label.ALabelStyle
import com.veldan.cosmolot.game.actors.label.spinning.SpinningLabel
import com.veldan.cosmolot.game.actors.slots.SpinResult
import com.veldan.cosmolot.game.actors.slots.slot6x4.SlotGroup
import com.veldan.cosmolot.game.manager.NavigationManager
import com.veldan.cosmolot.game.manager.SpriteManager
import com.veldan.cosmolot.game.util.*
import com.veldan.cosmolot.game.util.advanced.AdvancedScreen
import com.veldan.cosmolot.game.util.advanced.AdvancedStage
import com.veldan.cosmolot.util.Once
import com.veldan.cosmolot.util.toMS
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import com.veldan.cosmolot.game.util.Layout.Game2 as LG2

class GameScreen2 : AdvancedScreen(1274f, 821f) {

    companion object {
        private const val TIME_WAIT_AFTER_AUTOSPIN = 1f

        private var isPause = false
    }

    private val autoSpinStateFlow = MutableStateFlow(AutospinState.DEFAULT)
    private val onceStartAutoSpin = Once()

    private val betList = listOf(100, 500, 1_000)

    private val betFlow = MutableStateFlow(betList.first())

    private val balanceLabel = SpinningLabel("", ALabelStyle.bowler_20_white)
    private val menuButton = AButton(AButtonStyle.Game_2.menu)
    private val listCheckBox = List(3) { ACheckBox(ACheckBoxStyle.Game_2.bet) }
    private val musicButton = AButton(AButtonStyle.Game_2.music)
    private val spinButton = AButton(AButtonStyle.Game_2.spin)
    private val autoButton = AButton(AButtonStyle.Game_2.auto)
    private val slotGroup = SlotGroup()



    override fun show() {
        super.show()
        setBackgrounds(SpriteManager.GameRegion_2.BACKGROUND_BACK.region, SpriteManager.GameRegion_2.BACKGROUND.region)
        if (isPause) musicButton.press() else MusicUtil.GAME_2.apply {
            isLooping = true
        }.play()
        onBackBlock = { MusicUtil.GAME_2.stop() }
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addBalance()
        addMenu()
        addBet()
        addMusic()
        addSpin()
        addAuto()
        addSlotGroup()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addBalance() {
        addActor(balanceLabel)
        balanceLabel.setBounds(LG2.balance)
        updateBalance()
    }

    private fun AdvancedStage.addMenu() {
        addActor(menuButton)
        menuButton.apply {
            setBounds(LG2.menu)
            setOnClickListener {
                MusicUtil.GAME_2.stop()
                NavigationManager.back()
            }
        }
    }

    private fun AdvancedStage.addBet() {
        val boxGroup = ACheckBoxGroup()
        var nx = LG2.box.x

        betList.onEachIndexed { index, value ->
            val label = Label("${value}$FUN", ALabelStyle.bowler_15_white)
            val box = listCheckBox[index]

            addActor(box)
            box.apply {
                addActor(label)
                with(LG2.box) {
                    setBounds(nx, y, w, h)
                    nx += w + hs
                }
                checkBoxGroup = boxGroup
                setOnCheckListener { if (it) betFlow.value = value }

                label.apply {
                    disable()
                    setAlignment(Align.center)
                    setBounds(LG2.boxText)
                }
            }
        }

        listCheckBox.first().check()
    }

    private fun AdvancedStage.addMusic() {
        addActor(musicButton)
        musicButton.apply {
            setBounds(LG2.music)
            setOnClickListener {
                isPause = if (isPause) {
                    MusicUtil.GAME_2.play()
                    unpress()
                    false
                } else {
                    MusicUtil.GAME_2.pause()
                    press()
                    true
                }
            }
        }
    }

    private fun AdvancedStage.addSpin() {
        addActor(spinButton)
        spinButton.apply {
            setBounds(LG2.spin)
            setOnClickListener { spinHandler() }
        }
    }

    private fun AdvancedStage.addAuto() {
        addActor(autoButton)
        autoButton.apply {
            setBounds(LG2.auto)
            setOnClickListener { autoHandler() }
        }
    }

    private fun AdvancedStage.addSlotGroup() {
        addActor(slotGroup)
        slotGroup.setBounds(LG2.slotGroup)
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------
    private fun updateBalance() {
        coroutine.launch {
            Balance.balanceFlow.collect { balance -> balanceLabel.setText(balance.transformToBalanceFormat() + FUN) }
        }
    }

    private fun spinHandler() {
        listCheckBox.onEach { it.disable() }
        autoButton.pressAndDisable()
        spinButton.pressAndDisable()
        coroutine.launch {
            if (checkBalance()) spinAndSetResult()
            listCheckBox.onEach { it.enable() }
            autoButton.unpressAndEnable()
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
                        listCheckBox.onEach { it.disable() }

                        coroutine.launch {
                            while (autoSpinStateFlow.value == AutospinState.GO) {
                                if (checkBalance()) {
                                    spinAndSetResult()
                                    delay(TIME_WAIT_AFTER_AUTOSPIN.toMS)
                                } else autoSpinStateFlow.value = AutospinState.DEFAULT
                            }

                            autoButton.unpressAndEnable()
                            spinButton.unpressAndEnable()
                            listCheckBox.onEach { it.enable() }
                        }
                    }

                    AutospinState.DEFAULT -> autoButton.pressAndDisable(true)
                }
            }
        }
    }

}

