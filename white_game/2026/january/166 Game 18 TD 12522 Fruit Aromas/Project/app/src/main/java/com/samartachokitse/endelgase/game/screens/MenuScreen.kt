package com.samartachokitse.endelgase.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.samartachokitse.endelgase.game.LibGDXGame
import com.samartachokitse.endelgase.game.actors.AButton
import com.samartachokitse.endelgase.game.utils.*
import com.samartachokitse.endelgase.game.utils.actor.animHide
import com.samartachokitse.endelgase.game.utils.actor.animHideSuspend
import com.samartachokitse.endelgase.game.utils.actor.animShowSuspend
import com.samartachokitse.endelgase.game.utils.advanced.AdvancedScreen
import com.samartachokitse.endelgase.game.utils.advanced.AdvancedStage
import com.samartachokitse.endelgase.game.utils.font.FontParameter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MenuScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        private var isFirst = true

        var countA = 0
        var countB = 0
        var countC = 0
    }

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS).setSize(33)
    private val font          = fontGenerator_Pridi.generateFont(fontParameter)

    private val imgA = Image(game.all.a)
    private val imgB = Image(game.all.b)
    private val imgC = Image(game.all.c)

    private val lblA = Label("${game.sharedPreferences.getInt("a", 0)}", Label.LabelStyle(font, GColor.text))
    private val lblB = Label("${game.sharedPreferences.getInt("b", 0)}", Label.LabelStyle(font, GColor.text))
    private val lblC = Label("${game.sharedPreferences.getInt("c", 0)}", Label.LabelStyle(font, GColor.text))

    private val btnStart = AButton(this, AButton.Static.Type.Start)
    private val btnSett  = AButton(this, AButton.Static.Type.Sett)
    private val btnInfo  = AButton(this, AButton.Static.Type.Info)
    private val btnExit  = AButton(this, AButton.Static.Type.Exit)

    override fun show() {
        if (isFirst) {
            isFirst = false
            game.musicUtil.apply { music = FRUITS.apply {
                isLooping = true
                volumeLevelFlow.value = 25f
            } }
        }

        countA = game.sharedPreferences.getInt("a", 0)
        countB = game.sharedPreferences.getInt("b", 0)
        countC = game.sharedPreferences.getInt("c", 0)

        setBackBackground(game.all.backgrounds.random().region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addImgABC()
                addLblABC()
                addBtns()
            }
            launch { imgA.animByNY(TIME_ANIM, 48f, 891f) }
            delay((TIME_ANIM.toMS * 0.1f).toLong())
            launch { imgB.animByNY(TIME_ANIM, 231f, 891f) }
            delay((TIME_ANIM.toMS * 0.1f).toLong())
            launch { imgC.animByNY(TIME_ANIM, 415f, 891f) }
            delay((TIME_ANIM.toMS * 0.8f).toLong())

            launch { lblA.animShowSuspend(TIME_ANIM) }
            launch { lblB.animShowSuspend(TIME_ANIM) }
            launch { lblC.animShowSuspend(TIME_ANIM) }

            delay((TIME_ANIM.toMS * 0.8f).toLong())

            launch { btnStart.animByNY(TIME_ANIM, 145f, 624f) }
            delay((TIME_ANIM.toMS * 0.1f).toLong())
            launch { btnSett.animByNY(TIME_ANIM, 145f, 441f) }
            delay((TIME_ANIM.toMS * 0.1f).toLong())
            launch { btnInfo.animByNY(TIME_ANIM, 145f, 258f) }
            delay((TIME_ANIM.toMS * 0.1f).toLong())
            launch { btnExit.animByNY(TIME_ANIM, 252f, 61f) }
        }
    }

    private fun AdvancedStage.addImgABC() {
        addActors(imgA, imgB, imgC)
        imgA.setBounds(48f, HEIGHT, 164f, 158f)
        imgB.setBounds(231f, HEIGHT, 164f, 158f)
        imgC.setBounds(415f, HEIGHT, 164f, 158f)
    }

    private fun AdvancedStage.addLblABC() {
        addActors(lblA, lblB, lblC)
        lblA.setBounds(114f, 891f, 31f, 51f)
        lblB.setBounds(297f, 891f, 31f, 51f)
        lblC.setBounds(481f, 891f, 31f, 51f)
        lblA.apply {
            setAlignment(Align.center)
            color.a = 0f
        }
        lblB.apply {
            setAlignment(Align.center)
            color.a = 0f
        }
        lblC.apply {
            setAlignment(Align.center)
            color.a = 0f
        }
    }


    private fun AdvancedStage.addBtns() {
        addActors(btnStart, btnSett, btnInfo, btnExit)
        btnStart.apply {
            setBounds(WIDTH_UI, 0f, 338f, 124f)
            setOrigin(Align.center)
            setOnClickListener {
                animHideScreen {
                    game.navigationManager.navigate(GuessingScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        btnInfo.apply {
            setBounds(WIDTH_UI, 0f, 338f, 124f)
            setOrigin(Align.center)
            setOnClickListener {
                animHideScreen {
                    game.navigationManager.navigate(InfoScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        btnSett.apply {
            setBounds(WIDTH_UI, 0f, 338f, 124f)
            setOrigin(Align.center)
            setOnClickListener {
                animHideScreen {
                    game.navigationManager.navigate(SettingScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        btnExit.apply {
            setBounds(252f, -100f, 125f, 93f)
            setOnClickListener {
                animHideScreen {
                    stageUI.root.animHide(TIME_ANIM) { game.navigationManager.exit() }
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
            launch { btnExit.animByNY(TIME_ANIM, 252f, -100f) }
            delay((TIME_ANIM.toMS * 0.1f).toLong())
            launch { btnInfo.animByNY(TIME_ANIM, WIDTH_UI, 0f) }
            delay((TIME_ANIM.toMS * 0.1f).toLong())
            launch { btnSett.animByNY(TIME_ANIM, WIDTH_UI, 0f) }
            delay((TIME_ANIM.toMS * 0.1f).toLong())
            launch { btnStart.animByNY(TIME_ANIM, WIDTH_UI, 0f) }

            delay((TIME_ANIM.toMS * 0.8f).toLong())

            launch { lblA.animHideSuspend(TIME_ANIM) }
            launch { lblB.animHideSuspend(TIME_ANIM) }
            launch { lblC.animHideSuspend(TIME_ANIM) }

            delay((TIME_ANIM.toMS * 0.8f).toLong())

            launch { imgC.animByNY(TIME_ANIM, 415f, HEIGHT) }
            delay((TIME_ANIM.toMS * 0.1f).toLong())
            launch { imgB.animByNY(TIME_ANIM, 231f, HEIGHT) }
            delay((TIME_ANIM.toMS * 0.1f).toLong())
            launch { imgA.animByNY(TIME_ANIM, 48f, HEIGHT) }

            delay(TIME_ANIM.toMS)
            runGDX { block() }
        }
    }

}