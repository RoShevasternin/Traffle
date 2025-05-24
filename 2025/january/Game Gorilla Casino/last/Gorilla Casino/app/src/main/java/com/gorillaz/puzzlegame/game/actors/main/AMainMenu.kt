package com.gorillaz.puzzlegame.game.actors.main

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.gorillaz.puzzlegame.game.actors.button.AButton
import com.gorillaz.puzzlegame.game.actors.panel.APanelMain
import com.gorillaz.puzzlegame.game.actors.panel.APanelMenu
import com.gorillaz.puzzlegame.game.actors.panel.APanelRoulette
import com.gorillaz.puzzlegame.game.actors.panel.APanelSevens
import com.gorillaz.puzzlegame.game.screens.GalleryScreen
import com.gorillaz.puzzlegame.game.screens.MenuScreen
import com.gorillaz.puzzlegame.game.screens.PlayScreen
import com.gorillaz.puzzlegame.game.screens.ProfileScreen
import com.gorillaz.puzzlegame.game.screens.SettingsScreen
import com.gorillaz.puzzlegame.game.screens.ShopScreen
import com.gorillaz.puzzlegame.game.screens.Wheel_of_FortuneScreen
import com.gorillaz.puzzlegame.game.utils.Block
import com.gorillaz.puzzlegame.game.utils.TIME_ANIM_SCREEN
import com.gorillaz.puzzlegame.game.utils.actor.animDelay
import com.gorillaz.puzzlegame.game.utils.actor.animHide
import com.gorillaz.puzzlegame.game.utils.actor.animMoveTo
import com.gorillaz.puzzlegame.game.utils.actor.animShow
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedMainGroup
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedScreen
import com.gorillaz.puzzlegame.game.utils.gdxGame
import com.gorillaz.puzzlegame.game.utils.runGDX
import com.gorillaz.puzzlegame.util.log
import kotlinx.coroutines.launch

class AMainMenu(
    override val screen: MenuScreen,
): AdvancedMainGroup() {

    private val imgGorilla     = Image(gdxGame.assetsLoader.gorilla)
    private val aPanelMenu     = APanelMenu(screen)
    private val aPanelRoulette = APanelRoulette(screen)
    private val aPanelSevens   = APanelSevens(screen)
    private val aPanelMain     = APanelMain(screen)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgGorilla()
        addAPanelMenu()
        addAPanelRoulette()
        addAPanelSevens()
        addAPanelMain()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgGorilla() {
        addActor(imgGorilla)
        imgGorilla.setBounds(42f, 190f, 994f, 1043f)

        imgGorilla.addAction(Actions.forever(Actions.sequence(
            Actions.moveBy(0f, -25f, 0.45f, Interpolation.sineIn),
            Actions.moveBy(0f, 25f, 0.45f, Interpolation.sineOut),
        )))
    }

    private fun addAPanelMenu() {
        addActor(aPanelMenu)
        aPanelMenu.setBounds(6f, 0f, 1068f, 219f)

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
        aPanelRoulette.setBounds(0f, 1121f, 379f, 554f)

        aPanelRoulette.blockSpin = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(Wheel_of_FortuneScreen::class.java.name, screen::class.java.name)
            }
        }
    }

    private fun addAPanelSevens() {
        addActor(aPanelSevens)
        aPanelSevens.setBounds(524f, 1190f, 556f, 518f)

        aPanelSevens.blockPlay = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(PlayScreen::class.java.name, screen::class.java.name)
            }
        }
    }

    private fun addAPanelMain() {
        addActor(aPanelMain)
        aPanelMain.setBounds(6f, 1632f, 659f, 286f)
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