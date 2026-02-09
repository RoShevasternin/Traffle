package com.sugargalaxy.candyrunio.game.actors.main

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.sugargalaxy.candyrunio.game.actors.button.AButton
import com.sugargalaxy.candyrunio.game.actors.button.AButtonSpin
import com.sugargalaxy.candyrunio.game.actors.button.AImageButton
import com.sugargalaxy.candyrunio.game.actors.panel.APanelBet
import com.sugargalaxy.candyrunio.game.actors.panel.APanelMain
import com.sugargalaxy.candyrunio.game.actors.slots.ASlotGroup
import com.sugargalaxy.candyrunio.game.screens.GameScreen
import com.sugargalaxy.candyrunio.game.screens.PlayScreen
import com.sugargalaxy.candyrunio.game.utils.Block
import com.sugargalaxy.candyrunio.game.utils.TIME_ANIM_SCREEN
import com.sugargalaxy.candyrunio.game.utils.actor.PosSize
import com.sugargalaxy.candyrunio.game.utils.actor.animDelay
import com.sugargalaxy.candyrunio.game.utils.actor.animHide
import com.sugargalaxy.candyrunio.game.utils.actor.animShow
import com.sugargalaxy.candyrunio.game.utils.actor.setBounds
import com.sugargalaxy.candyrunio.game.utils.advanced.AdvancedMainGroup
import com.sugargalaxy.candyrunio.game.utils.gdxGame
import com.sugargalaxy.candyrunio.game.utils.runGDX
import kotlinx.coroutines.launch

class AMainGame(
    override val screen: GameScreen,
): AdvancedMainGroup() {

    private val currentLocationIndex = PlayScreen.CURRENT_LOCATION_INDEX

    private val personagePosSize = listOf(
        PosSize(135f, 826f, 1024f, 1024f),
        PosSize(135f, 826f, 1024f, 1024f),
        PosSize(135f, 826f, 1024f, 1024f),
        PosSize(135f, 826f, 1024f, 1024f),
        PosSize(135f, 826f, 1024f, 1024f),
        PosSize(135f, 826f, 1024f, 1024f),
        PosSize(135f, 826f, 1024f, 1024f),
    )[currentLocationIndex]
    private val listItemRegion = listOf(
        gdxGame.assetsAll.listItem_1,
        gdxGame.assetsAll.listItem_2,
        gdxGame.assetsAll.listItem_3,
        gdxGame.assetsAll.listItem_4,
        gdxGame.assetsAll.listItem_5,
        gdxGame.assetsAll.listItem_6,
        gdxGame.assetsAll.listItem_7,
    )[currentLocationIndex]
    private val currentInterpolationSlot = listOf(
        Interpolation.linear,
        Interpolation.pow3,
        Interpolation.swingIn,
        Interpolation.swingOut,
        Interpolation.exp10,
        Interpolation.smooth2,
        Interpolation.swing,
    )[currentLocationIndex]

    private val jackpotCoff = gdxGame.ds_LevelJeckpot.flow.value[currentLocationIndex] + 3

    private val aPanelMain   = APanelMain(screen)
    private val btnBack      = AButton(screen, AButton.Type.Back)
    private val imgPersonage = Image(gdxGame.assetsAll.listPersonage[currentLocationIndex])
    private val aPanelBet    = APanelBet(screen, currentLocationIndex)
    private val btnSpin      = AButtonSpin(screen)

    private val aSlotGroup   = ASlotGroup(
        screen,
        jackpotRegion     = gdxGame.assetsAll.jackpot,
        listItemRegion    = listItemRegion,
        jackpotCoff       = jackpotCoff,
        interpolationSlot = currentInterpolationSlot,
        isSpinAllOnce     = currentLocationIndex == 6
    )

    // Field

    private val listWinCoff = listOf(1.2f, 1.5f, 1.7f, 1.85f, 2f)

    override fun addActorsOnGroup() {
        color.a = 0f

        addAPanelMain()
        addImgPersonage()
        addBtnBack()
        addAPanelBet()
        addASlotGroup()
        addBtnSpin()

        animShowMain { aPanelMain.isCollectProgress = true }
    }

    // Actors ------------------------------------------------------------------------

    private fun addAPanelMain() {
        addActor(aPanelMain)
        aPanelMain.setBounds(3f, 1641f, 698f, 279f)
    }

    private fun addBtnBack() {
        addActor(btnBack)
        btnBack.setBounds(949f, 1787f, 108f, 109f)
        btnBack.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addImgPersonage() {
        addActor(imgPersonage)
        imgPersonage.setBounds(personagePosSize)

        imgPersonage.setOrigin(Align.bottomRight)
        imgPersonage.addAction(Actions.forever(
            Actions.sequence(
                Actions.scaleBy(-0.01f, -0.01f, 0.75f, Interpolation.sineIn),
                Actions.scaleTo(1f, 1f, 0.75f, Interpolation.sineOut),
            )
        ))
    }

    private fun addAPanelBet() {
        addActor(aPanelBet)
        aPanelBet.setBounds(310f, 102f, 701f, 219f)
    }

    private fun addASlotGroup() {
        addActor(aSlotGroup)
        aSlotGroup.setBounds(0f, 348f, 1080f, 1089f)
    }

    private fun addBtnSpin() {
        addActor(btnSpin)
        btnSpin.setBounds(40f, 0f, 420f, 422f)

        btnSpin.setOnClickListener {
            spinSlotGroup()
        }
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
        animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    // Logic ----------------------------------------------------------------------

    private fun spinSlotGroup() {
        if (gdxGame.ds_Gold.flow.value >= aPanelBet.currentBet) {
            gdxGame.ds_Gold.update { it - aPanelBet.currentBet }
            btnSpin.disable()
            aPanelBet.disableBtns()

            coroutine?.launch {
                val isWin = aSlotGroup.spin()

                runGDX {
                    var winSum = 0

                    if (isWin) {
                        winSum = (aPanelBet.currentBet * listWinCoff.random()).toInt()
                        gdxGame.ds_Gold.update { it + winSum }
                        gdxGame.ds_Level.update { it + 1 }
                    }

                    gdxGame.ds_Achievement.update { dataAchievement ->
                        dataAchievement.number_of_spins += 1
                        dataAchievement.number_of_wins += if (isWin) 1 else 0

                        if (winSum > dataAchievement.maximum_winnings) {
                            dataAchievement.maximum_winnings = winSum
                        }

                        dataAchievement
                    }

                    btnSpin.enable()
                    aPanelBet.enableBtns()
                    btnSpin.resetEffect()
                }
            }

        }
    }

}