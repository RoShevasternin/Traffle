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

class InfoScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS).setSize(33)
    private val font          = fontGenerator_Pridi.generateFont(fontParameter)

    private val imgA = Image(game.all.a)
    private val imgB = Image(game.all.b)
    private val imgC = Image(game.all.c)

    private val lblA = Label("${game.sharedPreferences.getInt("a", 15)}", Label.LabelStyle(font, GColor.text))
    private val lblB = Label("${game.sharedPreferences.getInt("b", 10)}", Label.LabelStyle(font, GColor.text))
    private val lblC = Label("${game.sharedPreferences.getInt("c", 5)}", Label.LabelStyle(font, GColor.text))

    private val btnMenu = AButton(this, AButton.Static.Type.Menu)

    private val imgMASTER = Image(game.all.MASTER)
    private val imgGIFT = Image(game.all.GIFT)
    private val imgKING = Image(game.all.KING)

    private val imgTitle = Image(game.all.infok)

    override fun show() {
        setBackBackground(game.all.backgrounds.random().region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addImgABC()
                addLblABC()
                addMenu()
                addImgMGK()
                addImgTitle()
            }
            launch {
                launch { imgA.animByNY(TIME_ANIM, 6f, 765f) }
                delay((TIME_ANIM.toMS * 0.1f).toLong())
                launch { imgB.animByNY(TIME_ANIM, 6f, 440f) }
                delay((TIME_ANIM.toMS * 0.1f).toLong())
                launch { imgC.animByNY(TIME_ANIM, 6f, 117f) }
            }
            launch {
                launch { imgMASTER.animByNY(TIME_ANIM, 170f, 660f) }
                delay((TIME_ANIM.toMS * 0.1f).toLong())
                launch { imgGIFT.animByNY(TIME_ANIM, 170f, 336f) }
                delay((TIME_ANIM.toMS * 0.1f).toLong())
                launch { imgKING.animByNY(TIME_ANIM, 170f, 12f) }
            }

            delay((TIME_ANIM.toMS * 0.8f).toLong())

            launch { lblA.animShowSuspend(TIME_ANIM) }
            launch { lblB.animShowSuspend(TIME_ANIM) }
            launch { lblC.animShowSuspend(TIME_ANIM) }

            launch { btnMenu.animByNY(TIME_ANIM, 17f, 999f) }
            launch { imgTitle.animByNY(TIME_ANIM, 138f, 988f) }
        }
    }

    private fun AdvancedStage.addImgABC() {
        addActors(imgA, imgB, imgC)
        imgA.setBounds(-165f, 0f, 164f, 158f)
        imgB.setBounds(-165f, 0f, 164f, 158f)
        imgC.setBounds(-165f, 0f, 164f, 158f)
    }

    private fun AdvancedStage.addLblABC() {
        addActors(lblA, lblB, lblC)
        lblA.setBounds(72f, 765f, 31f, 51f)
        lblB.setBounds(72f, 441f, 31f, 51f)
        lblC.setBounds(72f, 117f, 31f, 51f)
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

    private fun AdvancedStage.addImgMGK() {
        addActors(imgMASTER, imgGIFT, imgKING)
        imgMASTER.setBounds(WIDTH_UI, 660f, 460f, 313f)
        imgGIFT.setBounds(WIDTH_UI, 336f, 460f, 313f)
        imgKING.setBounds(WIDTH_UI, 12f, 460f, 313f)
    }

    private fun AdvancedStage.addImgTitle() {
        addActor(imgTitle)
        imgTitle.setBounds(WIDTH_UI, 988f, 441f, 109f)
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

            launch { lblA.animHideSuspend(TIME_ANIM) }
            launch { lblB.animHideSuspend(TIME_ANIM) }
            launch { lblC.animHideSuspend(TIME_ANIM) }

            delay((TIME_ANIM.toMS * 0.8f).toLong())

            launch {
                launch { imgC.animByNY(TIME_ANIM, -165f, 0f) }
                delay((TIME_ANIM.toMS * 0.1f).toLong())
                launch { imgB.animByNY(TIME_ANIM, -165f, 0f) }
                delay((TIME_ANIM.toMS * 0.1f).toLong())
                launch { imgA.animByNY(TIME_ANIM, -165f, 0f) }
            }
            launch {
                launch { imgMASTER.animByNY(TIME_ANIM, WIDTH_UI, 660f) }
                delay((TIME_ANIM.toMS * 0.1f).toLong())
                launch { imgGIFT.animByNY(TIME_ANIM, WIDTH_UI, 336f) }
                delay((TIME_ANIM.toMS * 0.1f).toLong())
                launch { imgKING.animByNY(TIME_ANIM, WIDTH_UI, 12f) }
            }

            delay(TIME_ANIM.toMS)
            runGDX { block() }
        }
    }

}