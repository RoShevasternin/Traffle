package com.gorillaz.puzzlegame.game.actors.main

import com.gorillaz.puzzlegame.game.actors.ALevelBlocked
import com.gorillaz.puzzlegame.game.actors.button.AImageButton
import com.gorillaz.puzzlegame.game.actors.panel.APanelIncreaseJackpot
import com.gorillaz.puzzlegame.game.actors.panel.APanelLocation
import com.gorillaz.puzzlegame.game.actors.panel.APanelMain
import com.gorillaz.puzzlegame.game.actors.panel.APanelMaxBet
import com.gorillaz.puzzlegame.game.actors.panel.APanelSelectLocation
import com.gorillaz.puzzlegame.game.screens.GameScreen
import com.gorillaz.puzzlegame.game.screens.PlayScreen
import com.gorillaz.puzzlegame.game.utils.Block
import com.gorillaz.puzzlegame.game.utils.MAX_LEVEL_JACKPOT
import com.gorillaz.puzzlegame.game.utils.TIME_ANIM_SCREEN
import com.gorillaz.puzzlegame.game.utils.actor.animDelay
import com.gorillaz.puzzlegame.game.utils.actor.animHide
import com.gorillaz.puzzlegame.game.utils.actor.animShow
import com.gorillaz.puzzlegame.game.utils.actor.disable
import com.gorillaz.puzzlegame.game.utils.actor.enable
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedMainGroup
import com.gorillaz.puzzlegame.game.utils.gdxGame

class AMainPlay(
    override val screen: PlayScreen,
): AdvancedMainGroup() {

    private val valueLevel = gdxGame.ds_Level.flow.value

    private val aPanelMain            = APanelMain(screen)
    private val btnBack               = AImageButton(screen, gdxGame.assetsAll.arrow)
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

    private fun addAPanelLocation() {
        addActor(aPanelLocation)
        aPanelLocation.setBounds(45f, 239f, 1035f, 909f)
    }

    private fun addAPanelMaxBet() {
        addActor(aPanelMaxBet)
        aPanelMaxBet.setBounds(373f, 1116f, 673f, 234f)
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
        aPanelSelectLocation.setBounds(6f, 0f, 686f, 316f)
    }

    private fun addAPanelIncreaseJackpot() {
        addActor(aPanelIncreaseJackpot)
        aPanelIncreaseJackpot.setBounds(733f, 0f, 336f, 426f)

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
        aLevelBlocked.setBounds(0f, 423f, 1080f, 421f)
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