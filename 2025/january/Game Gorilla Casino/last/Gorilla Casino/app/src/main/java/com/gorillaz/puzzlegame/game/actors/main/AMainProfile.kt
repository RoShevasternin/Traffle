package com.gorillaz.puzzlegame.game.actors.main

import com.gorillaz.puzzlegame.game.actors.button.AImageButton
import com.gorillaz.puzzlegame.game.actors.panel.APanelAvatar
import com.gorillaz.puzzlegame.game.actors.panel.APanelMain
import com.gorillaz.puzzlegame.game.actors.panel.APanelNickname
import com.gorillaz.puzzlegame.game.actors.panel.APanelSelectAvatar
import com.gorillaz.puzzlegame.game.screens.ProfileScreen
import com.gorillaz.puzzlegame.game.utils.Acts
import com.gorillaz.puzzlegame.game.utils.Block
import com.gorillaz.puzzlegame.game.utils.TIME_ANIM_SCREEN
import com.gorillaz.puzzlegame.game.utils.actor.animDelay
import com.gorillaz.puzzlegame.game.utils.actor.animHide
import com.gorillaz.puzzlegame.game.utils.actor.animShow
import com.gorillaz.puzzlegame.game.utils.actor.disable
import com.gorillaz.puzzlegame.game.utils.actor.enable
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedMainGroup
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedScreen
import com.gorillaz.puzzlegame.game.utils.font.FontParameter
import com.gorillaz.puzzlegame.game.utils.gdxGame
import kotlinx.coroutines.flow.update

class AMainProfile(
    override val screen: ProfileScreen,
): AdvancedMainGroup() {

    private val aPanelMain         = APanelMain(screen)
    private val aPanelNickname     = APanelNickname(screen)
    private val aPanelAvatar       = APanelAvatar(screen)
    private val aPanelSelectAvatar = APanelSelectAvatar(screen)
    private val btnBack            = AImageButton(screen, gdxGame.assetsAll.arrow)

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
        aPanelMain.setBounds(6f, 1632f, 659f, 286f)
    }

    private fun addAPanelNickname() {
        addActor(aPanelNickname)
        aPanelNickname.setBounds(368f, 1318f, 643f, 237f)
    }

    private fun addAPanelAvatar() {
        addActor(aPanelAvatar)
        aPanelAvatar.setBounds(368f, 752f, 643f, 622f)

        aPanelAvatar.blockAvatar = { animShowPanelSelectAvatar() }
    }

    private fun addAPanelSelectAvatar() {
        addActor(aPanelSelectAvatar)
        aPanelSelectAvatar.setBounds(218f, 0f, 643f, 1061f)

        val dsGems = gdxGame.ds_Gems

        aPanelSelectAvatar.apply {
            color.a = 0f
            disable()

            blockUse = { selectDataAvatar ->
                gdxGame.soundUtil.apply { play(upgrade) }

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
                    gdxGame.soundUtil.apply { play(coins) }
                    dsGems.update { it - selectDataAvatar.priceGems }
                    gdxGame.ds_User.update { dataUser ->
                        dataUser.listBuyedAvatarIndex.add(selectDataAvatar.index)
                        dataUser
                    }
                } else {
                    gdxGame.soundUtil.apply { play(fail) }
                }
            }
        }
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

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        animShow(TIME_ANIM_SCREEN)
        screen.animShowPanelAchievement()

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