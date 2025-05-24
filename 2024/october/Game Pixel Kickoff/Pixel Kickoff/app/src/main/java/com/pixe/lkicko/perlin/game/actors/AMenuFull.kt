package com.pixe.lkicko.perlin.game.actors

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.pixe.lkicko.perlin.game.screens.*
import com.pixe.lkicko.perlin.game.utils.TIME_ANIM
import com.pixe.lkicko.perlin.game.utils.actor.animHide
import com.pixe.lkicko.perlin.game.utils.actor.setOnClickListener
import com.pixe.lkicko.perlin.game.utils.advanced.AdvancedGroup
import com.pixe.lkicko.perlin.game.utils.advanced.AdvancedScreen

class AMenuFull(
    override val screen: AdvancedScreen,
) : AdvancedGroup() {

    private val imgMenu = Image(screen.game.all.menu_full)

    private val aMenu       = Actor()
    private val aPlay       = Actor()
    private val aShop       = Actor()
    private val aDailyBonus = Actor()
    private val aAwards     = Actor()
    private val aSettings   = Actor()
    private val aGallery    = Actor()

    var blockMenu = {}

    override fun addActorsOnGroup() {
        addAndFillActor(imgMenu)
        addActors(aMenu,aPlay,aShop,aDailyBonus,aAwards,aSettings,aGallery,)
        aMenu.apply {
            setBounds(85f,1545f,421f,174f)
            setOnClickListener(screen.game.soundUtil) {
                blockMenu()
            }
        }
        aPlay.apply {
            setBounds(55f,1263f,596f,246f)
            setOnClickListener(screen.game.soundUtil) {
                screen.stageUI.root.animHide(TIME_ANIM) {
                    screen.game.navigationManager.navigate(PlayScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        aShop.apply {
            setBounds(199f,1054f,421f,174f)
            setOnClickListener(screen.game.soundUtil) {
                screen.stageUI.root.animHide(TIME_ANIM) {
                    screen.game.navigationManager.navigate(ShopScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        aDailyBonus.apply {
            setBounds(255f,815f,421f,174f)
            setOnClickListener(screen.game.soundUtil) {
                screen.stageUI.root.animHide(TIME_ANIM) {
                    screen.game.navigationManager.navigate(DailyBonusScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        aAwards.apply {
            setBounds(312f,576f,421f,174f)
            setOnClickListener(screen.game.soundUtil) {
                screen.stageUI.root.animHide(TIME_ANIM) {
                    screen.game.navigationManager.navigate(AwardsScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        aSettings.apply {
            setBounds(372f,337f,421f,174f)
            setOnClickListener(screen.game.soundUtil) {
                screen.stageUI.root.animHide(TIME_ANIM) {
                    screen.game.navigationManager.navigate(SettingsScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        aGallery.apply {
            setBounds(427f,98f,421f,174f)
            setOnClickListener(screen.game.soundUtil) {
                screen.stageUI.root.animHide(TIME_ANIM) {
                    screen.game.navigationManager.navigate(GalleryScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
    }

}