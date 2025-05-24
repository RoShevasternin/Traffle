package com.colderonetrains.battlesskates.game.actors.main

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.colderonetrains.battlesskates.game.actors.button.AButton
import com.colderonetrains.battlesskates.game.screens.HomeScreen
import com.colderonetrains.battlesskates.game.screens.WelcomeScreen
import com.colderonetrains.battlesskates.game.utils.*
import com.colderonetrains.battlesskates.game.utils.actor.animDelay
import com.colderonetrains.battlesskates.game.utils.actor.animHide
import com.colderonetrains.battlesskates.game.utils.actor.animShow
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedMainGroup

class AMainWelcome(override val screen: WelcomeScreen): AdvancedMainGroup() {

    private val imgWelcome = Image(gdxGame.assetsAll.WELCOME)
    private val imgSkate   = Image(gdxGame.assetsAll.ICO_SKATE)
    private val btnStarted = AButton(screen, AButton.Type.GET_STARTED)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgWelcome()
        addImgSkate()
        addBtnStarted()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgWelcome() {
        addActor(imgWelcome)
        imgWelcome.setBounds(63f, 838f, 954f, 625f)
    }

    private fun addImgSkate() {
        addActor(imgSkate)
        imgSkate.setBounds(227f, 51f, 853f, 593f)
        imgSkate.setOrigin(Align.bottomRight)

        imgSkate.addAction(Acts.forever(Acts.sequence(
            Acts.scaleTo(0.93f, 0.93f, 1f, Interpolation.sineIn),
            Acts.scaleTo(1f, 1f, 1f, Interpolation.sineOut),
        )))
    }

    private fun addBtnStarted() {
        addActor(btnStarted)
        btnStarted.setBounds(151f, 347f, 779f, 275f)

        btnStarted.setOnClickListener {
            gdxGame.ds_IsWelcome.update { false }

            screen.hideScreen {
                gdxGame.navigationManager.navigate(HomeScreen::class.java.name)
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