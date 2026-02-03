package com.gorillaz.puzzlegame.game.actors.main

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.gorillaz.puzzlegame.game.actors.AGems
import com.gorillaz.puzzlegame.game.actors.ARoulette
import com.gorillaz.puzzlegame.game.actors.ASheen
import com.gorillaz.puzzlegame.game.actors.button.AButtonSpin
import com.gorillaz.puzzlegame.game.actors.button.AImageButton
import com.gorillaz.puzzlegame.game.actors.panel.APanelMain
import com.gorillaz.puzzlegame.game.screens.Wheel_of_FortuneScreen
import com.gorillaz.puzzlegame.game.utils.Acts
import com.gorillaz.puzzlegame.game.utils.Block
import com.gorillaz.puzzlegame.game.utils.TIME_ANIM_SCREEN
import com.gorillaz.puzzlegame.game.utils.actor.animDelay
import com.gorillaz.puzzlegame.game.utils.actor.animHide
import com.gorillaz.puzzlegame.game.utils.actor.animShow
import com.gorillaz.puzzlegame.game.utils.actor.disable
import com.gorillaz.puzzlegame.game.utils.actor.setOnClickListener
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedMainGroup
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedScreen
import com.gorillaz.puzzlegame.game.utils.gdxGame

class AMainWheel_of_Fortune(
    override val screen: Wheel_of_FortuneScreen,
): AdvancedMainGroup() {

    private val aPanelMain       = APanelMain(screen)
    private val btnBack          = AImageButton(screen, gdxGame.assetsAll.arrow)
    private val imgPanelPrice    = Image(gdxGame.assetsAll.PANEL_ROULETTE_SPIN_PRICE)
    private val btnSpin          = AButtonSpin(screen)
//    private val imgPanelWatchAdd = Image(gdxGame.assetsAll.PANEL_ROULETTE_WATCH_ADD)
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
        addBtnSpin()
//        addImgPanelWatchAdd()
        addAndFillActors(aSheen, aGems)
        addARoulette()
        addImgCursor()

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

    private fun addImgPanelPrice() {
        addActor(imgPanelPrice)
        imgPanelPrice.setBounds(453f, 579f, 578f, 451f)
    }

    private fun addBtnSpin() {
        addActor(btnSpin)
        btnSpin.setBounds(468f, 555f, 350f, 283f)

        btnSpin.setOnClickListener {
            //btnSpin.disable()
            spinRoulette()
        }
    }

//    private fun addImgPanelWatchAdd() {
//        addActor(imgPanelWatchAdd)
//        imgPanelWatchAdd.setBounds(566f, 230f, 466f, 281f)
//
//        val aBtn = Actor()
//        addActor(aBtn)
//        aBtn.setBounds(723f, 346f, 181f, 119f)
//        aBtn.setOnClickListener(gdxGame.soundUtil) {
//
//        }
//    }

    private fun addImgCursor() {
        addActor(imgCursor)
        imgCursor.setBounds(25f, 790f, 377f, 406f)

        imgCursor.setOrigin(306f, 327f)
        imgCursor.addAction(Acts.forever(Acts.sequence(
            Acts.scaleBy(-0.25f, -0.25f, 0.5f, Interpolation.sineOut),
            Acts.scaleBy(0.25f, 0.25f, 0.5f, Interpolation.sine),
        )))
    }

    private fun addARoulette() {
        addActor(aRoulette)
        aRoulette.setBounds(220f, 856f, 829f, 829f)
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