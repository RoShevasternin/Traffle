package com.fruithaven.juicydashx.game.actors.main

import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.fruithaven.juicydashx.game.actors.AGems
import com.fruithaven.juicydashx.game.actors.ARoulette
import com.fruithaven.juicydashx.game.actors.ASheen
import com.fruithaven.juicydashx.game.actors.button.AButton
import com.fruithaven.juicydashx.game.actors.button.AButtonSpin
import com.fruithaven.juicydashx.game.actors.panel.APanelMain
import com.fruithaven.juicydashx.game.screens.Wheel_of_FortuneScreen
import com.fruithaven.juicydashx.game.utils.Acts
import com.fruithaven.juicydashx.game.utils.Block
import com.fruithaven.juicydashx.game.utils.TIME_ANIM_SCREEN
import com.fruithaven.juicydashx.game.utils.actor.animDelay
import com.fruithaven.juicydashx.game.utils.actor.animHide
import com.fruithaven.juicydashx.game.utils.actor.animShow
import com.fruithaven.juicydashx.game.utils.actor.setOnClickListener
import com.fruithaven.juicydashx.game.utils.advanced.AdvancedMainGroup
import com.fruithaven.juicydashx.game.utils.gdxGame

class AMainWheel_of_Fortune(
    override val screen: Wheel_of_FortuneScreen,
): AdvancedMainGroup() {

    private val aPanelMain       = APanelMain(screen)
    private val btnBack          = AButton(screen, AButton.Type.Back)
    private val imgPanelPrice    = Image(gdxGame.assetsAll.PANEL_ROULETTE_SPIN_PRICE)
    private val btnSpin          = AButtonSpin(screen)
    private val imgPanelWatchAdd = Image(gdxGame.assetsAll.PANEL_ROULETTE_WATCH_ADD)
    private val aSheen           = ASheen(screen)
    private val aGems            = AGems(screen)
    private val aRoulette        = ARoulette(screen)
    private val imgCursor        = Image(gdxGame.assetsAll.ROULETTE_CURSOR)


    override fun addActorsOnGroup() {
        color.a = 0f
        screen.topStageBack.root.color.a = 0f

        addAPanelMain()
        addBtnBack()
        addImgPanelPrice()
        //addAndFillActors(aSheen, aGems)
        addARoulette()
        addImgCursor()
        //addImgPanelWatchAdd()
        addBtnSpin()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addAPanelMain() {
        addActor(aPanelMain)
        aPanelMain.setBounds(3f, 1641f, 746f, 279f)
    }

    private fun addBtnBack() {
        addActor(btnBack)
        btnBack.setBounds(936f, 1758f, 117f, 134f)
        btnBack.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addImgPanelPrice() {
        addActor(imgPanelPrice)
        imgPanelPrice.setBounds(246f, 711f, 785f, 319f)
    }

    private fun addBtnSpin() {
        addActor(btnSpin)
        btnSpin.setBounds(30f, 733f, 422f, 420f)

        btnSpin.setOnClickListener {
            //btnSpin.disable()
            spinRoulette()
        }
    }

    private fun addImgPanelWatchAdd() {
        //addActor(imgPanelWatchAdd)
        //imgPanelWatchAdd.setBounds(556f, 9f, 466f, 281f)

        val aBtn = Actor()
        addActor(aBtn)
        aBtn.setBounds(704f, 91f, 213f, 151f)
        aBtn.setOnClickListener(gdxGame.soundUtil) {

        }
    }

    private fun addImgCursor() {
        addActor(imgCursor)
        imgCursor.setBounds(593f, 1541f, 89f, 143f)

//        imgCursor.setOrigin(306f, 327f)
//        imgCursor.addAction(
//            Acts.forever(Acts.sequence(
//            Acts.scaleBy(-0.25f, -0.25f, 0.5f, Interpolation.sineOut),
//            Acts.scaleBy(0.25f, 0.25f, 0.5f, Interpolation.sine),
//        )))
    }

    private fun addARoulette() {
        val imgBack = Image(gdxGame.assetsAll.NEW_ROULETTE_BACK)
        addActor(imgBack)
        imgBack.setBounds(220f, 856f, 829f, 829f)

        addActor(aRoulette)
        aRoulette.setBounds(292f, 922f, 691f, 691f)
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        animShow(TIME_ANIM_SCREEN)
        screen.topStageBack.root.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
        animHide(TIME_ANIM_SCREEN)
        screen.topStageBack.root.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    // Logic -------------------------------------------------------

    private fun spinRoulette() {
        if (gdxGame.ds_Gems.flow.value >= 5) {
            gdxGame.ds_Gems.update { it - 5 }
            btnSpin.disable()

            aRoulette.spin { winItem ->
                when(winItem.type) {
                    ARoulette.WinType.GOLD -> gdxGame.ds_Gold.update { it + winItem.count }
                    ARoulette.WinType.GEMS -> gdxGame.ds_Gems.update { it + winItem.count }
                }
                btnSpin.enable()
                btnSpin.resetEffect()
            }
        }
    }

}