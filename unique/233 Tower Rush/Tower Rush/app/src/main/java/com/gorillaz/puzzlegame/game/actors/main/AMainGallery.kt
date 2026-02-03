package com.gorillaz.puzzlegame.game.actors.main

import com.gorillaz.puzzlegame.game.actors.button.AImageButton
import com.gorillaz.puzzlegame.game.actors.panel.APanelGallery
import com.gorillaz.puzzlegame.game.actors.panel.APanelMain
import com.gorillaz.puzzlegame.game.screens.GalleryScreen
import com.gorillaz.puzzlegame.game.utils.Block
import com.gorillaz.puzzlegame.game.utils.TIME_ANIM_SCREEN
import com.gorillaz.puzzlegame.game.utils.actor.animDelay
import com.gorillaz.puzzlegame.game.utils.actor.animHide
import com.gorillaz.puzzlegame.game.utils.actor.animShow
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedMainGroup
import com.gorillaz.puzzlegame.game.utils.gdxGame

class AMainGallery(
    override val screen: GalleryScreen,
): AdvancedMainGroup() {

    private val aPanelMain    = APanelMain(screen)
    private val btnBack       = AImageButton(screen, gdxGame.assetsAll.arrow)
    private val aPanelGallery = APanelGallery(screen)


    override fun addActorsOnGroup() {
        color.a = 0f

        addAPanelMain()
        addBtnBack()
        addAPanelGallery()

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

    private fun addAPanelGallery() {
        addActor(aPanelGallery)
        aPanelGallery.setBounds(0f, 0f, 1080f, 1677f)
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

}