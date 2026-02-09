package com.sugargalaxy.candyrunio.game.actors.main

import com.sugargalaxy.candyrunio.game.actors.ALevelBlocked
import com.sugargalaxy.candyrunio.game.actors.button.AButton
import com.sugargalaxy.candyrunio.game.actors.button.AImageButton
import com.sugargalaxy.candyrunio.game.actors.panel.APanelIncreaseJackpot
import com.sugargalaxy.candyrunio.game.actors.panel.APanelLocation
import com.sugargalaxy.candyrunio.game.actors.panel.APanelMain
import com.sugargalaxy.candyrunio.game.actors.panel.APanelMaxBet
import com.sugargalaxy.candyrunio.game.actors.panel.APanelSelectLocation
import com.sugargalaxy.candyrunio.game.screens.GameScreen
import com.sugargalaxy.candyrunio.game.screens.PlayScreen
import com.sugargalaxy.candyrunio.game.utils.Block
import com.sugargalaxy.candyrunio.game.utils.MAX_LEVEL_JACKPOT
import com.sugargalaxy.candyrunio.game.utils.TIME_ANIM_SCREEN
import com.sugargalaxy.candyrunio.game.utils.actor.animDelay
import com.sugargalaxy.candyrunio.game.utils.actor.animHide
import com.sugargalaxy.candyrunio.game.utils.actor.animShow
import com.sugargalaxy.candyrunio.game.utils.actor.disable
import com.sugargalaxy.candyrunio.game.utils.advanced.AdvancedMainGroup
import com.sugargalaxy.candyrunio.game.utils.gdxGame

class AMainPlay(
    override val screen: PlayScreen,
): AdvancedMainGroup() {

    private val valueLevel = gdxGame.ds_Level.flow.value

    private val aPanelMain            = APanelMain(screen)
    private val btnBack               = AButton(screen, AButton.Type.Back)
    private val aPanelMaxBet          = APanelMaxBet(screen)
    private val aPanelSelectLocation  = APanelSelectLocation(screen)
    private val aPanelIncreaseJackpot = APanelIncreaseJackpot(screen)
    private val aPanelLocation        = APanelLocation(screen)
    private val aLevelBlocked         = ALevelBlocked(screen)

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

    private fun addAPanelLocation() {
        addActor(aPanelLocation)
        aPanelLocation.setBounds(41f, 371f, 1017f, 757f)
    }

    private fun addAPanelMaxBet() {
        addActor(aPanelMaxBet)
        aPanelMaxBet.setBounds(406f, 1441f, 635f, 204f)
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
                    gdxGame.navigationManager.navigate(GameScreen::class.java.name, screen::class.java.name)
                }
            }
        }

        addActor(aPanelSelectLocation)
        aPanelSelectLocation.setBounds(6f, -64f, 686f, 380f)
    }

    private fun addAPanelIncreaseJackpot() {
        addActor(aPanelIncreaseJackpot)
        aPanelIncreaseJackpot.setBounds(733f, -64f, 336f, 490f)

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
        aLevelBlocked.setBounds(0f, 422f, 1080f, 358f)
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