package com.sugargalaxy.candyrunio.game.actors.main

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.sugargalaxy.candyrunio.game.actors.button.AButton
import com.sugargalaxy.candyrunio.game.actors.button.AImageButton
import com.sugargalaxy.candyrunio.game.actors.panel.APanelMain
import com.sugargalaxy.candyrunio.game.actors.panel.APanelSelectInapp
import com.sugargalaxy.candyrunio.game.screens.ShopScreen
import com.sugargalaxy.candyrunio.game.utils.Acts
import com.sugargalaxy.candyrunio.game.utils.Block
import com.sugargalaxy.candyrunio.game.utils.TIME_ANIM_SCREEN
import com.sugargalaxy.candyrunio.game.utils.actor.animDelay
import com.sugargalaxy.candyrunio.game.utils.actor.animHide
import com.sugargalaxy.candyrunio.game.utils.actor.animShow
import com.sugargalaxy.candyrunio.game.utils.advanced.AdvancedMainGroup
import com.sugargalaxy.candyrunio.game.utils.gdxGame

class AMainShop(
    override val screen: ShopScreen,
): AdvancedMainGroup() {

    private val aPanelMain        = APanelMain(screen)
    private val btnBack           = AButton(screen, AButton.Type.Back)
    private val imgStore          = Image(gdxGame.assetsAll.STORE)
    private val aPanelSelectInapp = APanelSelectInapp(screen)


    override fun addActorsOnGroup() {
        color.a = 0f

        addAPanelMain()
        addBtnBack()
        addImgStore()
        addAPanelSelectInapp()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addAPanelMain() {
        addActor(aPanelMain)
        aPanelMain.setBounds(3f, 1641f, 698f, 279f)
    }

    private fun addBtnBack() {
        addActor(btnBack)
        btnBack.setBounds(949f, 1787f, 108f, 109f)
        btnBack.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addImgStore() {
        addActor(imgStore)
        imgStore.setBounds(-90f, 1065f, 1151f, 489f)
        imgStore.setOrigin(Align.center)
        imgStore.addAction(Acts.forever(Acts.sequence(
            Acts.scaleBy(-0.015f, -0.015f, 0.85f, Interpolation.pow2),
            Acts.scaleTo(1f, 1f, 0.85f, Interpolation.pow2),
        )))
    }

    private fun addAPanelSelectInapp() {
        addActor(aPanelSelectInapp)
        aPanelSelectInapp.setBounds(108f, -65f, 911f, 1050f)
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