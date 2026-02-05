/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.actors.main

import com.novaburst.pixelrally.game.actors.ALevelBlocked
import com.novaburst.pixelrally.game.actors.button.InteractiveButton
import com.novaburst.pixelrally.game.actors.button.IconButton
import com.novaburst.pixelrally.game.actors.panel.APanelIncreaseJackpot
import com.novaburst.pixelrally.game.actors.panel.APanelLocation
import com.novaburst.pixelrally.game.actors.panel.APanelMain
import com.novaburst.pixelrally.game.actors.panel.APanelMaxBet
import com.novaburst.pixelrally.game.actors.panel.APanelSelectLocation
import com.novaburst.pixelrally.game.screens.PlayDisplay
import com.novaburst.pixelrally.game.screens.PlayScreen
import com.novaburst.pixelrally.game.utils.Block
import com.novaburst.pixelrally.game.utils.MAX_LEVEL_JACKPOT
import com.novaburst.pixelrally.game.utils.TIME_ANIM_SCREEN
import com.novaburst.pixelrally.game.utils.actor.animDelay
import com.novaburst.pixelrally.game.utils.actor.animHide
import com.novaburst.pixelrally.game.utils.actor.animShow
import com.novaburst.pixelrally.game.utils.actor.disable
import com.novaburst.pixelrally.game.utils.advanced.PrimaryContainer
import com.novaburst.pixelrally.game.utils.gdxGame

class AMainPlay(
    override val screen: PlayScreen,
): PrimaryContainer() {

    private val valueLevel = gdxGame.ds_Level.flow.value

    private val aPanelMain = APanelMain(screen)
    private val btnBack               = InteractiveButton(screen, InteractiveButton.Type.Back)
    private val aPanelMaxBet          = APanelMaxBet(screen)
    private val aPanelSelectLocation  = APanelSelectLocation(screen)
    private val aPanelIncreaseJackpot = APanelIncreaseJackpot(screen)
    private val aPanelLocation = APanelLocation(screen)
    private val aLevelBlocked = ALevelBlocked(screen)

    // Handler method
    override fun addActorsOnGroup() {
        color.a = 0f

        addAPanelMain()
        addBtnBack()
        addAPanelLocation()
        addAPanelMaxBet()
        addAPanelSelectLocation()
        addAPanelIncreaseJackpot()
        addALevelBlocked()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    // Core functionality
    private fun addAPanelMain() {
        addActor(aPanelMain)
        aPanelMain.setBounds(-45f, 1641f, 611f, 279f)
    }

    // Processing logic
    private fun addBtnBack() {
        addActor(btnBack)
        btnBack.setBounds(948f, 1792f, 104f, 104f)
        btnBack.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    // Handler method
    private fun addAPanelLocation() {
        addActor(aPanelLocation)
        aPanelLocation.setBounds(25f, 597f, 1024f, 666f)
    }

    // Function implementation
    private fun addAPanelMaxBet() {
        addActor(aPanelMaxBet)
        aPanelMaxBet.setBounds(455f, 1376f, 611f, 249f)
    }

    private fun addAPanelSelectLocation() {
        aPanelSelectLocation.apply {
            blockNext = { dataLocation ->
                this@AMainPlay.screen.aBackground.animToNewTexture(gdxGame.assetsAll.listBackground[dataLocation.index], TIME_ANIM_SCREEN)
                gdxGame.currentBackground = gdxGame.assetsAll.listBackground[dataLocation.index]

                aPanelLocation.update(dataLocation)
                aPanelMaxBet.updateMaxBet(dataLocation.maxBet)

                updateAPanelIncreaseJackpot()
                checkIsLocationAvailable()
            }
            blockPlay = {
                PlayScreen.CURRENT_LOCATION_INDEX = aPanelSelectLocation.currentLocationIndex
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(PlayDisplay::class.java.name, screen::class.java.name)
                }
            }
        }

        addActor(aPanelSelectLocation)
        aPanelSelectLocation.setBounds(-27f, -134f, 778f, 494f)
    }

    private fun addAPanelIncreaseJackpot() {
        addActor(aPanelIncreaseJackpot)
        aPanelIncreaseJackpot.setBounds(713f, -159f, 554f, 633f)

        aPanelIncreaseJackpot.blockUp = {
            aPanelIncreaseJackpot.disableBtnUp()

            val currentDataLocation = aPanelSelectLocation.currentDataLocation
            val currentLevelJackpot = gdxGame.ds_LevelJeckpot.flow.value[currentDataLocation.index]

            if (currentLevelJackpot < MAX_LEVEL_JACKPOT) {
                val priceUp = currentDataLocation.listPriceUp[currentLevelJackpot]

                if (gdxGame.ds_Gems.flow.value >= priceUp) {
                    gdxGame.ds_LevelJeckpot.update { list ->
                        val mList = list.toMutableList()
                        mList[currentDataLocation.index] = mList[currentDataLocation.index] + 1
                        mList
                    }
                    gdxGame.ds_Gems.update { it - priceUp }

                    this.animDelay(0.250f) {
                        updateAPanelIncreaseJackpot()
                        aPanelIncreaseJackpot.enableBtnUp()
                    }
                }
            }

        }
    }

    private fun addALevelBlocked() {
        addActor(aLevelBlocked)
        aLevelBlocked.setBounds(0f, 422f, 1080f, 256f)
        aLevelBlocked.color.a = 0f
        aLevelBlocked.disable()
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

    private fun updateAPanelIncreaseJackpot() {
        val currentDataLocation = aPanelSelectLocation.currentDataLocation
        val currentLevelJackpot = gdxGame.ds_LevelJeckpot.flow.value[currentDataLocation.index]

        if (currentLevelJackpot == MAX_LEVEL_JACKPOT) aPanelIncreaseJackpot.updateToMaxJackpot()
        else {
            aPanelIncreaseJackpot.updateToUpJackpot()

            val priceUp = currentDataLocation.listPriceUp[currentLevelJackpot]
            aPanelIncreaseJackpot.updatePrice(priceUp)

            if (gdxGame.ds_Gems.flow.value >= priceUp) aPanelIncreaseJackpot.enableBtnUp()
        }

        aPanelLocation.updatePercent(currentLevelJackpot)
    }

    private fun checkIsLocationAvailable() {
        val currentDataLocation = aPanelSelectLocation.currentDataLocation

        aLevelBlocked.updateLevel(currentDataLocation.levelAvailable)

        if (valueLevel >= currentDataLocation.levelAvailable) {
            aLevelBlocked.animHide(TIME_ANIM_SCREEN)
            aPanelSelectLocation.enableBtnPlay()
            aPanelIncreaseJackpot.enableBtnUp()
        } else {
            aLevelBlocked.animShow(TIME_ANIM_SCREEN)
            aPanelSelectLocation.disableBtnPlay()
            aPanelIncreaseJackpot.disableBtnUp()
        }
    }


}