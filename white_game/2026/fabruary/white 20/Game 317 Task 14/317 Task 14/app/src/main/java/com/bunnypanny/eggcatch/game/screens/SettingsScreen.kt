package com.bunnypanny.eggcatch.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.bunnypanny.eggcatch.game.GDXGame
import com.bunnypanny.eggcatch.game.actors.AProgress
import com.bunnypanny.eggcatch.game.actors.button.AButton
import com.bunnypanny.eggcatch.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.bunnypanny.eggcatch.game.utils.actor.animHide
import com.bunnypanny.eggcatch.game.utils.actor.animShow
import com.bunnypanny.eggcatch.game.utils.actor.setOnClickListener
import com.bunnypanny.eggcatch.game.utils.advanced.AdvancedScreen
import com.bunnypanny.eggcatch.game.utils.advanced.AdvancedStage
import com.bunnypanny.eggcatch.game.utils.gdxGame
import com.bunnypanny.eggcatch.game.utils.region
import com.bunnypanny.eggcatch.game.utils.runGDX
import kotlinx.coroutines.launch

class SettingsScreen(override val game: GDXGame) : AdvancedScreen() {

    private val assets = game.assetsAll

    private val aSettingsImg = Image(assets.SETTINGS_PAN)
    private val aBackBtn     = AButton(this, AButton.Static.Type.BACK)

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.assetsAll.BACKGROUND.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM_SCREEN_ALPHA)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addSettingsImg()
        addBackBtn()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addBackBtn() {
        addActor(aBackBtn)
        aBackBtn.setBounds(75f, 1680f, 220f, 220f)

        aBackBtn.setOnClickListener {
            stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) { game.navigationManager.back() }
        }
    }

    private fun AdvancedStage.addSettingsImg() {
        addActor(aSettingsImg)
        aSettingsImg.setBounds(107f, 437f, 867f, 962f)

        val progMusic = AProgress(this@SettingsScreen)
        val progSound = AProgress(this@SettingsScreen)

        addActors(progMusic, progSound)

        progMusic.setBounds(364f, 1000f, 430f, 54f)
        progSound.setBounds(364f, 769f, 430f, 54f)

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
            launch {
                progSound.progressPercentFlow.collect {
                    runGDX {
                        gdxGame.soundUtil.volumeLevel = it
                    }
                }
            }
        }
    }

}