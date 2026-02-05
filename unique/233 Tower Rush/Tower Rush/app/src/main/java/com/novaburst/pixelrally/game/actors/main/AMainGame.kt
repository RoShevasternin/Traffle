package com.novaburst.pixelrally.game.actors.main

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.novaburst.pixelrally.game.actors.button.AButton
import com.novaburst.pixelrally.game.actors.button.AButtonSpin
import com.novaburst.pixelrally.game.actors.button.AImageButton
import com.novaburst.pixelrally.game.actors.panel.APanelBet
import com.novaburst.pixelrally.game.actors.panel.APanelMain
import com.novaburst.pixelrally.game.actors.slots.ASlotGroup
import com.novaburst.pixelrally.game.screens.GameScreen
import com.novaburst.pixelrally.game.screens.PlayScreen
import com.novaburst.pixelrally.game.utils.Block
import com.novaburst.pixelrally.game.utils.TIME_ANIM_SCREEN
import com.novaburst.pixelrally.game.utils.actor.PosSize
import com.novaburst.pixelrally.game.utils.actor.animDelay
import com.novaburst.pixelrally.game.utils.actor.animHide
import com.novaburst.pixelrally.game.utils.actor.animShow
import com.novaburst.pixelrally.game.utils.actor.setBounds
import com.novaburst.pixelrally.game.utils.advanced.AdvancedMainGroup
import com.novaburst.pixelrally.game.utils.gdxGame
import com.novaburst.pixelrally.game.utils.runGDX
import kotlinx.coroutines.launch

class AMainGame(
    override val screen: GameScreen,
): AdvancedMainGroup() {

    private val currentLocationIndex = PlayScreen.CURRENT_LOCATION_INDEX

    private val personagePosSize = listOf(
        PosSize(152f, 865f, 732f, 907f),
        PosSize(187f, 962f, 666f, 839f),
        PosSize(149f, 796f, 804f, 897f),
        PosSize(221f, 870f, 658f, 878f),
        PosSize(222f, 912f, 632f, 842f),
        PosSize(228f, 858f, 698f, 931f),
        PosSize(300f, 674f, 864f, 1152f),
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
        aPanelMain.setBounds(-45f, 1641f, 611f, 279f)
    }

    private fun addBtnBack() {
        addActor(btnBack)
        btnBack.setBounds(948f, 1792f, 104f, 104f)
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
        aPanelBet.setBounds(343f, 93f, 595f, 217f)
    }

    private fun addASlotGroup() {
        addActor(aSlotGroup)
        aSlotGroup.setBounds(-166f, 396f, 1419f, 922f)
    }

    private fun addBtnSpin() {
        addActor(btnSpin)
        btnSpin.setBounds(0f, 0f, 420f, 422f)

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