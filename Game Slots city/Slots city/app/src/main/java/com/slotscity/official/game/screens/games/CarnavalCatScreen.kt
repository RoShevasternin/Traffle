package com.slotscity.official.game.screens.games

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.slotscity.official.game.LibGDXGame
import com.slotscity.official.game.actors.button.AButton
import com.slotscity.official.game.actors.carnaval_cat.slot5x3.SlotGroup
import com.slotscity.official.game.actors.checkbox.ACheckBox
import com.slotscity.official.game.utils.TIME_ANIM_ALPHA
import com.slotscity.official.game.utils.actor.animHide
import com.slotscity.official.game.utils.actor.animShow
import com.slotscity.official.game.utils.advanced.AdvancedScreen
import com.slotscity.official.game.utils.advanced.AdvancedStage
import com.slotscity.official.game.utils.font.FontParameter
import com.slotscity.official.game.utils.region
import com.slotscity.official.game.utils.runGDX
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class CarnavalCatScreen(override val game: LibGDXGame): AdvancedScreen() {

    private val assets = game.carnavalCatAssets

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font50    = fontGeneratorSlotCity_Black.generateFont(parameter.setSize(50))

    private val STAKE_STEP = 10
    private val STAKE_MAX  = 100
    private val STAKE_MIN  = 10

    private val stakeFlow = MutableStateFlow(STAKE_MIN)

    // Actor
    private val staticImg   = Image(assets.STATIC_FIELD)
    private val btnBack     = AButton(this, AButton.Static.Type.BACK)
    private val balanceLbl  = Label("" + game.balance.balanceFlow.value, Label.LabelStyle(font50, Color.WHITE))
    private val stakeLbl    = Label("" + stakeFlow.value, Label.LabelStyle(font50, Color.WHITE))
    private val minusBtn    = AButton(this, AButton.Static.Type.MINUS)
    private val plusBtn     = AButton(this, AButton.Static.Type.PLUS)
    private val spinBtn     = AButton(this, AButton.Static.CarnavalCatType.SPIN)
    private val autoSpinBtn = AButton(this, AButton.Static.CarnavalCatType.AUTO_SPIN)
    private val speedBtn    = ACheckBox(this, ACheckBox.Static.CarnavalCatType.SPEED)
    private val slotGroup   = SlotGroup(this)

    // Field
    private val clickableList = listOf(minusBtn, plusBtn, spinBtn, autoSpinBtn)

    override fun show() {
        stageBack.root.animHide()
        stageUI.root.animHide()
        setBackBackground(assets.BACKGROUND.region)
        super.show()
        stageBack.root.animShow(TIME_ANIM_ALPHA) { stageUI.root.animShow(TIME_ANIM_ALPHA) }

        game.musicUtil.apply {
            coff  = 0.25f
            music = game.carnavalCatMusic.MUSIC.apply { isLooping = true }
        }
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addStaticImg()
        addBack()
        addBalanceAndStake()
        addMinusPlusBtn()
        addSpinBtn()
        addAutoSpinBtn()
        addSpeedBtn()
        addSlotGroupImg()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addStaticImg() {
        addActor(staticImg)
        staticImg.setBounds(43f, 79f, 1575f, 946f)
    }

    private fun AdvancedStage.addBack() {
        addActor(btnBack)
        btnBack.apply {
            setBounds(115f, 26f, 95f, 95f)
            setOnClickListener { stageUI.root.animHide(TIME_ANIM_ALPHA) {
                stageBack.root.animHide(TIME_ANIM_ALPHA) { game.navigationManager.back() }
            } }
        }
    }

    private fun AdvancedStage.addBalanceAndStake() {
        addActors(balanceLbl, stakeLbl)
        balanceLbl.setBounds(498f, 16f, 295f, 60f)
        stakeLbl.setBounds(1247f, 16f, 173f, 60f)
        balanceLbl.setAlignment(Align.center)
        stakeLbl.setAlignment(Align.center)

        coroutine?.launch {
            launch {
                game.balance.balanceFlow.collect {
                    runGDX { balanceLbl.setText("$it$") }
                }
            }
            launch {
                stakeFlow.collect { stake -> runGDX { stakeLbl.setText("$stake$") } }
            }
        }
    }

    private fun AdvancedStage.addMinusPlusBtn() {
        addActors(minusBtn, plusBtn)
        minusBtn.apply {
            setBounds(1132f, 16f, 94f, 94f)
            setOnClickListener {
                if (stakeFlow.value - STAKE_STEP >= STAKE_MIN) stakeFlow.value -= STAKE_STEP
            }
        }
        plusBtn.apply {
            setBounds(1441f, 16f, 94f, 94f)
            setOnClickListener {
                if (stakeFlow.value + STAKE_STEP <= STAKE_MAX) stakeFlow.value += STAKE_STEP
            }
        }
    }

    private fun AdvancedStage.addSpinBtn() {
        addActor(spinBtn)
        spinBtn.apply {
            setBounds(1649f, 465f, 256f, 259f)

            setOnClickListener(game.soundUtil.start) {
                game.balance.balanceFlow.value -= stakeFlow.value

                clickableList.onEach { it.disable() }

                coroutine?.launch {
                    val isWin = slotGroup.spin()
                    if (isWin) screen.game.balance.balanceFlow.value += (stakeFlow.value * (1..10).random())

                    clickableList.onEach { it.enable() }
                }
            }
        }
    }

    private fun AdvancedStage.addAutoSpinBtn() {
        addActor(autoSpinBtn)
        autoSpinBtn.apply {
            setBounds(1701f, 764f, 155f, 156f)

            val autoClickableList = clickableList.toMutableList().apply { remove(autoSpinBtn) }

            var state = Static.AutoSpinState.DEFAULT

            setOnClickListener {
                when (state) {
                    Static.AutoSpinState.DEFAULT -> {
                        state = Static.AutoSpinState.START

                        autoSpinBtn.press()
                        autoClickableList.onEach { it.disable() }

                        coroutine?.launch {
                            while (isActive && state == Static.AutoSpinState.START) {
                                game.balance.balanceFlow.value -= stakeFlow.value

                                val isWin = slotGroup.spin()
                                if (isWin) screen.game.balance.balanceFlow.value += (stakeFlow.value * (1..10).random())

                                delay(700)
                            }

                            autoClickableList.onEach { it.enable() }
                            autoSpinBtn.enable()
                            state = Static.AutoSpinState.DEFAULT
                        }
                    }
                    Static.AutoSpinState.START -> {
                        state = Static.AutoSpinState.STOP
                        autoSpinBtn.disable()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun AdvancedStage.addSpeedBtn() {
        addActor(speedBtn)
        speedBtn.apply {
            setBounds(1694f, 269f, 155f, 156f)

            setOnCheckListener { isCheck -> SlotGroup.isSpeed = isCheck }
        }
    }

    private fun AdvancedStage.addSlotGroupImg() {
        addActor(slotGroup)
        slotGroup.setBounds(372f, 194f, 1173f, 686f)
    }

    // ---------------------------------------------------
    // class
    // ---------------------------------------------------

    object Static {
        enum class AutoSpinState {
            DEFAULT, START, STOP
        }
    }


}