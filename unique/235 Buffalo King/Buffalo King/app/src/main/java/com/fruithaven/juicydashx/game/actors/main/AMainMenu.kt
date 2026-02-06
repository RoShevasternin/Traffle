package com.fruithaven.juicydashx.game.actors.main

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.fruithaven.juicydashx.game.actors.panel.APanelMain
import com.fruithaven.juicydashx.game.actors.panel.APanelMenu
import com.fruithaven.juicydashx.game.actors.panel.APanelRoulette
import com.fruithaven.juicydashx.game.actors.panel.APanelSevens
import com.fruithaven.juicydashx.game.screens.GalleryScreen
import com.fruithaven.juicydashx.game.screens.MenuScreen
import com.fruithaven.juicydashx.game.screens.PlayScreen
import com.fruithaven.juicydashx.game.screens.ProfileScreen
import com.fruithaven.juicydashx.game.screens.SettingsScreen
import com.fruithaven.juicydashx.game.screens.ShopScreen
import com.fruithaven.juicydashx.game.screens.Wheel_of_FortuneScreen
import com.fruithaven.juicydashx.game.utils.Block
import com.fruithaven.juicydashx.game.utils.TIME_ANIM_SCREEN
import com.fruithaven.juicydashx.game.utils.actor.animDelay
import com.fruithaven.juicydashx.game.utils.actor.animHide
import com.fruithaven.juicydashx.game.utils.actor.animShow
import com.fruithaven.juicydashx.game.utils.actor.disable
import com.fruithaven.juicydashx.game.utils.advanced.AdvancedMainGroup
import com.fruithaven.juicydashx.game.utils.gdxGame
import com.fruithaven.juicydashx.util.log

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
        addAPanelRoulette()
        addAPanelSevens()
        addImgGorilla()
        addAPanelMenu()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgGorilla() {
        addActor(imgGorilla)
        imgGorilla.setBounds(-56f, -108f, 1280f, 1280f)
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
        aPanelRoulette.setBounds(-51f, 751f, 699f, 875f)

        aPanelRoulette.blockSpin = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(Wheel_of_FortuneScreen::class.java.name, screen::class.java.name)
            }
        }
    }

    private fun addAPanelSevens() {
        addActor(aPanelSevens)
        aPanelSevens.setBounds(432f, 1090f, 687f, 649f)

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