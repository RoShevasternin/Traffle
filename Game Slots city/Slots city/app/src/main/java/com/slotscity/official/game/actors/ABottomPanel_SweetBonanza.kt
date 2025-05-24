package com.slotscity.official.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.slotscity.official.game.actors.button.AButton
import com.slotscity.official.game.actors.checkbox.ACheckBox
import com.slotscity.official.game.utils.HEIGHT_UI
import com.slotscity.official.game.utils.TIME_ANIM_ALPHA
import com.slotscity.official.game.utils.WIDTH_UI
import com.slotscity.official.game.utils.actor.animShow
import com.slotscity.official.game.utils.actor.disable
import com.slotscity.official.game.utils.actor.enable
import com.slotscity.official.game.utils.actor.setOnClickListener
import com.slotscity.official.game.utils.advanced.AdvancedGroup
import com.slotscity.official.game.utils.advanced.AdvancedScreen
import com.slotscity.official.game.utils.font.FontParameter
import com.slotscity.official.game.utils.runGDX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ABottomPanel_SweetBonanza(override val screen: AdvancedScreen): AdvancedGroup() {

    private val assets        = screen.game.allAssets

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font60    = screen.fontGeneratorRoboto_Regular.generateFont(parameter.setSize(60))
    private val font20    = screen.fontGeneratorSlotCity_Black.generateFont(parameter.setSize(20))

    private val text1 = "Делайте ваши ставки!"
    private val text2 = "Удачи!"

    private val STAKE_STEP = 1
    private val STAKE_MAX  = 10
    private val STAKE_MIN  = 2

    private val stakeFlow = MutableStateFlow(STAKE_MIN)

    // Actor
    private val textLbl    = Label(text1, Label.LabelStyle(font60, Color.WHITE))
    private val creditImg  = Image(assets.credit)
    private val balanceLbl = Label("$" + screen.game.balance.balanceFlow.value, Label.LabelStyle(font20, Color.WHITE))
    private val stakeLbl   = Label("$" + stakeFlow.value, Label.LabelStyle(font20, Color.WHITE))
    private val soundCBox   = ACheckBox(screen, ACheckBox.Static.Type.VALUE)
    private val minusBtn   = AButton(screen, AButton.Static.Type.MINUS)
    private val plusBtn    = AButton(screen, AButton.Static.Type.PLUS)
    private val spinBtn    = ASpinBtn(screen)

    // Field
    private val clickableList = listOf(minusBtn, plusBtn)

    var spinBlock: suspend () -> Boolean = { false }

    override fun addActorsOnGroup() {
        addAndFillActor(Image(screen.drawerUtil.getRegion(Color.valueOf("0C0C0C").apply { a = 0.55f })))
        addTextLbl()
        addCreditImg()
        addSoundCBox()
        addMinusPlusBtn()
        addSpinBtn()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun addTextLbl() {
        addActor(textLbl)
        textLbl.apply {
            setBounds(592f, 42f, 627f, 70f)
            setAlignment(Align.center)
        }
    }

    private fun addCreditImg() {
        addActor(creditImg)
        creditImg.setBounds(322f, 42f, 69f, 55f)

        addActors(balanceLbl, stakeLbl)
        balanceLbl.setBounds(417f, 73f, 130f, 24f)
        stakeLbl.setBounds(417f, 41f, 130f, 24f)

        coroutine?.launch {
            launch {
                screen.game.balance.balanceFlow.collect {
                    runGDX { balanceLbl.setText("$$it") }
                }
            }
            launch {
                stakeFlow.collect { stake -> runGDX { stakeLbl.setText("$$stake") } }
            }
        }
    }

    private fun addSoundCBox() {
        addActor(soundCBox)
        soundCBox.setBounds(213f, 42f, 60f, 60f)

        if (screen.game.soundUtil.isPause.not()) soundCBox.check(false)

        soundCBox.setOnCheckListener {
            if (it) {
                screen.game.musicUtil.volumeLevelFlow.value = 100f
                screen.game.soundUtil.isPause = false
            } else {
                screen.game.musicUtil.volumeLevelFlow.value = 0f
                screen.game.soundUtil.isPause = true
            }
        }
    }

    private fun addMinusPlusBtn() {
        addActors(minusBtn, plusBtn)
        minusBtn.apply {
            setBounds(1263f, 32f, 94f, 94f)
            setOnClickListener {
                if (stakeFlow.value - STAKE_STEP >= STAKE_MIN) stakeFlow.value -= STAKE_STEP
            }
        }
        plusBtn.apply {
            setBounds(1670f, 32f, 94f, 94f)
            setOnClickListener {
                if (stakeFlow.value + STAKE_STEP <= STAKE_MAX) stakeFlow.value += STAKE_STEP
            }
        }
    }

    private fun addSpinBtn() {
        addActor(spinBtn)
        spinBtn.apply {
            setBounds(1382f, 2f, 262f, 254f)
            spinBlock = {
                screen.game.balance.balanceFlow.value -= stakeFlow.value

                textLbl.setText(text2)
                clickableList.onEach { it.disable() }

                coroutine?.launch {
                    val isWin = this@ABottomPanel_SweetBonanza.spinBlock()
                    if (isWin) screen.game.balance.balanceFlow.value += (stakeFlow.value * (1..10).random())
                    endSpin()
                }
            }
        }
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun endSpin() {
        spinBtn.stopSpin()
        textLbl.setText(text1)
        clickableList.onEach { it.enable() }
    }

}