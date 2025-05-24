package com.colderonetrains.battlesskates.game.actors.main

import com.colderonetrains.battlesskates.game.actors.ABoxBIP
import com.colderonetrains.battlesskates.game.actors.AItemTrick
import com.colderonetrains.battlesskates.game.actors.AMenu
import com.colderonetrains.battlesskates.game.actors.ARandom_Description
import com.colderonetrains.battlesskates.game.actors.ARandom_RollAgain
import com.colderonetrains.battlesskates.game.actors.ARandom_RollTrick
import com.colderonetrains.battlesskates.game.actors.AScrollerItem
import com.colderonetrains.battlesskates.game.actors.ATopBack
import com.colderonetrains.battlesskates.game.data.DataTrick
import com.colderonetrains.battlesskates.game.dataStore.LevelType
import com.colderonetrains.battlesskates.game.screens.RandomScreen
import com.colderonetrains.battlesskates.game.screens.TrickCatalogScreen
import com.colderonetrains.battlesskates.game.utils.Block
import com.colderonetrains.battlesskates.game.utils.GLOBAL_listDataTrick_Beginner
import com.colderonetrains.battlesskates.game.utils.GLOBAL_listDataTrick_Intermediate
import com.colderonetrains.battlesskates.game.utils.GLOBAL_listDataTrick_Pro
import com.colderonetrains.battlesskates.game.utils.TIME_ANIM_SCREEN
import com.colderonetrains.battlesskates.game.utils.WIDTH_UI
import com.colderonetrains.battlesskates.game.utils.actor.animDelay
import com.colderonetrains.battlesskates.game.utils.actor.animHide
import com.colderonetrains.battlesskates.game.utils.actor.animShow
import com.colderonetrains.battlesskates.game.utils.actor.disable
import com.colderonetrains.battlesskates.game.utils.actor.enable
import com.colderonetrains.battlesskates.game.utils.actor.setPosition
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedMainGroup
import com.colderonetrains.battlesskates.game.utils.currentTimeMinus
import com.colderonetrains.battlesskates.game.utils.gdxGame

class AMainRandom(override val screen: RandomScreen): AdvancedMainGroup() {

    private val aTopBack = ATopBack(screen, "Random Trick")
    private val aBoxBIP  = ABoxBIP(screen)
    private val aMenu    = AMenu(screen, AMenu.CheckType.RANDOM)

    private val aRollTrick   = ARandom_RollTrick(screen)
    private val aRollAgain   = ARandom_RollAgain(screen)

    private var aDescription: ARandom_Description? = null

    private var selectedLevelType = LevelType.Beginner

    override fun addActorsOnGroup() {
        color.a = 0f

        addATopBack()
        addABoxBIP()
        addAMenu()
        addARolls()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addATopBack() {
        addActor(aTopBack)
        aTopBack.setBounds(20f, 1663f, 491f, 115f)
    }

    private fun addABoxBIP() {
        addActor(aBoxBIP)
        aBoxBIP.setBounds(86f, 1505f, 762f, 88f)

        aBoxBIP.apply {
            blockBEGINNER = {
                selectedLevelType = LevelType.Beginner
            }
            blockINTERMEDIATE = {
                selectedLevelType = LevelType.Intermediate
            }
            blockPRO = {
                selectedLevelType = LevelType.Pro
            }
        }
    }

    private fun addAMenu() {
        addActor(aMenu)
        aMenu.setBounds(50f, 45f, 980f, 180f)
    }

    private fun addARolls() {
        addActors(aRollTrick, aRollAgain)
        aRollTrick.setBounds(206f, 1016f, 669f, 165f)
        aRollAgain.setBounds(86f, 1316f, 908f, 127f)

        aRollAgain.color.a = 0f
        aRollAgain.disable()

        aRollTrick.blockClick = {
            aRollTrick.animHide(0.25f)
            aRollAgain.animShow(0.25f)
            addADescription()
        }
        aRollAgain.blockClick = {
            aRollAgain.disable()
            addADescription()
        }
    }

    private fun addADescription() {
        aDescription?.let {
            it.clearActions()
            it.animHide(0.25f) { it.dispose() }
        }

        screen.stageUI.root.animDelay(0.3f) {
            aDescription = getADescription()
            aDescription?.let {
                it.color.a = 0f
                addActor(it)
                it.setBounds(86f, 255f, 908f, 999f)
                it.animShow(0.25f) {
                    aRollAgain.enable()
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

    // Logic -------------------------------------------------

    private fun getADescription() = ARandom_Description(
        screen,
        gdxGame.assetsAll.listIcon.random(),
        when(selectedLevelType) {
            LevelType.Beginner     -> GLOBAL_listDataTrick_Beginner
            LevelType.Intermediate -> GLOBAL_listDataTrick_Intermediate
            LevelType.Pro          -> GLOBAL_listDataTrick_Pro
        }.random(),
        selectedLevelType
    )

}