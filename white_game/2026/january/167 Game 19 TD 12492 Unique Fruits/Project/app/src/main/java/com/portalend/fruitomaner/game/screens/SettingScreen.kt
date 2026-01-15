package com.portalend.fruitomaner.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.portalend.fruitomaner.game.LibGDXGame
import com.portalend.fruitomaner.game.actors.AButton
import com.portalend.fruitomaner.game.actors.AProgress
import com.portalend.fruitomaner.game.utils.*
import com.portalend.fruitomaner.game.utils.actor.animHideSuspend
import com.portalend.fruitomaner.game.utils.actor.animShowSuspend
import com.portalend.fruitomaner.game.utils.advanced.AdvancedScreen
import com.portalend.fruitomaner.game.utils.advanced.AdvancedStage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class SettingScreen(override val game: LibGDXGame) : AdvancedScreen() {

    // Actor
    private val btnMenu     = AButton(this, AButton.Static.Type.Back)
    private val imgCenter   = Image(game.all.proflert)
    private val imgMVS      = Image(game.all.msend)
    private val imgS      = Image(game.all.vine)
    private val imgM      = Image(game.all.klubnik)

    private val progressMusic = AProgress(this)
    private val progressSound = AProgress(this)

    override fun show() {
        setBackBackground(game.all.backgrounds.random().region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addCenter()
                addMenu()
                addProgress()
                addMVS()
                addMS()
            }
            launch { imgMVS.animShowSuspend(TIME_ANIM) }
            delay(TIME_ANIM.toMS / 2)
            launch { progressMusic.animShowSuspend(TIME_ANIM) }
            launch { progressSound.animShowSuspend(TIME_ANIM) }
            delay(TIME_ANIM.toMS / 2)
            launch { imgCenter.animShowSuspend(TIME_ANIM) }
            launch { imgCenter.animByNY(TIME_ANIM, 300f, 352f) }
            delay(TIME_ANIM.toMS / 2)
            launch { btnMenu.animShowSuspend(TIME_ANIM) }
            launch { btnMenu.animByNY(TIME_ANIM, 246f, 76f) }
            delay(TIME_ANIM.toMS / 2)
            launch { imgM.animShowSuspend(TIME_ANIM) }
            launch { imgM.animByNY(TIME_ANIM, 189f, 830f) }
            launch { imgS.animShowSuspend(TIME_ANIM) }
            launch { imgS.animByNY(TIME_ANIM, 557f, 830f) }

        }
    }

    private fun AdvancedStage.addMenu() {
        addActors(btnMenu)
        btnMenu.apply {
            color.a = 0f
            setBounds(246f, -170f, 376f, 169f)
            setOrigin(Align.center)
            setOnClickListener {
                animHideScreen { game.navigationManager.back() }
            }
        }
    }

    private fun AdvancedStage.addCenter() {
        addActors(imgCenter)
        imgCenter.apply {
            color.a = 0f
            setBounds(300f, -267f, 267f, 267f)
            setOrigin(Align.center)
        }
    }

    private fun AdvancedStage.addProgress() {
        addActors(progressMusic, progressSound)
        progressMusic.apply {
            color.a = 0f
            setBounds(182f, 485f, 136f, 810f)

            progressPercentFlow.value = screen.game.musicUtil.volumeLevelFlow.value

            coroutine?.launch {
                progressPercentFlow.collect {
                    runGDX { screen.game.musicUtil.volumeLevelFlow.value = it }
                }
            }
        }
        progressSound.apply {
            color.a = 0f
            setBounds(550f, 485f, 136f, 810f)

            progressPercentFlow.value = screen.game.soundUtil.volumeLevel

            coroutine?.launch {
                progressPercentFlow.collect {
                    runGDX { screen.game.soundUtil.volumeLevel = it }
                }
            }
        }
    }

    private fun AdvancedStage.addMVS() {
        addActors(imgMVS)
        imgMVS.apply {
            color.a   = 0f
            touchable = Touchable.disabled
            setBounds(143f, 1375f, 595f, 63f)
        }
    }

    private fun AdvancedStage.addMS() {
        addActors(imgM, imgS)
        imgM.apply {
            color.a   = 0f
            touchable = Touchable.disabled
            setBounds(-130f, 0f, 121f, 121f)
            setOrigin(Align.center)
        }
        imgS.apply {
            color.a   = 0f
            touchable = Touchable.disabled
            setBounds(870f, 0f, 121f, 121f)
            setOrigin(Align.center)
        }
    }


    // Anim ------------------------------------------------------------------------

    private suspend fun Actor.animByNY(time: Float, nx: Float, ny: Float) = suspendCoroutine { continuation ->
        runGDX {
            addAction(Actions.rotateBy(720f, time))
            addAction(Actions.sequence(
                Actions.moveTo(nx, ny, time, Interpolation.sineOut),
                Actions.run { continuation.resume(Unit) }
            ))
        }
    }

    private fun animHideScreen(block: () -> Unit) {
        coroutine?.launch {
            launch { imgS.animByNY(TIME_ANIM, 870f, 1500f) }
            launch { imgM.animByNY(TIME_ANIM, -121f, 1500f) }
            delay(TIME_ANIM.toMS / 2)
            launch { imgMVS.animHideSuspend(TIME_ANIM) }
            launch { progressMusic.animHideSuspend(TIME_ANIM) }
            launch { progressSound.animHideSuspend(TIME_ANIM) }
            delay(TIME_ANIM.toMS / 2)
            launch { btnMenu.animByNY(TIME_ANIM, 246f, -180f) }
            delay(TIME_ANIM.toMS / 2)
            launch { imgCenter.animByNY(TIME_ANIM, 300f, -300f) }

            delay((TIME_ANIM+0.25f).toMS)
            runGDX { block() }
        }
    }

}