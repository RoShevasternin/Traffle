package com.colderonetrains.battlesskates.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.colderonetrains.battlesskates.game.actors.ABoxABIP
import com.colderonetrains.battlesskates.game.actors.AMenu
import com.colderonetrains.battlesskates.game.actors.AScrollerProgress
import com.colderonetrains.battlesskates.game.actors.AScrollerTrick
import com.colderonetrains.battlesskates.game.actors.button.AButton
import com.colderonetrains.battlesskates.game.screens.AchievementsScreen
import com.colderonetrains.battlesskates.game.utils.Block
import com.colderonetrains.battlesskates.game.utils.TIME_ANIM_SCREEN
import com.colderonetrains.battlesskates.game.utils.WIDTH_UI
import com.colderonetrains.battlesskates.game.utils.actor.animDelay
import com.colderonetrains.battlesskates.game.utils.actor.animHide
import com.colderonetrains.battlesskates.game.utils.actor.animShow
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedMainGroup
import com.colderonetrains.battlesskates.game.utils.gdxGame

class AMainAchievements(override val screen: AchievementsScreen): AdvancedMainGroup() {

    private val btnBack           = AButton(screen, AButton.Type.Back)
    private val imgTitle          = Image(gdxGame.assetsAll.PROGRESS_AND_ACHIVE)
    private val aBoxABIP          = ABoxABIP(screen)
    private val aMenu             = AMenu(screen, AMenu.CheckType.ACHIVE)
    private val aScrollerProgress = AScrollerProgress(screen)

    private val aScrollerTrick_All          = AScrollerTrick(screen, AScrollerTrick.TypeTrick.All)
    private val aScrollerTrick_Beginner     = AScrollerTrick(screen, AScrollerTrick.TypeTrick.Beginner)
    private val aScrollerTrick_Intermediate = AScrollerTrick(screen, AScrollerTrick.TypeTrick.Intermediate)
    private val aScrollerTrick_Pro          = AScrollerTrick(screen, AScrollerTrick.TypeTrick.Pro)

    private var currentAScroller = aScrollerTrick_All

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgTitle()
        addBtnBack()
        addABoxABIP()
        addAScrollerTrick()
        addAScrollerProgress()
        addAMenu()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addBtnBack() {
        addActor(btnBack)
        btnBack.setBounds(20f, 1663f, 124f, 115f)
        btnBack.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addImgTitle() {
        addActor(imgTitle)
        imgTitle.setBounds(61f, 1463f, 883f, 295f)
    }

    private fun addABoxABIP() {
        addActor(aBoxABIP)
        aBoxABIP.setBounds(57f, 1347f, 902f, 82f)

        aBoxABIP.apply {
            blockALL = {
                currentAScroller.x = WIDTH_UI + 1000
                currentAScroller = aScrollerTrick_All
                currentAScroller.x = 61f
            }
            blockBEGINNER = {
                currentAScroller.x = WIDTH_UI + 1000
                currentAScroller = aScrollerTrick_Beginner
                currentAScroller.x = 61f
            }
            blockINTERMEDIATE = {
                currentAScroller.x = WIDTH_UI + 1000
                currentAScroller = aScrollerTrick_Intermediate
                currentAScroller.x = 61f
            }
            blockPRO = {
                currentAScroller.x = WIDTH_UI + 1000
                currentAScroller = aScrollerTrick_Pro
                currentAScroller.x = 61f
            }
        }
    }

    private fun addAScrollerProgress() {
        addActor(aScrollerProgress)
        aScrollerProgress.setBounds(61f, 203f, 1080f, 524f)
    }

    private fun addAScrollerTrick() {
        addActors(aScrollerTrick_All, aScrollerTrick_Beginner, aScrollerTrick_Intermediate, aScrollerTrick_Pro)
        aScrollerTrick_All.setBounds(61f, 817f, 930f, 473f)
        aScrollerTrick_Beginner.setBounds(WIDTH_UI + 1000, 817f, 930f, 473f)
        aScrollerTrick_Intermediate.setBounds(WIDTH_UI + 1000, 817f, 930f, 473f)
        aScrollerTrick_Pro.setBounds(WIDTH_UI + 1000, 817f, 930f, 473f)
    }

    private fun addAMenu() {
        addActor(aMenu)
        aMenu.setBounds(50f, 45f, 980f, 180f)
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