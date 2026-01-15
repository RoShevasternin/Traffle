package com.cosmicbounce.galaxytic.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.cosmicbounce.galaxytic.game.LibGDXGame
import com.cosmicbounce.galaxytic.game.actors.AButton
import com.cosmicbounce.galaxytic.game.utils.HEIGHT_UI
import com.cosmicbounce.galaxytic.game.utils.TIME_ANIM
import com.cosmicbounce.galaxytic.game.utils.WIDTH_UI
import com.cosmicbounce.galaxytic.game.utils.actor.animHide
import com.cosmicbounce.galaxytic.game.utils.advanced.AdvancedScreen
import com.cosmicbounce.galaxytic.game.utils.advanced.AdvancedStage
import com.cosmicbounce.galaxytic.game.utils.region
import com.cosmicbounce.galaxytic.game.utils.runGDX
import com.cosmicbounce.galaxytic.game.utils.toMS
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MenuScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        private var isFirst = true
    }

    private val imgLogo = Image(game.all.logo)
    private val btnBegi = AButton(this, AButton.Static.Type.Begin)
    private val btnSett = AButton(this, AButton.Static.Type.Settongs)
    private val btnInfo = AButton(this, AButton.Static.Type.Info)
    private val btnExit = AButton(this, AButton.Static.Type.Exit)

    override fun show() {
        if (isFirst) {
            isFirst = false
            game.musicUtil.apply { music = audio.apply {
                isLooping = true
                volumeLevelFlow.value = 25f
            } }
        }

        setBackBackground(game.all.backgrounds.random().region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                goAsteroids()

                addImgLogo()
                addBtns()
            }
            launch { imgLogo.animByNY(TIME_ANIM, 190f, 821f) }
            launch { btnBegi.animByNY(TIME_ANIM, 117f, 581f) }
            delay((TIME_ANIM.toMS * 0.2f).toLong())
            launch { btnSett.animByNY(TIME_ANIM, 117f, 415f) }
            delay((TIME_ANIM.toMS * 0.2f).toLong())
            launch { btnInfo.animByNY(TIME_ANIM, 117f, 249f) }
            delay((TIME_ANIM.toMS * 0.2f).toLong())
            launch { btnExit.animByNY(TIME_ANIM, 117f, 83f) }

        }
    }

    private fun AdvancedStage.addImgLogo() {
        addActor(imgLogo)
        imgLogo.setBounds(190f, HEIGHT, 256f, 231f)
    }

    private fun AdvancedStage.addBtns() {
        addActors(btnBegi, btnSett, btnInfo, btnExit)
        btnBegi.apply {
            setBounds(117f, -200f, 402f, 138f)
            setOrigin(Align.center)
            setOnClickListener {
                animHideScreen {
                    game.navigationManager.navigate(BounceScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        btnSett.apply {
            setBounds(117f, -200f, 402f, 138f)
            setOrigin(Align.center)
            setOnClickListener {
                animHideScreen {
                    game.navigationManager.navigate(SettingScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        btnInfo.apply {
            setBounds(117f, -200f, 402f, 138f)
            setOrigin(Align.center)
            setOnClickListener {
                animHideScreen {
                    game.navigationManager.navigate(InfoScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        btnExit.apply {
            setBounds(117f, -200f, 402f, 138f)
            setOnClickListener {
                animHideScreen {
                    stageUI.root.animHide(TIME_ANIM) { game.navigationManager.exit() }
                }
            }
        }
    }

    // Anim ------------------------------------------------------------------------

    private fun animHideScreen(block: () -> Unit) {
        coroutine?.launch {

            launch { btnExit.animByNY_From(TIME_ANIM, 117f, -200f) }
            delay((TIME_ANIM.toMS * 0.2f).toLong())
            launch { btnInfo.animByNY_From(TIME_ANIM, 117f, -200f) }
            delay((TIME_ANIM.toMS * 0.2f).toLong())
            launch { btnSett.animByNY_From(TIME_ANIM, 117f, -200f) }
            delay((TIME_ANIM.toMS * 0.2f).toLong())

            launch { imgLogo.animByNY_From(TIME_ANIM, 190f, HEIGHT_UI) }
            launch { btnBegi.animByNY_From(TIME_ANIM, 117f, -200f) }

            delay(TIME_ANIM.toMS)
            runGDX { block() }
        }
    }

    // Asteroids ------------------------------------------------------------------------

    private fun goAsteroids() {
        val aLeft  = Image(game.all.left)
        val aRight = Image(game.all.right)
        stageUI.addActors(aLeft, aRight)
        aLeft.setBounds(300f, HEIGHT_UI -300, 287f, 283f)
        aRight.setBounds(WIDTH_UI, HEIGHT_UI, 287f, 283f)


        val startYInterval = (538..849)
        val endYInterval   = (0..487)
        val timeInterval   = (70..300)

        fun startLeft() {
            aLeft.setPosition(-300f, startYInterval.random().toFloat())

            aLeft.apply {
                clearActions()
                addAction(Actions.sequence(
                    Actions.moveTo(WIDTH_UI, endYInterval.random().toFloat(), timeInterval.random() / 100f),
                    Actions.run { startLeft() }
                ))
            }
        }

        fun startRight() {
            aRight.setPosition(WIDTH_UI, startYInterval.random().toFloat())

            aRight.apply {
                clearActions()
                addAction(Actions.sequence(
                    Actions.moveTo(-300f, endYInterval.random().toFloat(), timeInterval.random() / 100f),
                    Actions.run { startRight() }
                ))
            }
        }

        startLeft()
        startRight()

    }


}

suspend fun Actor.animByNY(time: Float, nx: Float, ny: Float) = suspendCoroutine { continuation ->
    runGDX {
        clearActions()
        addAction(
            Actions.sequence(
            Actions.moveTo(nx, ny, time, Interpolation.circleOut),
            Actions.run { continuation.resume(Unit) }
        ))
    }
}

suspend fun Actor.animByNY_From(time: Float, nx: Float, ny: Float) = suspendCoroutine { continuation ->
    runGDX {
        clearActions()
        addAction(
            Actions.sequence(
            Actions.moveTo(nx, ny, time, Interpolation.circleIn),
            Actions.run { continuation.resume(Unit) }
        ))
    }
}