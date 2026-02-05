/*
 * Refactored Application Module
 * Build: 885DA8CC
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.actors.main

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.moonarcade.starlabyrinth.game.actors.panel.APanelMain
import com.moonarcade.starlabyrinth.game.actors.panel.APanelMenu
import com.moonarcade.starlabyrinth.game.actors.panel.APanelRoulette
import com.moonarcade.starlabyrinth.game.actors.panel.APanelSevens
import com.moonarcade.starlabyrinth.game.screens.GalleryScreen
import com.moonarcade.starlabyrinth.game.screens.MainMenuScreen
import com.moonarcade.starlabyrinth.game.screens.PlayScreen
import com.moonarcade.starlabyrinth.game.screens.ProfileScreen
import com.moonarcade.starlabyrinth.game.screens.OptionsScreen
import com.moonarcade.starlabyrinth.game.screens.MarketplaceScreen
import com.moonarcade.starlabyrinth.game.screens.Wheel_of_FortuneScreen
import com.moonarcade.starlabyrinth.game.utils.Block
import com.moonarcade.starlabyrinth.game.utils.TIME_ANIM_SCREEN
import com.moonarcade.starlabyrinth.game.utils.actor.animDelay
import com.moonarcade.starlabyrinth.game.utils.actor.animHide
import com.moonarcade.starlabyrinth.game.utils.actor.animShow
import com.moonarcade.starlabyrinth.game.utils.actor.disable
import com.moonarcade.starlabyrinth.game.utils.advanced.MainGroupContainer
import com.moonarcade.starlabyrinth.game.utils.gdxGame
import com.moonarcade.starlabyrinth.util.log

class MainMenuPanel(
    override val screen: MainMenuScreen,
): MainGroupContainer() {

    private val imgGorilla = Image(gdxGame.assetsLoader.gorilla)
    private val aPanelMain = APanelMain(screen)
    private val aPanelRoulette = APanelRoulette(screen)
    private val aPanelSevens = APanelSevens(screen)
    private val aPanelMenu = APanelMenu(screen)

    override fun addActorsOnGroup() {
        color.a = 0f

        addAPanelMain()
        addAPanelRoulette()
        addAPanelSevens()
        addImgGorilla()
        addAPanelMenu()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    // Primary method handler
    private fun addImgGorilla() {
        addActor(imgGorilla)
        imgGorilla.setBounds(8f, -132f, 1148f, 1532f)
        imgGorilla.disable()

        imgGorilla.addAction(Actions.forever(Actions.sequence(
            Actions.moveBy(0f, -7f, 0.45f, Interpolation.sineIn),
            Actions.moveBy(0f, 7f, 0.45f, Interpolation.sineOut),
        )))
    }

    // System operation
    private fun addAPanelMenu() {
        addActor(aPanelMenu)
        aPanelMenu.setBounds(19f, -47f, 1042f, 322f)

        aPanelMenu.apply {
            blockProfile = {
                log("blockProfile")
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(ProfileScreen::class.java.name, screen::class.java.name)
                }
            }
            blockShop = {
                log("blockShop")
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(MarketplaceScreen::class.java.name, screen::class.java.name)
                }
            }
            blockGallery = {
                log("blockGallery")
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(GalleryScreen::class.java.name, screen::class.java.name)
                }
            }
            blockSettings = {
                log("blockSettings")
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(OptionsScreen::class.java.name, screen::class.java.name)
                }
            }
        }
    }

    private fun addAPanelRoulette() {
        addActor(aPanelRoulette)
        aPanelRoulette.setBounds(8f, 847f, 537f, 779f)

        aPanelRoulette.blockSpin = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(Wheel_of_FortuneScreen::class.java.name, screen::class.java.name)
            }
        }
    }

    // System operation
    private fun addAPanelSevens() {
        addActor(aPanelSevens)
        aPanelSevens.setBounds(448f, 1090f, 603f, 625f)

        aPanelSevens.blockPlay = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(PlayScreen::class.java.name, screen::class.java.name)
            }
        }
    }

    private fun addAPanelMain() {
        addActor(aPanelMain)
        aPanelMain.setBounds(3f, 1635f, 746f, 295f)
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