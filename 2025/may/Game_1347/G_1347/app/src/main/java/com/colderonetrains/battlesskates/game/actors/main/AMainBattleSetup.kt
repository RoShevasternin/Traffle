package com.colderonetrains.battlesskates.game.actors.main

import com.colderonetrains.battlesskates.game.actors.*
import com.colderonetrains.battlesskates.game.actors.autoLayout.AVerticalGroup
import com.colderonetrains.battlesskates.game.actors.autoLayout.AutoLayout
import com.colderonetrains.battlesskates.game.actors.battle_setup.AHowManySkaters
import com.colderonetrains.battlesskates.game.actors.battle_setup.AUseCustomTricks
import com.colderonetrains.battlesskates.game.actors.button.AButton
import com.colderonetrains.battlesskates.game.screens.BattleSetupScreen
import com.colderonetrains.battlesskates.game.screens.GameScreen
import com.colderonetrains.battlesskates.game.utils.*
import com.colderonetrains.battlesskates.game.utils.actor.*
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedMainGroup

class AMainBattleSetup(override val screen: BattleSetupScreen): AdvancedMainGroup() {

    private val aTopBack = ATopBack(screen, "S.K.A.T.E Battle")
    private val aMenu    = AMenu(screen, AMenu.CheckType.GAME)

    private val verticalGroup = AVerticalGroup(screen, 55f, paddingBottom = 300f, isWrap = true, alignmentHorizontal = AutoLayout.AlignmentHorizontal.CENTER)
    private val scroll        = AScrollPane(verticalGroup)

    private val aHowManySkaters    = AHowManySkaters(screen)
    private val aUseCustomTricks   = AUseCustomTricks(screen)
    private val btnStartButtle     = AButton(screen, AButton.Type.StartButtle)

    override fun addActorsOnGroup() {
        color.a = 0f

        addATopBack()
        addScroll()
        addAMenu()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addATopBack() {
        addActor(aTopBack)
        aTopBack.setBounds(20f, 1663f, 491f, 115f)
    }

    private fun addAMenu() {
        addActor(aMenu)
        aMenu.setBounds(50f, 45f, 980f, 180f)
    }

    private fun addScroll() {
        addActor(scroll)
        scroll.setBounds(156f, 175f, 768f, 1425f)

        verticalGroup.addActorsToVerticalGroup()
    }

    // Actors VerticalGroup------------------------------------------------------------------------

    private fun AVerticalGroup.addActorsToVerticalGroup() {
        setSize(768f, 1425f)

        aHowManySkaters.setSize(768f, 1142f)
        addActor(aHowManySkaters)

        addBoxUseCustomTricks()
        addBtnStartButtle()
    }

    private fun AVerticalGroup.addBoxUseCustomTricks() {
        aUseCustomTricks.setSize(768f, 84f)
        addActor(aUseCustomTricks)
        aUseCustomTricks.blockIsUseCustomTricks = { isUseCustomTrick -> BattleSetupScreen.GLOBAL_isUseCustomTrick = isUseCustomTrick }
    }

    private fun AVerticalGroup.addBtnStartButtle() {
        btnStartButtle.setSize(669f, 165f)
        addActor(btnStartButtle)

        btnStartButtle.setOnTouchListener(gdxGame.soundUtil) {
            btnStartButtle.disable()
            BattleSetupScreen.GLOBAL_listPlayerName.addAll(aHowManySkaters.getListPlayerName())
            BattleSetupScreen.GLOBAL_listCustomTrick.addAll(aUseCustomTricks.getListCustomTrick())

            screen.hideScreen {
                gdxGame.navigationManager.navigate(GameScreen::class.java.name, screen::class.java.name)
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

}