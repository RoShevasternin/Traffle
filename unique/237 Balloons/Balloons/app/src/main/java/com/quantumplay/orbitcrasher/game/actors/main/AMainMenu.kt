package com.quantumplay.orbitcrasher.game.actors.main

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.quantumplay.orbitcrasher.game.actors.panel.APanelMain
import com.quantumplay.orbitcrasher.game.actors.panel.APanelMenu
import com.quantumplay.orbitcrasher.game.actors.panel.APanelRoulette
import com.quantumplay.orbitcrasher.game.actors.panel.APanelSevens
import com.quantumplay.orbitcrasher.game.screens.GalleryScreen
import com.quantumplay.orbitcrasher.game.screens.MenuScreen
import com.quantumplay.orbitcrasher.game.screens.PlayScreen
import com.quantumplay.orbitcrasher.game.screens.ProfileScreen
import com.quantumplay.orbitcrasher.game.screens.SettingsScreen
import com.quantumplay.orbitcrasher.game.screens.ShopScreen
import com.quantumplay.orbitcrasher.game.screens.Wheel_of_FortuneScreen
import com.quantumplay.orbitcrasher.game.utils.Block
import com.quantumplay.orbitcrasher.game.utils.TIME_ANIM_SCREEN
import com.quantumplay.orbitcrasher.game.utils.actor.animDelay
import com.quantumplay.orbitcrasher.game.utils.actor.animHide
import com.quantumplay.orbitcrasher.game.utils.actor.animShow
import com.quantumplay.orbitcrasher.game.utils.actor.disable
import com.quantumplay.orbitcrasher.game.utils.advanced.AdvancedMainGroup
import com.quantumplay.orbitcrasher.game.utils.gdxGame
import com.quantumplay.orbitcrasher.util.log

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
        imgGorilla.setBounds(-173f, -223f, 1349f, 1349f)
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
        aPanelRoulette.setBounds(37f, 868f, 487f, 758f)

        aPanelRoulette.blockSpin = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(Wheel_of_FortuneScreen::class.java.name, screen::class.java.name)
            }
        }
    }

    private fun addAPanelSevens() {
        addActor(aPanelSevens)
        aPanelSevens.setBounds(448f, 1090f, 616f, 713f)

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