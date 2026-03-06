package com.circuser.pairante.game.screens

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.circuser.pairante.game.actors.AProgress
import com.circuser.pairante.game.actors.ATmpGroup
import com.circuser.pairante.game.actors.button.AButton
import com.circuser.pairante.game.utils.Block
import com.circuser.pairante.game.utils.TIME_ANIM_SCREEN
import com.circuser.pairante.game.utils.actor.addActorWithConstraints
import com.circuser.pairante.game.utils.actor.addActors
import com.circuser.pairante.game.utils.actor.addAndFillActor
import com.circuser.pairante.game.utils.actor.animDelay
import com.circuser.pairante.game.utils.actor.animHide
import com.circuser.pairante.game.utils.actor.animShow
import com.circuser.pairante.game.utils.actor.setOnClickListener
import com.circuser.pairante.game.utils.advanced.AdvancedScreen
import com.circuser.pairante.game.utils.gdxGame
import com.circuser.pairante.game.utils.runGDX
import kotlinx.coroutines.launch

class SettingsScreen: AdvancedScreen() {

    private val aMenuGroup = ATmpGroup(this)
    private val aBackBtn   = AButton(this, AButton.Type.BACK)

    override fun show() {
        setBackBackground(gdxGame.assetsAll.B_DEF)
        super.show()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        addMenuGroup()
        addBackBtn()

        animShowScreen()
    }

    override fun animHideScreen(blockEnd: Block) {
        stageUI.root.animHide(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    override fun animShowScreen(blockEnd: Block) {
        stageUI.root.animShow(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    // Actors ------------------------------------------------------------------------

    private fun Group.addMenuGroup() {
        aMenuGroup.setSize(673f, 901f)
        addActorWithConstraints(aMenuGroup) {
            startToStartOf   = this@addMenuGroup
            endToEndOf       = this@addMenuGroup
            topToTopOf       = this@addMenuGroup
            bottomToBottomOf = this@addMenuGroup
        }

        val aSettingsImg = Image(gdxGame.assetsAll.SETT)
        val progMusic    = AProgress(this@SettingsScreen)
        val progSound    = AProgress(this@SettingsScreen)

        aMenuGroup.apply {
            addAndFillActor(aSettingsImg)
            addActors(progMusic, progSound)
        }
        progMusic.setBounds(157f, 437f, 430f, 54f)
        progSound.setBounds(157f, 220f, 430f, 54f)

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

    private fun Group.addBackBtn() {
        aBackBtn.setSize(100f, 100f)
        addActorWithConstraints(aBackBtn) {
            startToStartOf   = this@addBackBtn
            topToTopOf       = this@addBackBtn

            marginStart = 96f
            marginTop   = 96f
        }

        aBackBtn.setOnClickListener { animHideScreen { gdxGame.navigationManager.back() } }
    }
}