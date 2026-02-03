package com.bigfish.pairtoper.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.bigfish.pairtoper.game.actors.AProgress
import com.bigfish.pairtoper.game.actors.button.AButton
import com.bigfish.pairtoper.game.screens.SettScreen
import com.bigfish.pairtoper.game.utils.Block
import com.bigfish.pairtoper.game.utils.TIME_ANIM_SCREEN
import com.bigfish.pairtoper.game.utils.actor.animDelay
import com.bigfish.pairtoper.game.utils.actor.animHide
import com.bigfish.pairtoper.game.utils.actor.animMoveTo
import com.bigfish.pairtoper.game.utils.actor.animShow
import com.bigfish.pairtoper.game.utils.actor.setOnClickListener
import com.bigfish.pairtoper.game.utils.advanced.AdvancedMainGroup
import com.bigfish.pairtoper.game.utils.gdxGame
import com.bigfish.pairtoper.game.utils.runGDX
import kotlinx.coroutines.launch

class AMainSett(override val screen: SettScreen): AdvancedMainGroup() {

    private val imgRules = Image(gdxGame.assetsAll.SETT)
    private val btnX     = AButton(screen, AButton.Type.X)
//    private val mus      = ACheckBox(screen, ACheckBox.Type.Mus)
//    private val snd      = ACheckBox(screen, ACheckBox.Type.Sod)

    private val progMusic = AProgress(screen)
    private val progSound = AProgress(screen)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgRules()
        addBtnX()
//        addMus()
//        addSod()
        addMusSou()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgRules() {
        addActor(imgRules)
        imgRules.setBounds(105f, 405f, 870f, 1049f)
    }

    private fun addBtnX() {
        addActor(btnX)
        btnX.setBounds(96f, 1724f, 100f, 100f)
        btnX.setOnClickListener(gdxGame.soundUtil) {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addMusSou() {
        addActor(progMusic)
        progMusic.setBounds(300f, 911f, 563f, 40f)
        addActor(progSound)
        progSound.setBounds(300f, 654f, 563f, 40f)

        progMusic.progressPercentFlow.value = gdxGame.musicUtil.volumeLevelFlow.value
        progSound.progressPercentFlow.value = gdxGame.soundUtil.volumeLevel

        coroutine?.launch {
            launch {
                progMusic.progressPercentFlow.collect {
                    runGDX {
                        gdxGame.musicUtil.volumeLevelFlow.value = it
                    }
                }
            }
            progSound.progressPercentFlow.collect {
                runGDX {
                    gdxGame.soundUtil.volumeLevel = it
                }
            }
        }
    }




//
//    private fun addMus() {
//        addActor(mus)
//        mus.setBounds(40f, 1761f, 100f, 100f)
//        if (gdxGame.musicUtil.currentMusic?.isPlaying == false) mus.check()
//        mus.setOnCheckListener {
//            if (it) gdxGame.musicUtil.currentMusic?.pause() else gdxGame.musicUtil.currentMusic?.play()
//        }
//    }
//
//    private fun addSod() {
//        addActor(snd)
//        snd.setBounds(925f, 1761f, 100f, 100f)
//        if (gdxGame.soundUtil.isPause) snd.check()
//        snd.setOnCheckListener {
//            gdxGame.soundUtil.isPause = it
//        }
//    }

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