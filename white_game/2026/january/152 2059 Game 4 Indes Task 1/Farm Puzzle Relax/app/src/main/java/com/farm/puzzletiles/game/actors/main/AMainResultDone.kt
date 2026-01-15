package com.farm.puzzletiles.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.farm.puzzletiles.game.actors.button.AButton
import com.farm.puzzletiles.game.screens.MenuScreen
import com.farm.puzzletiles.game.screens.PuzzleScreen
import com.farm.puzzletiles.game.screens.ResultDoneScreen
import com.farm.puzzletiles.game.utils.Block
import com.farm.puzzletiles.game.utils.TIME_ANIM_SCREEN
import com.farm.puzzletiles.game.utils.actor.animDelay
import com.farm.puzzletiles.game.utils.actor.animHide
import com.farm.puzzletiles.game.utils.actor.animShow
import com.farm.puzzletiles.game.utils.actor.setOnClickListener
import com.farm.puzzletiles.game.utils.advanced.AdvancedMainGroup
import com.farm.puzzletiles.game.utils.gdxGame

class AMainResultDone(override val screen: ResultDoneScreen): AdvancedMainGroup() {

    private val listBtnType = listOf(AButton.Type.Next, AButton.Type.Menu)

    private val imgPanel = Image(gdxGame.assetsAll.DONE)
    private val listBtn  = List(2) { AButton(screen, listBtnType[it]) }

    override fun addActorsOnGroup() {
        color.a = 0f

        gdxGame.soundUtil.apply { play(win) }

        addImgPanel()
        addBtn()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgPanel() {
        addActor(imgPanel)
        imgPanel.setBounds(110f, 359f, 860f, 1201f)
    }

    private fun addBtn() {
        listBtn.forEachIndexed { index, image ->
            addActor(image)
            when(index) {
                0 -> {
                    image.setBounds(288f, 727f, 513f, 196f)
                    image.setOnClickListener(gdxGame.soundUtil) {
                        screen.hideScreen {
                            //gdxGame.navigationManager.clearBackStack()
                            gdxGame.navigationManager.navigate(PuzzleScreen::class.java.name)
                        }
                    }
                }
                1 -> {
                    image.setBounds(288f, 499f, 513f, 196f)
                    image.setOnClickListener(gdxGame.soundUtil) {
                        screen.hideScreen {
                            gdxGame.navigationManager.clearBackStack()
                            gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
                            //gdxGame.activity.webViewHelper.loadUrl(gdxGame.activity.getPrivacyURL(), false)
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