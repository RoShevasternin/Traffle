package com.gorillaz.puzzlegame.game.actors.main

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.gorillaz.puzzlegame.game.actors.button.AImageButton
import com.gorillaz.puzzlegame.game.actors.panel.APanelMain
import com.gorillaz.puzzlegame.game.actors.panel.APanelSelectInapp
import com.gorillaz.puzzlegame.game.screens.ShopScreen
import com.gorillaz.puzzlegame.game.utils.Acts
import com.gorillaz.puzzlegame.game.utils.Block
import com.gorillaz.puzzlegame.game.utils.TIME_ANIM_SCREEN
import com.gorillaz.puzzlegame.game.utils.actor.animDelay
import com.gorillaz.puzzlegame.game.utils.actor.animHide
import com.gorillaz.puzzlegame.game.utils.actor.animShow
import com.gorillaz.puzzlegame.game.utils.actor.setOrigin
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedMainGroup
import com.gorillaz.puzzlegame.game.utils.gdxGame

class AMainShop(
    override val screen: ShopScreen,
): AdvancedMainGroup() {

    private val aPanelMain        = APanelMain(screen)
    private val btnBack           = AImageButton(screen, gdxGame.assetsAll.arrow)
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
        aPanelMain.setBounds(6f, 1632f, 659f, 286f)
    }

    private fun addBtnBack() {
        addActor(btnBack)
        btnBack.setBounds(932f, 1810f, 139f, 102f)
        btnBack.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addImgStore() {
        addActor(imgStore)
        imgStore.setBounds(18f, 1252f, 1043f, 364f)
        imgStore.setOrigin(Align.center)
        imgStore.addAction(Acts.forever(Acts.sequence(
            Acts.scaleBy(-0.035f, -0.035f, 0.85f, Interpolation.pow2),
            Acts.scaleTo(1f, 1f, 0.85f, Interpolation.pow2),
        )))
    }

    private fun addAPanelSelectInapp() {
        addActor(aPanelSelectInapp)
        aPanelSelectInapp.setBounds(71f, 0f, 967f, 1001f)
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