package com.farm.tilerotate.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.farm.tilerotate.game.actors.button.AButton
import com.farm.tilerotate.game.screens.MenuScreen
import com.farm.tilerotate.game.screens.GameScreen
import com.farm.tilerotate.game.screens.ResultFailScreen
import com.farm.tilerotate.game.screens.SettScreen
import com.farm.tilerotate.game.utils.Block
import com.farm.tilerotate.game.utils.TIME_ANIM_SCREEN
import com.farm.tilerotate.game.utils.actor.animDelay
import com.farm.tilerotate.game.utils.actor.animHide
import com.farm.tilerotate.game.utils.actor.animShow
import com.farm.tilerotate.game.utils.actor.setBounds
import com.farm.tilerotate.game.utils.actor.setOnClickListener
import com.farm.tilerotate.game.utils.advanced.AdvancedMainGroup
import com.farm.tilerotate.game.utils.gdxGame

class AMainResultFail(override val screen: ResultFailScreen): AdvancedMainGroup() {

    private val imgPanel = Image(gdxGame.assetsAll.RES)
    private val listBtn  = List(2) { Actor() }
    private val imgText  = Image(gdxGame.assetsAll.lose)

    override fun addActorsOnGroup() {
        color.a = 0f

        gdxGame.soundUtil.apply { play(lose) }

        addImgPanel()
        addBtn()

        addActor(imgText)
        imgText.setBounds(374f, 1198f, 333f, 141f)

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgPanel() {
        addActor(imgPanel)
        imgPanel.setBounds(140f, 495f, 801f, 930f)
    }

    private fun addBtn() {
        listBtn.forEachIndexed { index, image ->
            addActor(image)
            when(index) {
                0 -> {
                    image.setBounds(270f, 1000f, 540f, 181f)
                    image.setOnClickListener(gdxGame.soundUtil) {
                        screen.hideScreen {
                            //gdxGame.navigationManager.clearBackStack()
                            gdxGame.navigationManager.navigate(GameScreen::class.java.name)
                        }
                    }
                }
                1 -> {
                    image.setBounds(270f, 790f, 540f, 181f)
                    image.setOnClickListener(gdxGame.soundUtil) {
                        screen.hideScreen {
                            gdxGame.navigationManager.navigate(SettScreen::class.java.name)
                            //gdxGame.activity.webViewHelper.loadUrl(gdxGame.activity.getPrivacyURL(), false)
                        }
                    }
                }
                2 -> {
                   //image.setBounds(270f, 581f, 540f, 181f)
                   //image.setOnClickListener(gdxGame.soundUtil) {
                   //    gdxGame.activity.webViewHelper.loadUrl("file:///android_asset/privacy_policy.html", false)
                   //}
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