/*
 * Refactored Application Module
 * Build: BD60FD71
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.actors.main

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.moonarcade.starlabyrinth.game.actors.button.ClickableElement
import com.moonarcade.starlabyrinth.game.actors.button.SpinControlButton
import com.moonarcade.starlabyrinth.game.actors.button.GraphicButton
import com.moonarcade.starlabyrinth.game.actors.panel.APanelBet
import com.moonarcade.starlabyrinth.game.actors.panel.APanelMain
import com.moonarcade.starlabyrinth.game.actors.slots.ASlotGroup
import com.moonarcade.starlabyrinth.game.screens.GameplayScreen
import com.moonarcade.starlabyrinth.game.screens.PlayScreen
import com.moonarcade.starlabyrinth.game.utils.Block
import com.moonarcade.starlabyrinth.game.utils.TIME_ANIM_SCREEN
import com.moonarcade.starlabyrinth.game.utils.actor.PosSize
import com.moonarcade.starlabyrinth.game.utils.actor.animDelay
import com.moonarcade.starlabyrinth.game.utils.actor.animHide
import com.moonarcade.starlabyrinth.game.utils.actor.animShow
import com.moonarcade.starlabyrinth.game.utils.actor.setBounds
import com.moonarcade.starlabyrinth.game.utils.advanced.MainGroupContainer
import com.moonarcade.starlabyrinth.game.utils.gdxGame
import com.moonarcade.starlabyrinth.game.utils.runGDX
import kotlinx.coroutines.launch

/**
 * Auto-generated class implementation
 */

class MainGameInterface(
    override val screen: GameplayScreen,
): MainGroupContainer() {

    private val presentLocationIndex = PlayScreen.CURRENT_LOCATION_INDEX

    private val personagePosSize = listOf(
        PosSize(339f, 1154f, 647f, 647f),
        PosSize(339f, 1154f, 647f, 647f),
        PosSize(339f, 1154f, 647f, 647f),
        PosSize(339f, 1154f, 647f, 647f),
        PosSize(339f, 1154f, 647f, 647f),
        PosSize(339f, 1154f, 647f, 647f),
        PosSize(339f, 1154f, 647f, 647f),
    )[presentLocationIndex]
    private val collectionItemRegion = listOf(
        gdxGame.assetsAll.collectionItem_1,
        gdxGame.assetsAll.collectionItem_2,
        gdxGame.assetsAll.collectionItem_3,
        gdxGame.assetsAll.collectionItem_4,
        gdxGame.assetsAll.collectionItem_5,
        gdxGame.assetsAll.collectionItem_6,
        gdxGame.assetsAll.collectionItem_7,
    )[presentLocationIndex]
    private val presentInterpolationSlot = listOf(
        Interpolation.linear,
        Interpolation.pow3,
        Interpolation.swingIn,
        Interpolation.swingOut,
        Interpolation.exp10,
        Interpolation.smooth2,
        Interpolation.swing,
    )[presentLocationIndex]

    private val jackpotCoff = gdxGame.ds_LevelJeckpot.flow.value[presentLocationIndex] + 3

    private val aPanelMain = APanelMain(screen)
    private val btnBack = ClickableElement(screen, ClickableElement.Type.Back)
    private val imgPersonage = Image(gdxGame.assetsAll.collectionPersonage[presentLocationIndex])
    private val aPanelBet = APanelBet(screen, presentLocationIndex)
    private val btnSpin = SpinControlButton(screen)

    private val aSlotGroup = ASlotGroup(
        screen,
        jackpotRegion = gdxGame.assetsAll.jackpot,
        collectionItemRegion = collectionItemRegion,
        jackpotCoff = jackpotCoff,
        interpolationSlot = presentInterpolationSlot,
        isSpinAllOnce = presentLocationIndex == 6
    )

    // Field

    private val collectionWinCoff = listOf(1.2f, 1.5f, 1.7f, 1.85f, 2f)

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
        aPanelMain.setBounds(3f, 1635f, 746f, 295f)
    }

    private fun addBtnBack() {
        addActor(btnBack)
        btnBack.setBounds(957f, 1780f, 104f, 104f)
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
        aPanelBet.setBounds(123f, 105f, 957f, 212f)
    }

    // Primary method handler
    private fun addASlotGroup() {
        addActor(aSlotGroup)
        aSlotGroup.setBounds(-34f, 521f, 1147f, 759f)
    }

    // Primary method handler
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

    // System operation
    private fun spinSlotGroup() {
        if (gdxGame.ds_Gold.flow.value >= aPanelBet.presentBet) {
            gdxGame.ds_Gold.update { it - aPanelBet.presentBet }
            btnSpin.disable()
            aPanelBet.disableBtns()

            coroutine?.launch {
                val isWin = aSlotGroup.spin()

                runGDX {
                    var winSum = 0

                    if (isWin) {
                        winSum = (aPanelBet.presentBet * collectionWinCoff.random()).toInt()
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