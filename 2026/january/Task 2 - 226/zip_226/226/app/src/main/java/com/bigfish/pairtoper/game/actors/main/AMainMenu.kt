package com.bigfish.pairtoper.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.bigfish.pairtoper.game.screens.GameScreen
import com.bigfish.pairtoper.game.screens.MenuScreen
import com.bigfish.pairtoper.game.screens.SelecteScreen
import com.bigfish.pairtoper.game.screens.SettScreen
import com.bigfish.pairtoper.game.utils.Block
import com.bigfish.pairtoper.game.utils.TIME_ANIM_SCREEN
import com.bigfish.pairtoper.game.utils.actor.animDelay
import com.bigfish.pairtoper.game.utils.actor.animHide
import com.bigfish.pairtoper.game.utils.actor.animShow
import com.bigfish.pairtoper.game.utils.actor.setOnClickListener
import com.bigfish.pairtoper.game.utils.advanced.AdvancedMainGroup
import com.bigfish.pairtoper.game.utils.gdxGame

class AMainMenu(override val screen: MenuScreen): AdvancedMainGroup() {

    private val imgMenu    = Image(gdxGame.assetsAll.menu)
    private val listBtn    = List(2) { Actor() }

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgMenu()
        addBtn()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgMenu() {
        addActor(imgMenu)
        imgMenu.setBounds(302f, 616f, 504f, 833f)
    }

    private fun addBtn() {
        listBtn.forEachIndexed { index, image ->
            addActor(image)
            when(index) {
                0 -> {
                    image.setBounds(302f, 1070f, 504f, 192f)
                    image.setOnClickListener(gdxGame.soundUtil) {
                        screen.hideScreen {
                            gdxGame.navigationManager.navigate(SelecteScreen::class.java.name, screen::class.java.name)
                        }
                    }
                }
                1 -> {
                    image.setBounds(302f, 843f, 504f, 192f)
                    image.setOnClickListener(gdxGame.soundUtil) {
                        screen.hideScreen {
                            gdxGame.navigationManager.navigate(SettScreen::class.java.name, screen::class.java.name)
                        }
                    }
                }
            }
        }
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
//        screen.stageBack.root.animShow(TIME_ANIM_SCREEN)

        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
//        screen.stageBack.root.animHide(TIME_ANIM_SCREEN)

        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

}