package com.crystalpath.mystmazer.game.actors.main

import com.crystalpath.mystmazer.game.actors.button.AButton
import com.crystalpath.mystmazer.game.actors.button.AImageButton
import com.crystalpath.mystmazer.game.actors.panel.APanelGallery
import com.crystalpath.mystmazer.game.actors.panel.APanelMain
import com.crystalpath.mystmazer.game.screens.GalleryScreen
import com.crystalpath.mystmazer.game.utils.Block
import com.crystalpath.mystmazer.game.utils.TIME_ANIM_SCREEN
import com.crystalpath.mystmazer.game.utils.actor.animDelay
import com.crystalpath.mystmazer.game.utils.actor.animHide
import com.crystalpath.mystmazer.game.utils.actor.animShow
import com.crystalpath.mystmazer.game.utils.advanced.AdvancedMainGroup
import com.crystalpath.mystmazer.game.utils.gdxGame

class AMainGallery(
    override val screen: GalleryScreen,
): AdvancedMainGroup() {

    private val aPanelMain    = APanelMain(screen)
    private val btnBack       = AButton(screen, AButton.Type.Back)
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
        aPanelMain.setBounds(3f, 1641f, 746f, 279f)
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

    private fun addAPanelGallery() {
        addActor(aPanelGallery)
        aPanelGallery.setBounds(-8f, -65f, 1088f, 1742f)
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