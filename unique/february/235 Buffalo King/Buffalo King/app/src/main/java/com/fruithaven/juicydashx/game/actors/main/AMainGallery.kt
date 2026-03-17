package com.fruithaven.juicydashx.game.actors.main

import com.fruithaven.juicydashx.game.actors.button.AButton
import com.fruithaven.juicydashx.game.actors.button.AImageButton
import com.fruithaven.juicydashx.game.actors.panel.APanelGallery
import com.fruithaven.juicydashx.game.actors.panel.APanelMain
import com.fruithaven.juicydashx.game.screens.GalleryScreen
import com.fruithaven.juicydashx.game.utils.Block
import com.fruithaven.juicydashx.game.utils.TIME_ANIM_SCREEN
import com.fruithaven.juicydashx.game.utils.actor.animDelay
import com.fruithaven.juicydashx.game.utils.actor.animHide
import com.fruithaven.juicydashx.game.utils.actor.animShow
import com.fruithaven.juicydashx.game.utils.advanced.AdvancedMainGroup
import com.fruithaven.juicydashx.game.utils.gdxGame

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
        btnBack.setBounds(936f, 1758f, 117f, 134f)
        btnBack.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addAPanelGallery() {
        addActor(aPanelGallery)
        aPanelGallery.setBounds(0f, -65f, 1080f, 1659f)
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