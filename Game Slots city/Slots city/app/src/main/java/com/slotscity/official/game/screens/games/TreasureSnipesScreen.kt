package com.slotscity.official.game.screens.games

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.slotscity.official.game.LibGDXGame
import com.slotscity.official.game.actors.button.AButton
import com.slotscity.official.game.actors.treasure_snipes.slot5x3.SlotGroup
import com.slotscity.official.game.utils.GameColor
import com.slotscity.official.game.utils.TIME_ANIM_ALPHA
import com.slotscity.official.game.utils.actor.animHide
import com.slotscity.official.game.utils.actor.animShow
import com.slotscity.official.game.utils.actor.disable
import com.slotscity.official.game.utils.actor.enable
import com.slotscity.official.game.utils.actor.setOnClickListener
import com.slotscity.official.game.utils.advanced.AdvancedScreen
import com.slotscity.official.game.utils.advanced.AdvancedStage
import com.slotscity.official.game.utils.font.FontParameter
import com.slotscity.official.game.utils.region
import com.slotscity.official.game.utils.runGDX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TreasureSnipesScreen(override val game: LibGDXGame): AdvancedScreen() {

    private val assets = game.treasureSnipesAssets

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font16    = fontGeneratorSlotCity_Black.generateFont(parameter.setSize(16))

    private val STAKE_STEP = 5
    private val STAKE_MAX  = 50
    private val STAKE_MIN  = 5

    private val stakeFlow = MutableStateFlow(STAKE_MIN)

    // Actor
    private val panelImg    = Image(assets.PANEL)
    private val balanceLbl  = Label("" + game.balance.balanceFlow.value, Label.LabelStyle(font16, GameColor.gray))
    private val stakeLbl    = Label("" + stakeFlow.value, Label.LabelStyle(font16, GameColor.gray))
    private val minusBtn    = Actor()
    private val plusBtn     = Actor()
    private val spinBtn     = AButton(this, AButton.Static.TreasureSnipesType.SPIN)
    private val slotGroup   = SlotGroup(this)

    // Field
    private val clickableList = listOf(minusBtn, plusBtn, spinBtn)

    override fun show() {
        stageBack.root.animHide()
        stageUI.root.animHide()
        setBackBackground(assets.BACKGROUND.region)
        super.show()
        stageBack.root.animShow(TIME_ANIM_ALPHA) { stageUI.root.animShow(TIME_ANIM_ALPHA) }

        game.musicUtil.apply {
            coff  = 0.25f
            music = game.treasureSnipesMusic.MUSIC.apply { isLooping = true }
        }
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addPanelImg()
        addBack()
        addBalanceAndStake()
        addMinusPlusBtn()
        addSpinBtn()
        addSlotGroupImg()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addPanelImg() {
        addActor(panelImg)
        panelImg.setBounds(71f, 30f, 1351f, 70f)
    }

    private fun AdvancedStage.addBack() {
        val back = Actor()
        addActor(back)
        back.apply {
            setBounds(30f, 0f, 152f, 131f)
            setOnClickListener(game.soundUtil) { stageUI.root.animHide(TIME_ANIM_ALPHA) {
                stageBack.root.animHide(TIME_ANIM_ALPHA) { game.navigationManager.back() }
            } }
        }
    }

    private fun AdvancedStage.addBalanceAndStake() {
        addActors(balanceLbl, stakeLbl)
        balanceLbl.setBounds(1194f, 37f, 208f, 19f)
        stakeLbl.setBounds(557f, 40f, 115f, 19f)
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
            setBounds(490f, 24f, 60f, 50f)
            setOnClickListener(game.soundUtil) {
                if (stakeFlow.value - STAKE_STEP >= STAKE_MIN) stakeFlow.value -= STAKE_STEP
            }
        }
        plusBtn.apply {
            setBounds(679f, 24f, 60f, 50f)
            setOnClickListener(game.soundUtil) {
                if (stakeFlow.value + STAKE_STEP <= STAKE_MAX) stakeFlow.value += STAKE_STEP
            }
        }
    }

    private fun AdvancedStage.addSpinBtn() {
        addActor(spinBtn)
        spinBtn.apply {
            setBounds(887f, 0f, 146f, 133f)

            setOnClickListener(game.soundUtil.start) {
                game.balance.balanceFlow.value -= stakeFlow.value

                clickableList.onEach { it.disable() }

                coroutine?.launch {
                    val isWin = slotGroup.spin()
                    if (isWin) screen.game.balance.balanceFlow.value += (stakeFlow.value * (1..7).random())

                    clickableList.onEach { it.enable() }
                }
            }
        }
    }

    private fun AdvancedStage.addSlotGroupImg() {
        addActor(slotGroup)
        slotGroup.setBounds(376f, 141f, 1177f, 635f)
    }

}