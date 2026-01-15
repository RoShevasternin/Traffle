package com.samartachokitse.endelgase.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.samartachokitse.endelgase.game.LibGDXGame
import com.samartachokitse.endelgase.game.actors.AButton
import com.samartachokitse.endelgase.game.actors.AProgress
import com.samartachokitse.endelgase.game.utils.*
import com.samartachokitse.endelgase.game.utils.actor.animHide
import com.samartachokitse.endelgase.game.utils.advanced.AdvancedScreen
import com.samartachokitse.endelgase.game.utils.advanced.AdvancedStage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class SettingScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressMusic = AProgress(this)
    private val progressSound = AProgress(this)

    private val btnMenu  = AButton(this, AButton.Static.Type.Menu)
    private val imgTitle = Image(game.all.settk)
    private val imgBotme = Image(game.all.mu_sd)

    override fun show() {
        setBackBackground(game.all.backgrounds.random().region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addMenu()
                addImgTitle()
                addProgress()
                addImgMuSd()
            }

            launch { btnMenu.animByNY(TIME_ANIM, 17f, 999f) }
            launch { imgTitle.animByNY(TIME_ANIM, 211f, 988f) }
            launch { progressMusic.animByNY(TIME_ANIM, 93f, 126f) }
            launch { progressSound.animByNY(TIME_ANIM, 355f, 126f) }
            launch { imgBotme.animByNY(TIME_ANIM, 92f, 17f) }
        }
    }

    private fun AdvancedStage.addMenu() {
        addActor(btnMenu)
        btnMenu.apply {
            setBounds(-100f, 999f, 86f, 86f)
            setOnClickListener {
                animHideScreen {
                    stageUI.root.animHide(TIME_ANIM) { game.navigationManager.back() }
                }
            }
        }
    }

    private fun AdvancedStage.addImgTitle() {
        addActor(imgTitle)
        imgTitle.setBounds(WIDTH_UI, 988f, 305f, 109f)
    }

    private fun AdvancedStage.addImgMuSd() {
        addActor(imgBotme)
        imgBotme.setBounds(WIDTH_UI, -100f, 453f, 109f)
    }

    private fun AdvancedStage.addProgress() {
        addActors(progressMusic, progressSound)
        progressMusic.apply {
            setBounds(-181f, 126f, 181f, 767f)

            progressPercentFlow.value = screen.game.musicUtil.volumeLevelFlow.value

            coroutine?.launch {
                progressPercentFlow.collect {
                    runGDX { screen.game.musicUtil.volumeLevelFlow.value = it }
                }
            }
        }
        progressSound.apply {
            setBounds(WIDTH_UI, 126f, 181f, 767f)

            progressPercentFlow.value = screen.game.soundUtil.volumeLevel

            coroutine?.launch {
                progressPercentFlow.collect {
                    runGDX { screen.game.soundUtil.volumeLevel = it }
                }
            }
        }
    }

    // Anim ------------------------------------------------------------------------

    private suspend fun Actor.animByNY(time: Float, nx: Float, ny: Float) = suspendCoroutine { continuation ->
        runGDX {
            clearActions()
            addAction(Actions.sequence(
                Actions.moveTo(nx, ny, time, Interpolation.smooth2),
                Actions.run { continuation.resume(Unit) }
            ))
        }
    }

    private fun animHideScreen(block: () -> Unit) {
        coroutine?.launch {
            launch { btnMenu.animByNY(TIME_ANIM, -100f, 999f) }
            launch { imgTitle.animByNY(TIME_ANIM, WIDTH_UI, 988f) }
            launch { progressMusic.animByNY(TIME_ANIM, -181f, 126f) }
            launch { progressSound.animByNY(TIME_ANIM, WIDTH_UI, 126f) }
            launch { imgBotme.animByNY(TIME_ANIM, WIDTH_UI, -100f) }

            delay(TIME_ANIM.toMS)
            runGDX { block() }
        }
    }

}