/*
 * Refactored Application Module
 * Build: 0983E2E6
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.actors.main

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.moonarcade.starlabyrinth.game.actors.button.ClickableElement
import com.moonarcade.starlabyrinth.game.actors.button.GraphicButton
import com.moonarcade.starlabyrinth.game.actors.panel.APanelMain
import com.moonarcade.starlabyrinth.game.actors.panel.APanelSelectInapp
import com.moonarcade.starlabyrinth.game.screens.MarketplaceScreen
import com.moonarcade.starlabyrinth.game.utils.Acts
import com.moonarcade.starlabyrinth.game.utils.Block
import com.moonarcade.starlabyrinth.game.utils.TIME_ANIM_SCREEN
import com.moonarcade.starlabyrinth.game.utils.actor.animDelay
import com.moonarcade.starlabyrinth.game.utils.actor.animHide
import com.moonarcade.starlabyrinth.game.utils.actor.animShow
import com.moonarcade.starlabyrinth.game.utils.advanced.MainGroupContainer
import com.moonarcade.starlabyrinth.game.utils.gdxGame

class MainShopPanel(
    override val screen: MarketplaceScreen,
): MainGroupContainer() {

    private val aPanelMain = APanelMain(screen)
    private val btnBack = ClickableElement(screen, ClickableElement.Type.Back)
    private val imgStore = Image(gdxGame.assetsAll.STORE)
    private val aPanelSelectInapp = APanelSelectInapp(screen)


    override fun addActorsOnGroup() {
        color.a = 0f

        addAPanelMain()
        addBtnBack()
        addImgStore()
        addAPanelSelectInapp()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    // Internal processing
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

    private fun addImgStore() {
        addActor(imgStore)
        imgStore.setBounds(18f, 1183f, 1044f, 364f)
        imgStore.setOrigin(Align.center)
        imgStore.addAction(Acts.forever(Acts.sequence(
            Acts.scaleBy(-0.015f, -0.015f, 0.85f, Interpolation.pow2),
            Acts.scaleTo(1f, 1f, 0.85f, Interpolation.pow2),
        )))
    }

    // System operation
    private fun addAPanelSelectInapp() {
        addActor(aPanelSelectInapp)
        aPanelSelectInapp.setBounds(108f, -65f, 911f, 1050f)
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