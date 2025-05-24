package com.colderonetrains.battlesskates.game.actors.main

import com.colderonetrains.battlesskates.game.actors.ABoxBIP
import com.colderonetrains.battlesskates.game.actors.AItemTrick
import com.colderonetrains.battlesskates.game.actors.AMenu
import com.colderonetrains.battlesskates.game.actors.AScrollerItem
import com.colderonetrains.battlesskates.game.actors.ATopBack
import com.colderonetrains.battlesskates.game.dataStore.LevelType
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
import com.colderonetrains.battlesskates.game.utils.actor.setPosition
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedMainGroup
import com.colderonetrains.battlesskates.game.utils.gdxGame

class AMainTrickCatalog(override val screen: TrickCatalogScreen): AdvancedMainGroup() {

    private val aTopBack = ATopBack(screen, "Trick Catalog")
    private val aBoxBIP  = ABoxBIP(screen)
    private val aMenu    = AMenu(screen, AMenu.CheckType.TRACK_CATALOG)

    private val aScrollerItem_Beginner     = AScrollerItem(screen, gdxGame.assetsAll.listIcon.take(10), GLOBAL_listDataTrick_Beginner, LevelType.Beginner)
    private val aScrollerItem_Intermediate = AScrollerItem(screen, gdxGame.assetsAll.listIcon.shuffled().take(10), GLOBAL_listDataTrick_Intermediate, LevelType.Intermediate)
    private val aScrollerItem_Pro          = AScrollerItem(screen, gdxGame.assetsAll.listIcon.shuffled().take(10), GLOBAL_listDataTrick_Pro, LevelType.Pro)

    private var currentAScroller = aScrollerItem_Beginner

    override fun addActorsOnGroup() {
        color.a = 0f

        addATopBack()
        addABoxBIP()
        addAMenu()
        addAScrolls()

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
                currentAScroller.x = WIDTH_UI + 1000
                currentAScroller = aScrollerItem_Beginner
                currentAScroller.x = 85f
            }
            blockINTERMEDIATE = {
                currentAScroller.x = WIDTH_UI + 1000
                currentAScroller = aScrollerItem_Intermediate
                currentAScroller.x = 85f
            }
            blockPRO = {
                currentAScroller.x = WIDTH_UI + 1000
                currentAScroller = aScrollerItem_Pro
                currentAScroller.x = 85f
            }
        }
    }

    private fun addAMenu() {
        addActor(aMenu)
        aMenu.setBounds(50f, 45f, 980f, 180f)
    }

    private fun addAScrolls() {
        addActors(aScrollerItem_Beginner, aScrollerItem_Intermediate, aScrollerItem_Pro)
        aScrollerItem_Beginner.setBounds(85f, 227f, 910f, 1231f)
        aScrollerItem_Intermediate.setBounds(WIDTH_UI + 1000, 227f, 910f, 1231f)
        aScrollerItem_Pro.setBounds(WIDTH_UI + 1000, 227f, 910f, 1231f)
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

}