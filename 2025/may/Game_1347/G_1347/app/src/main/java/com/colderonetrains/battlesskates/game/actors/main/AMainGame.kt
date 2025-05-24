package com.colderonetrains.battlesskates.game.actors.main

import com.colderonetrains.battlesskates.game.actors.*
import com.colderonetrains.battlesskates.game.actors.autoLayout.AVerticalGroup
import com.colderonetrains.battlesskates.game.actors.autoLayout.AutoLayout
import com.colderonetrains.battlesskates.game.actors.battle_setup.AHowManySkaters
import com.colderonetrains.battlesskates.game.actors.battle_setup.AUseCustomTricks
import com.colderonetrains.battlesskates.game.actors.button.AButton
import com.colderonetrains.battlesskates.game.data.DataPlayer
import com.colderonetrains.battlesskates.game.data.DataTrick
import com.colderonetrains.battlesskates.game.screens.BattleSetupScreen
import com.colderonetrains.battlesskates.game.screens.GameScreen
import com.colderonetrains.battlesskates.game.screens.HomeScreen
import com.colderonetrains.battlesskates.game.screens.TrickDescriptionScreen
import com.colderonetrains.battlesskates.game.utils.*
import com.colderonetrains.battlesskates.game.utils.actor.*
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedMainGroup
import com.colderonetrains.battlesskates.util.log

class AMainGame(override val screen: GameScreen): AdvancedMainGroup() {

    // Data
    private val listPlayerName = BattleSetupScreen.GLOBAL_listPlayerName
    private val listDataPlayer = MutableList(listPlayerName.size) { DataPlayer(listPlayerName[it], 0) }
    private val listDataTrick  = getListDataTrick()

    // Actors
    private val aTopBack         = ATopBack(screen, "S.K.A.T.E Battle")
    private val aGameDescription = AGame_Description(screen)

    private val btnLANDED = AButton(screen, AButton.Type.LANDED)
    private val btnFAILED = AButton(screen, AButton.Type.FAILED)

    private var currentPlayerIndex = -1

    override fun addActorsOnGroup() {
        color.a = 0f

        addATopBack()
        addAGameDescription()
        addBtns()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addATopBack() {
        addActor(aTopBack)
        aTopBack.setBounds(20f, 1663f, 491f, 115f)
    }

    private fun addAGameDescription() {
        addActor(aGameDescription)
        aGameDescription.setBounds(42f, 274f, 997f, 1354f)
        nextPlayer()
    }

    private fun addBtns() {
        addActors(btnLANDED, btnFAILED)
        btnLANDED.setBounds(154f, 129f, 416f, 102f)
        btnFAILED.setBounds(615f, 129f, 312f, 102f)

        btnLANDED.setOnClickListener {
            screen.stageUI.root.disable()

            screen.imgShadow.animShow(0.3f) {
                screen.aLanded.also {
                    it.blockNextPlayer = {
                        it.disable()
                        it.animHide(0.25f)
                        screen.imgShadow.animHide(0.25f) { screen.stageUI.root.enable() }

                        nextPlayer()
                    }

                    it.enable()
                    it.animShow(0.3f)
                    gdxGame.soundUtil.apply { play(win) }
                }
            }
        }
        btnFAILED.setOnClickListener {
            listDataPlayer[currentPlayerIndex].failedCount++

            screen.stageUI.root.disable()

            screen.imgShadow.animShow(0.3f) {
                screen.aFailed.also {
                    it.blockNextPlayer = {
                        it.disable()
                        it.animHide(0.25f)
                        screen.imgShadow.animHide(0.25f) { screen.stageUI.root.enable() }

                        if (listDataPlayer[currentPlayerIndex].failedCount >= 5) {
                            listDataPlayer.removeAt(currentPlayerIndex)
                        }

                        if (listDataPlayer.size == 1) {
                            screen.hideScreen {
                                gdxGame.navigationManager.clearBackStack()
                                gdxGame.navigationManager.navigate(HomeScreen::class.java.name)
                            }
                        } else {
                            nextPlayer()
                        }
                    }

                    it.show_S_K_A_T_E(listDataPlayer[currentPlayerIndex].failedCount)
                    it.enable()
                    it.animShow(0.3f)
                    gdxGame.soundUtil.apply { play(fail) }
                }
            }
        }
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    // Logic --------------------------------------------------

    private fun getListDataTrick(): List<DataTrick> {
        val listAllTrick    = (GLOBAL_listDataTrick_Beginner + GLOBAL_listDataTrick_Intermediate + GLOBAL_listDataTrick_Pro)
        val listCustomTrick = BattleSetupScreen.GLOBAL_listCustomTrick

        return if (BattleSetupScreen.GLOBAL_isUseCustomTrick.not()) {
            listAllTrick
        } else {
            if (listCustomTrick.isEmpty()) {
                listAllTrick
            } else {
                listAllTrick.filter { it.nName in listCustomTrick }
            }
        }
    }

    private fun nextPlayer() {
        if ((currentPlayerIndex + 1) > listDataPlayer.lastIndex) currentPlayerIndex = 0 else currentPlayerIndex += 1

        aGameDescription.update(
            playerName = listPlayerName[currentPlayerIndex],
            dataTrick  = listDataTrick.random(),
            iconRegion = gdxGame.assetsAll.listIcon.random()
        )
    }

}