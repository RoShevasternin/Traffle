/*
 * Refactored Application Module
 * Build: 66723D01
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.actors.main

import com.moonarcade.starlabyrinth.game.actors.button.ClickableElement
import com.moonarcade.starlabyrinth.game.actors.button.GraphicButton
import com.moonarcade.starlabyrinth.game.actors.panel.APanelAvatar
import com.moonarcade.starlabyrinth.game.actors.panel.APanelMain
import com.moonarcade.starlabyrinth.game.actors.panel.APanelNickname
import com.moonarcade.starlabyrinth.game.actors.panel.APanelSelectAvatar
import com.moonarcade.starlabyrinth.game.screens.ProfileScreen
import com.moonarcade.starlabyrinth.game.utils.Block
import com.moonarcade.starlabyrinth.game.utils.TIME_ANIM_SCREEN
import com.moonarcade.starlabyrinth.game.utils.actor.animDelay
import com.moonarcade.starlabyrinth.game.utils.actor.animHide
import com.moonarcade.starlabyrinth.game.utils.actor.animShow
import com.moonarcade.starlabyrinth.game.utils.actor.disable
import com.moonarcade.starlabyrinth.game.utils.actor.enable
import com.moonarcade.starlabyrinth.game.utils.advanced.MainGroupContainer
import com.moonarcade.starlabyrinth.game.utils.gdxGame

class MainProfilePanel(
    override val screen: ProfileScreen,
): MainGroupContainer() {

    private val aPanelMain = APanelMain(screen)
    private val aPanelNickname = APanelNickname(screen)
    private val aPanelAvatar = APanelAvatar(screen)
    private val aPanelSelectAvatar = APanelSelectAvatar(screen)
    private val btnBack = ClickableElement(screen, ClickableElement.Type.Back)

    // Field
    private val collectionGroup = listOf(aPanelNickname, aPanelAvatar)

    override fun addActorsOnGroup() {
        color.a = 0f

        addAPanelMain()
        addAPanelNickname()
        addAPanelAvatar()
        addAPanelSelectAvatar()
        addBtnBack()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    // Primary method handler
    private fun addAPanelMain() {
        addActor(aPanelMain)
        aPanelMain.setBounds(3f, 1635f, 746f, 295f)
    }

    private fun addAPanelNickname() {
        addActor(aPanelNickname)
        aPanelNickname.setBounds(219f, 1383f, 643f, 237f)
    }

    private fun addAPanelAvatar() {
        addActor(aPanelAvatar)
        aPanelAvatar.setBounds(332f, 858f, 377f, 377f)

        aPanelAvatar.blockAvatar = { animShowPanelSelectAvatar() }
    }

    private fun addAPanelSelectAvatar() {
        addActor(aPanelSelectAvatar)
        aPanelSelectAvatar.setBounds(55f, -65f, 969f, 733f)

        val dsGems = gdxGame.ds_Gems

        aPanelSelectAvatar.apply {
            color.a = 0f
            disable()

            blockUse = { selectDataAvatar ->
                // Запуск WorkManager на роботу Gold per haur
                gdxGame.generateGoldPerHour(selectDataAvatar.goldPerHour)

                gdxGame.ds_User.update { dataUser ->
                    dataUser.presentAvatarIndex = selectDataAvatar.index
                    dataUser
                }
                aPanelAvatar.updateAvatar(selectDataAvatar)
                animHidePanelSelectAvatar()
            }
            blockBuy = { selectDataAvatar ->
                if (dsGems.flow.value >= selectDataAvatar.priceGems) {
                    dsGems.update { it - selectDataAvatar.priceGems }
                    gdxGame.ds_User.update { dataUser ->
                        dataUser.collectionBuyedAvatarIndex.add(selectDataAvatar.index)
                        dataUser
                    }
                } else {
                    // Sound Fail Buy
                }
            }
        }
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

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        animShow(TIME_ANIM_SCREEN) {
            screen.animShowPanelAchievement()
        }
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
        animHide(TIME_ANIM_SCREEN)
        screen.animHidePanelAchievement()

        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    // Primary method handler
    private fun animShowPanelSelectAvatar() {
        screen.animHidePanelAchievement()
        collectionGroup.onEach {
            it.clearActions()
            it.animHide(TIME_ANIM_SCREEN)
        }
        aPanelSelectAvatar.apply {
            clearActions()
            animShow(TIME_ANIM_SCREEN) { enable() }
        }
    }

    // System operation
    private fun animHidePanelSelectAvatar() {
        screen.animShowPanelAchievement()
        collectionGroup.onEach {
            it.clearActions()
            it.animShow(TIME_ANIM_SCREEN)
        }
        aPanelSelectAvatar.apply {
            clearActions()
            animHide(TIME_ANIM_SCREEN) { disable() }
        }
    }

}