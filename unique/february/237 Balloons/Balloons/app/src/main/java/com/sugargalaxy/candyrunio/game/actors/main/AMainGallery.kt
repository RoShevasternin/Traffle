package com.sugargalaxy.candyrunio.game.actors.main

import com.sugargalaxy.candyrunio.game.actors.button.AButton
import com.sugargalaxy.candyrunio.game.actors.button.AImageButton
import com.sugargalaxy.candyrunio.game.actors.panel.APanelGallery
import com.sugargalaxy.candyrunio.game.actors.panel.APanelMain
import com.sugargalaxy.candyrunio.game.screens.GalleryScreen
import com.sugargalaxy.candyrunio.game.utils.Block
import com.sugargalaxy.candyrunio.game.utils.TIME_ANIM_SCREEN
import com.sugargalaxy.candyrunio.game.utils.actor.animDelay
import com.sugargalaxy.candyrunio.game.utils.actor.animHide
import com.sugargalaxy.candyrunio.game.utils.actor.animShow
import com.sugargalaxy.candyrunio.game.utils.advanced.AdvancedMainGroup
import com.sugargalaxy.candyrunio.game.utils.gdxGame

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
        aPanelMain.setBounds(3f, 1641f, 698f, 279f)
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