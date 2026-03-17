package com.moonarcade.starlabyrinth.game.actors.main

import com.moonarcade.starlabyrinth.game.actors.button.AButton
import com.moonarcade.starlabyrinth.game.actors.button.AImageButton
import com.moonarcade.starlabyrinth.game.actors.panel.APanelGallery
import com.moonarcade.starlabyrinth.game.actors.panel.APanelMain
import com.moonarcade.starlabyrinth.game.screens.GalleryScreen
import com.moonarcade.starlabyrinth.game.utils.Block
import com.moonarcade.starlabyrinth.game.utils.TIME_ANIM_SCREEN
import com.moonarcade.starlabyrinth.game.utils.actor.animDelay
import com.moonarcade.starlabyrinth.game.utils.actor.animHide
import com.moonarcade.starlabyrinth.game.utils.actor.animShow
import com.moonarcade.starlabyrinth.game.utils.advanced.AdvancedMainGroup
import com.moonarcade.starlabyrinth.game.utils.gdxGame

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
        aPanelMain.setBounds(3f, 1635f, 746f, 295f)
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