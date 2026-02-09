package com.sugargalaxy.candyrunio.game.actors.main

import com.sugargalaxy.candyrunio.game.actors.button.AButton
import com.sugargalaxy.candyrunio.game.actors.button.AImageButton
import com.sugargalaxy.candyrunio.game.actors.panel.APanelAvatar
import com.sugargalaxy.candyrunio.game.actors.panel.APanelMain
import com.sugargalaxy.candyrunio.game.actors.panel.APanelNickname
import com.sugargalaxy.candyrunio.game.actors.panel.APanelSelectAvatar
import com.sugargalaxy.candyrunio.game.screens.ProfileScreen
import com.sugargalaxy.candyrunio.game.utils.Block
import com.sugargalaxy.candyrunio.game.utils.TIME_ANIM_SCREEN
import com.sugargalaxy.candyrunio.game.utils.actor.animDelay
import com.sugargalaxy.candyrunio.game.utils.actor.animHide
import com.sugargalaxy.candyrunio.game.utils.actor.animShow
import com.sugargalaxy.candyrunio.game.utils.actor.disable
import com.sugargalaxy.candyrunio.game.utils.actor.enable
import com.sugargalaxy.candyrunio.game.utils.advanced.AdvancedMainGroup
import com.sugargalaxy.candyrunio.game.utils.gdxGame

class AMainProfile(
    override val screen: ProfileScreen,
): AdvancedMainGroup() {

    private val aPanelMain         = APanelMain(screen)
    private val aPanelNickname     = APanelNickname(screen)
    private val aPanelAvatar       = APanelAvatar(screen)
    private val aPanelSelectAvatar = APanelSelectAvatar(screen)
    private val btnBack            = AButton(screen, AButton.Type.Back)

    // Field
    private val listGroup = listOf(aPanelNickname, aPanelAvatar)

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

    private fun addAPanelMain() {
        addActor(aPanelMain)
        aPanelMain.setBounds(3f, 1641f, 698f, 279f)
    }

    private fun addAPanelNickname() {
        addActor(aPanelNickname)
        aPanelNickname.setBounds(218f, 1313f, 643f, 237f)
    }

    private fun addAPanelAvatar() {
        addActor(aPanelAvatar)
        aPanelAvatar.setBounds(218f, 747f, 643f, 622f)

        aPanelAvatar.blockAvatar = { animShowPanelSelectAvatar() }
    }

    private fun addAPanelSelectAvatar() {
        addActor(aPanelSelectAvatar)
        aPanelSelectAvatar.setBounds(218f, -65f, 643f, 1126f)

        val dsGems = gdxGame.ds_Gems

        aPanelSelectAvatar.apply {
            color.a = 0f
            disable()

            blockUse = { selectDataAvatar ->
                // Запуск WorkManager на роботу Gold per haur
                gdxGame.generateGoldPerHour(selectDataAvatar.goldPerHour)

                gdxGame.ds_User.update { dataUser ->
                    dataUser.currentAvatarIndex = selectDataAvatar.index
                    dataUser
                }
                aPanelAvatar.updateAvatar(selectDataAvatar)
                animHidePanelSelectAvatar()
            }
            blockBuy = { selectDataAvatar ->
                if (dsGems.flow.value >= selectDataAvatar.priceGems) {
                    dsGems.update { it - selectDataAvatar.priceGems }
                    gdxGame.ds_User.update { dataUser ->
                        dataUser.listBuyedAvatarIndex.add(selectDataAvatar.index)
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
        btnBack.setBounds(949f, 1787f, 108f, 109f)
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

    private fun animShowPanelSelectAvatar() {
        screen.animHidePanelAchievement()
        listGroup.onEach {
            it.clearActions()
            it.animHide(TIME_ANIM_SCREEN)
        }
        aPanelSelectAvatar.apply {
            clearActions()
            animShow(TIME_ANIM_SCREEN) { enable() }
        }
    }

    private fun animHidePanelSelectAvatar() {
        screen.animShowPanelAchievement()
        listGroup.onEach {
            it.clearActions()
            it.animShow(TIME_ANIM_SCREEN)
        }
        aPanelSelectAvatar.apply {
            clearActions()
            animHide(TIME_ANIM_SCREEN) { disable() }
        }
    }

}