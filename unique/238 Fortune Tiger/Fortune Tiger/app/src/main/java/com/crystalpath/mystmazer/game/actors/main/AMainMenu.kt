package com.crystalpath.mystmazer.game.actors.main

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.crystalpath.mystmazer.game.actors.panel.APanelMain
import com.crystalpath.mystmazer.game.actors.panel.APanelMenu
import com.crystalpath.mystmazer.game.actors.panel.APanelRoulette
import com.crystalpath.mystmazer.game.actors.panel.APanelSevens
import com.crystalpath.mystmazer.game.screens.GalleryScreen
import com.crystalpath.mystmazer.game.screens.MenuScreen
import com.crystalpath.mystmazer.game.screens.PlayScreen
import com.crystalpath.mystmazer.game.screens.ProfileScreen
import com.crystalpath.mystmazer.game.screens.SettingsScreen
import com.crystalpath.mystmazer.game.screens.ShopScreen
import com.crystalpath.mystmazer.game.screens.Wheel_of_FortuneScreen
import com.crystalpath.mystmazer.game.utils.Block
import com.crystalpath.mystmazer.game.utils.TIME_ANIM_SCREEN
import com.crystalpath.mystmazer.game.utils.actor.animDelay
import com.crystalpath.mystmazer.game.utils.actor.animHide
import com.crystalpath.mystmazer.game.utils.actor.animShow
import com.crystalpath.mystmazer.game.utils.actor.disable
import com.crystalpath.mystmazer.game.utils.advanced.AdvancedMainGroup
import com.crystalpath.mystmazer.game.utils.gdxGame
import com.crystalpath.mystmazer.util.log

class AMainMenu(
    override val screen: MenuScreen,
): AdvancedMainGroup() {

    private val imgGorilla     = Image(gdxGame.assetsLoader.gorilla)
    private val aPanelMain     = APanelMain(screen)
    private val aPanelRoulette = APanelRoulette(screen)
    private val aPanelSevens   = APanelSevens(screen)
    private val aPanelMenu     = APanelMenu(screen)

    override fun addActorsOnGroup() {
        color.a = 0f

        addAPanelMain()
        addImgGorilla()
        addAPanelRoulette()
        addAPanelSevens()
        addAPanelMenu()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgGorilla() {
        addActor(imgGorilla)
        imgGorilla.setBounds(-34f, -157f, 1150f, 1546f)
        imgGorilla.disable()

        imgGorilla.addAction(Actions.forever(Actions.sequence(
            Actions.moveBy(0f, -7f, 0.45f, Interpolation.sineIn),
            Actions.moveBy(0f, 7f, 0.45f, Interpolation.sineOut),
        )))
    }

    private fun addAPanelMenu() {
        addActor(aPanelMenu)
        aPanelMenu.setBounds(-84f, 40f, 1249f, 307f)

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
                    gdxGame.navigationManager.navigate(ShopScreen::class.java.name, screen::class.java.name)
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
                    gdxGame.navigationManager.navigate(SettingsScreen::class.java.name, screen::class.java.name)
                }
            }
        }
    }

    private fun addAPanelRoulette() {
        addActor(aPanelRoulette)
        aPanelRoulette.setBounds(0f, 1121f, 288f, 551f)

        aPanelRoulette.blockSpin = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(Wheel_of_FortuneScreen::class.java.name, screen::class.java.name)
            }
        }
    }

    private fun addAPanelSevens() {
        addActor(aPanelSevens)
        aPanelSevens.setBounds(592f, 1190f, 533f, 574f)

        aPanelSevens.blockPlay = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(PlayScreen::class.java.name, screen::class.java.name)
            }
        }
    }

    private fun addAPanelMain() {
        addActor(aPanelMain)
        aPanelMain.setBounds(3f, 1641f, 746f, 279f)
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