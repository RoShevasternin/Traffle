/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.actors.main

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.novaburst.pixelrally.game.actors.panel.APanelMain
import com.novaburst.pixelrally.game.actors.panel.APanelMenu
import com.novaburst.pixelrally.game.actors.panel.APanelRoulette
import com.novaburst.pixelrally.game.actors.panel.APanelSevens
import com.novaburst.pixelrally.game.screens.GalleryScreen
import com.novaburst.pixelrally.game.screens.MainMenu
import com.novaburst.pixelrally.game.screens.PlayScreen
import com.novaburst.pixelrally.game.screens.ProfileScreen
import com.novaburst.pixelrally.game.screens.ConfigDisplay
import com.novaburst.pixelrally.game.screens.StoreDisplay
import com.novaburst.pixelrally.game.screens.Wheel_of_FortuneScreen
import com.novaburst.pixelrally.game.utils.Block
import com.novaburst.pixelrally.game.utils.TIME_ANIM_SCREEN
import com.novaburst.pixelrally.game.utils.actor.animDelay
import com.novaburst.pixelrally.game.utils.actor.animHide
import com.novaburst.pixelrally.game.utils.actor.animShow
import com.novaburst.pixelrally.game.utils.actor.disable
import com.novaburst.pixelrally.game.utils.advanced.PrimaryContainer
import com.novaburst.pixelrally.game.utils.gdxGame
import com.novaburst.pixelrally.util.log

class AMainMenu(
    override val screen: MainMenu,
): PrimaryContainer() {

    private val imgGorilla     = Image(gdxGame.assetsLoader.gorilla)
    private val aPanelMain = APanelMain(screen)
    private val aPanelRoulette = APanelRoulette(screen)
    private val aPanelSevens   = APanelSevens(screen)
    private val aPanelMenu     = APanelMenu(screen)

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

    private fun addImgGorilla() {
        addActor(imgGorilla)
        imgGorilla.setBounds(44f, 57f, 996f, 1327f)
        imgGorilla.disable()

        imgGorilla.addAction(Actions.forever(Actions.sequence(
            Actions.moveBy(0f, -7f, 0.45f, Interpolation.sineIn),
            Actions.moveBy(0f, 7f, 0.45f, Interpolation.sineOut),
        )))
    }

    private fun addAPanelMenu() {
        addActor(aPanelMenu)
        aPanelMenu.setBounds(-41f, -193f, 1161f, 494f)

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
                    gdxGame.navigationManager.navigate(StoreDisplay::class.java.name, screen::class.java.name)
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
                    gdxGame.navigationManager.navigate(ConfigDisplay::class.java.name, screen::class.java.name)
                }
            }
        }
    }

    private fun addAPanelRoulette() {
        addActor(aPanelRoulette)
        aPanelRoulette.setBounds(31f, 1041f, 491f, 585f)

        aPanelRoulette.blockSpin = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(Wheel_of_FortuneScreen::class.java.name, screen::class.java.name)
            }
        }
    }

    private fun addAPanelSevens() {
        addActor(aPanelSevens)
        aPanelSevens.setBounds(412f, 1090f, 689f, 702f)

        aPanelSevens.blockPlay = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(PlayScreen::class.java.name, screen::class.java.name)
            }
        }
    }

    private fun addAPanelMain() {
        addActor(aPanelMain)
        aPanelMain.setBounds(-45f, 1641f, 611f, 279f)
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