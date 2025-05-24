package com.gorillaz.puzzlegame.game.actors.main

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.gorillaz.puzzlegame.game.actors.button.AButtonSpin
import com.gorillaz.puzzlegame.game.actors.button.AImageButton
import com.gorillaz.puzzlegame.game.actors.panel.APanelBet
import com.gorillaz.puzzlegame.game.actors.panel.APanelMain
import com.gorillaz.puzzlegame.game.actors.slots.ASlotGroup
import com.gorillaz.puzzlegame.game.screens.GameScreen
import com.gorillaz.puzzlegame.game.screens.PlayScreen
import com.gorillaz.puzzlegame.game.utils.Block
import com.gorillaz.puzzlegame.game.utils.TIME_ANIM_SCREEN
import com.gorillaz.puzzlegame.game.utils.actor.PosSize
import com.gorillaz.puzzlegame.game.utils.actor.animDelay
import com.gorillaz.puzzlegame.game.utils.actor.animHide
import com.gorillaz.puzzlegame.game.utils.actor.animShow
import com.gorillaz.puzzlegame.game.utils.actor.setBounds
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedMainGroup
import com.gorillaz.puzzlegame.game.utils.gdxGame
import com.gorillaz.puzzlegame.game.utils.runGDX
import kotlinx.coroutines.launch

class AMainGame(
    override val screen: GameScreen,
): AdvancedMainGroup() {

    private val currentLocationIndex = PlayScreen.CURRENT_LOCATION_INDEX

    private val personagePosSize = listOf(
        PosSize(460f, 1193f, 596f, 626f),
        PosSize(445f, 1193f, 506f, 533f),
        PosSize(518f, 1193f, 446f, 507f),
        PosSize(535f, 1193f, 369f, 560f),
        PosSize(532f, 1193f, 456f, 595f),
        PosSize(580f, 1193f, 335f, 534f),
        PosSize(541f, 1193f, 521f, 517f),
    )[currentLocationIndex]
    private val listItemRegion = listOf(
        gdxGame.assetsAll.listItem_1,
        gdxGame.assetsAll.listItem_2,
        gdxGame.assetsAll.listItem_3,
        gdxGame.assetsAll.listItem_4,
        gdxGame.assetsAll.listItem_5,
        gdxGame.assetsAll.listItem_6,
        gdxGame.assetsAll.listItem_1,
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
    private val btnBack      = AImageButton(screen, gdxGame.assetsAll.arrow)
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
        aPanelMain.setBounds(6f, 1632f, 659f, 286f)
    }

    private fun addBtnBack() {
        addActor(btnBack)
        btnBack.setBounds(932f, 1810f, 139f, 102f)
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
                Actions.scaleBy(-0.025f, -0.025f, 0.75f, Interpolation.sineIn),
                Actions.scaleTo(1f, 1f, 0.75f, Interpolation.sineOut),
            )
        ))
    }

    private fun addAPanelBet() {
        addActor(aPanelBet)
        aPanelBet.setBounds(5f, 0f, 1071f, 268f)
    }

    private fun addASlotGroup() {
        addActor(aSlotGroup)
        aSlotGroup.setBounds(0f, 410f, 1080f, 799f)
    }

    private fun addBtnSpin() {
        addActor(btnSpin)
        btnSpin.setBounds(-3f, 36f, 459f, 361f)

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