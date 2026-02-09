package com.sugargalaxy.candyrunio.game.actors.main

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.sugargalaxy.candyrunio.game.actors.panel.APanelMain
import com.sugargalaxy.candyrunio.game.actors.panel.APanelMenu
import com.sugargalaxy.candyrunio.game.actors.panel.APanelRoulette
import com.sugargalaxy.candyrunio.game.actors.panel.APanelSevens
import com.sugargalaxy.candyrunio.game.screens.GalleryScreen
import com.sugargalaxy.candyrunio.game.screens.MenuScreen
import com.sugargalaxy.candyrunio.game.screens.PlayScreen
import com.sugargalaxy.candyrunio.game.screens.ProfileScreen
import com.sugargalaxy.candyrunio.game.screens.SettingsScreen
import com.sugargalaxy.candyrunio.game.screens.ShopScreen
import com.sugargalaxy.candyrunio.game.screens.Wheel_of_FortuneScreen
import com.sugargalaxy.candyrunio.game.utils.Block
import com.sugargalaxy.candyrunio.game.utils.TIME_ANIM_SCREEN
import com.sugargalaxy.candyrunio.game.utils.actor.animDelay
import com.sugargalaxy.candyrunio.game.utils.actor.animHide
import com.sugargalaxy.candyrunio.game.utils.actor.animShow
import com.sugargalaxy.candyrunio.game.utils.actor.disable
import com.sugargalaxy.candyrunio.game.utils.advanced.AdvancedMainGroup
import com.sugargalaxy.candyrunio.game.utils.gdxGame
import com.sugargalaxy.candyrunio.util.log

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
        imgGorilla.setBounds(0f, 166f, 833f, 981f)
        imgGorilla.disable()

        imgGorilla.addAction(Actions.forever(Actions.sequence(
            Actions.moveBy(0f, -7f, 0.45f, Interpolation.sineIn),
            Actions.moveBy(0f, 7f, 0.45f, Interpolation.sineOut),
        )))
    }

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
        aPanelRoulette.setBounds(-31f, 1121f, 356f, 539f)

        aPanelRoulette.blockSpin = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(Wheel_of_FortuneScreen::class.java.name, screen::class.java.name)
            }
        }
    }

    private fun addAPanelSevens() {
        addActor(aPanelSevens)
        aPanelSevens.setBounds(585f, 1090f, 516f, 621f)

        aPanelSevens.blockPlay = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(PlayScreen::class.java.name, screen::class.java.name)
            }
        }
    }

    private fun addAPanelMain() {
        addActor(aPanelMain)
        aPanelMain.setBounds(3f, 1641f, 698f, 279f)
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