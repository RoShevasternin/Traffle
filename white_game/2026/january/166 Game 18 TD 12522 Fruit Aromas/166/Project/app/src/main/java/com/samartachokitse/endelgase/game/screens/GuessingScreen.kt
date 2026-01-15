package com.samartachokitse.endelgase.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.samartachokitse.endelgase.R
import com.samartachokitse.endelgase.appContext
import com.samartachokitse.endelgase.game.LibGDXGame
import com.samartachokitse.endelgase.game.actors.AButton
import com.samartachokitse.endelgase.game.actors.AImageAns
import com.samartachokitse.endelgase.game.utils.*
import com.samartachokitse.endelgase.game.utils.actor.*
import com.samartachokitse.endelgase.game.utils.advanced.AdvancedScreen
import com.samartachokitse.endelgase.game.utils.advanced.AdvancedStage
import com.samartachokitse.endelgase.game.utils.font.FontParameter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class GuessingScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS)
    private val font18        = fontGenerator_Pridi.generateFont(fontParameter.setCharacters(FontParameter.CharType.ALL).setSize(18))
    private val font24        = fontGenerator_Pridi.generateFont(fontParameter.setSize(24))
    private val font50        = fontGenerator_Pridi.generateFont(fontParameter.setSize(50))

    private val imgA = Image(game.all.a)
    private val imgB = Image(game.all.b)
    private val imgC = Image(game.all.c)

    private val lblA     = Label("${MenuScreen.countA}", Label.LabelStyle(font24, GColor.text))
    private val lblB     = Label("${MenuScreen.countB}", Label.LabelStyle(font24, GColor.text))
    private val lblC     = Label("${MenuScreen.countC}", Label.LabelStyle(font24, GColor.text))
    private val lblTimer = Label("0", Label.LabelStyle(font50, Color.WHITE))

    private val btnMenu  = AButton(this, AButton.Static.Type.Menu)

    private val imgFrame = Image(game.all.FRAME)
    private val imgTimer = Image(game.all.timer)
    private val imgStar1 = Image(game.all.starka)
    private val imgStar2 = Image(game.all.starka)

    private val imgMisha1 = Image(game.all.LEFT)
    private val imgMisha2 = Image(game.all.RIGHT)
    private val trica     = Image(game.all.TRICA)

    // Field
    private var isStartTimer = false
    private val timerFlow    = MutableStateFlow(0)

    private val listQuessin = List(10) { Quessin(it, appContext.resources.getStringArray(R.array.texts)[it], game.all.items[it]) }

    private val lblQues = Label("", Label.LabelStyle(font18, Color.WHITE))
    private val answer1 = AImageAns(this)
    private val answer2 = AImageAns(this)
    private val answer3 = AImageAns(this)

    private val imgResult   = Image()

    data class Quessin(val id: Int, val text: String, val region: TextureRegion)

    override fun show() {
//        if (isFirst) {
//            isFirst = false
//            game.musicUtil.apply { music = whip_afro.apply {
//                isLooping = true
//                volumeLevelFlow.value = 23f
//            } }
//        }

        setBackBackground(game.all.backgrounds.random().region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addImgABC()
                addLblABC()
                addMenu()
                addFrame()
                addTimer()
                addMishoks()
                addTrica()
                addQessin()
                addImgResult()
            }

            launch { btnMenu.animByNY(TIME_ANIM, 45f, 982f) }

            launch { imgA.animByNY(TIME_ANIM, 186f, 963f) }
            delay((TIME_ANIM.toMS * 0.1f).toLong())
            launch { imgB.animByNY(TIME_ANIM, 335f, 963f) }
            delay((TIME_ANIM.toMS * 0.1f).toLong())
            launch { imgC.animByNY(TIME_ANIM, 485f, 963f) }

            delay((TIME_ANIM.toMS * 0.8f).toLong())

            launch { lblA.animShowSuspend(TIME_ANIM) }
            launch { lblB.animShowSuspend(TIME_ANIM) }
            launch { lblC.animShowSuspend(TIME_ANIM) }

            delay((TIME_ANIM.toMS * 0.25f).toLong())
            launch { imgFrame.animShowSuspend(TIME_ANIM) }
            delay((TIME_ANIM.toMS * 0.8f).toLong())

            launch { imgTimer.animShowSuspend(TIME_ANIM) }
            delay((TIME_ANIM.toMS * 0.7f).toLong())
            launch { imgStar1.animShowSuspend(TIME_ANIM) }
            delay((TIME_ANIM.toMS * 0.7f).toLong())
            launch { imgStar2.animShowSuspend(TIME_ANIM) }
            delay((TIME_ANIM.toMS * 0.7f).toLong())
            launch { lblTimer.animShowSuspend(TIME_ANIM) }

            launch { imgMisha1.animShowSuspend(TIME_ANIM) }
            launch { imgMisha2.animShowSuspend(TIME_ANIM) }
            delay((TIME_ANIM.toMS * 0.5f).toLong())
            launch { trica.animShowSuspend(TIME_ANIM) }

            runGDX { restart() }

        }
    }

    private fun AdvancedStage.addImgABC() {
        addActors(imgA, imgB, imgC)
        imgA.setBounds(WIDTH_UI, HEIGHT, 120f, 116f)
        imgB.setBounds(WIDTH_UI, HEIGHT, 120f, 116f)
        imgC.setBounds(WIDTH_UI, HEIGHT, 120f, 116f)
    }

    private fun AdvancedStage.addLblABC() {
        addActors(lblA, lblB, lblC)
        lblA.setBounds(235f, 963f, 23f, 38f)
        lblB.setBounds(384f, 963f, 23f, 38f)
        lblC.setBounds(534f, 963f, 23f, 38f)
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
            setBounds(-100f, 982f, 86f, 86f)
            setOnClickListener {
                animHideScreen {
                    stageUI.root.animHide(TIME_ANIM) { game.navigationManager.back() }
                }
            }
        }
    }

    private fun AdvancedStage.addFrame() {
        addActor(imgFrame)
        imgFrame.apply {
            color.a = 0f
            setBounds(50f, 394f, 529f, 329f)
        }
    }

    private fun AdvancedStage.addTimer() {
        addActor(imgTimer)
        imgTimer.apply {
            color.a = 0f
            setBounds(219f, 783f, 193f, 120f)
        }
        addActors(imgStar1, imgStar2)
        imgStar1.apply {
            color.a = 0f
            setBounds(201f, 873f, 52f, 48f)
            setOrigin(Align.center)
            addAction(Actions.forever(Actions.rotateBy(-360f, 3f)))
        }
        imgStar2.apply {
            color.a = 0f
            setBounds(376f, 775f, 52f, 48f)
            setOrigin(Align.center)
            addAction(Actions.forever(Actions.rotateBy(360f, 3f)))
        }
        addActor(lblTimer)
        lblTimer.apply {
            color.a = 0f
            setBounds(289f, 788f, 52f, 78f)
            setAlignment(Align.center)
        }

        coroutine?.launch {
            while (isActive) {
                delay(1000)

                if (isStartTimer) {
                    timerFlow.value++
                }
            }
        }
        coroutine?.launch {
            timerFlow.collect {
                runGDX { lblTimer.setText(it) }
            }
        }

    }

    private fun AdvancedStage.addMishoks() {
        addActors(imgMisha1, imgMisha2)
        imgMisha1.apply {
            color.a = 0f
            setBounds(-125f, -152f, 364f, 429f)
            setOrigin(Align.center)
            addAction(Actions.forever(Actions.sequence(
                Actions.scaleBy(-0.3f, -0.3f, TIME_ANIM+0.7f),
                Actions.scaleBy(0.3f, 0.3f, TIME_ANIM+0.7f),
            )))
        }
        imgMisha2.apply {
            color.a = 0f
            setBounds(396f, -152f, 364f, 429f)
            setOrigin(Align.center)
            addAction(Actions.sequence(
                Actions.delay(TIME_ANIM+0.7f),
                Actions.forever(Actions.sequence(
                Actions.scaleBy(-0.3f, -0.3f, TIME_ANIM+0.7f),
                Actions.scaleBy(0.3f, 0.3f, TIME_ANIM+0.7f),
            ))))
        }

    }

    private fun AdvancedStage.addTrica() {
        addActor(trica)
        trica.apply {
            color.a = 0f
            setBounds(63f, 144f, 503f, 202f)
        }
    }

    private fun AdvancedStage.addQessin() {
        addActors(lblQues, answer1, answer2, answer3)
        lblQues.apply {
            setBounds(100f, 447f, 428f, 224f)
            setAlignment(Align.center)
            wrap = true
        }
        answer1.apply {
            setBounds(63f, 264f, 223f, 81f)
            setOnClickListener {

                when (timerFlow.value) {
                    in 0..5 -> {
                        imgResult.drawable = TextureRegionDrawable(game.all.results[2])
                        imgResult.animShow(TIME_ANIM) {
                            imgResult.addAction(Actions.sequence(
                                Actions.moveTo(493f, 996f, TIME_ANIM+0.5f, Interpolation.sine),
                                Actions.run {
                                    imgResult.animHide(TIME_ANIM) {
                                        MenuScreen.countC += 1
                                        lblC.setText(MenuScreen.countC)
                                        game.sharedPreferences.edit().putInt("c", MenuScreen.countC).apply()
                                        imgResult.setPosition(261f, 16f)
                                    }
                                }
                            ))
                        }
                    }
                    in 6..10 -> {
                        imgResult.drawable = TextureRegionDrawable(game.all.results[1])
                        imgResult.animShow(TIME_ANIM) {
                            imgResult.addAction(Actions.sequence(
                                Actions.moveTo(343f, 996f, TIME_ANIM+0.5f, Interpolation.sine),
                                Actions.run {
                                    imgResult.animHide(TIME_ANIM) {
                                        MenuScreen.countB += 1
                                        lblB.setText(MenuScreen.countB)
                                        game.sharedPreferences.edit().putInt("b", MenuScreen.countB).apply()
                                        imgResult.setPosition(261f, 16f)
                                    }
                                }
                            ))
                        }
                    }
                    in 11..15 -> {
                        imgResult.drawable = TextureRegionDrawable(game.all.results[0])
                        imgResult.animShow(TIME_ANIM) {
                            imgResult.addAction(Actions.sequence(
                                Actions.moveTo(193f, 996f, TIME_ANIM+0.5f, Interpolation.sine),
                                Actions.run {
                                    imgResult.animHide(TIME_ANIM) {
                                        MenuScreen.countA += 1
                                        lblA.setText(MenuScreen.countA)
                                        game.sharedPreferences.edit().putInt("a", MenuScreen.countA).apply()
                                        imgResult.setPosition(261f, 16f)
                                    }
                                }
                            ))
                        }
                    }
                }

                restart()
                game.soundUtil.apply { play(win, 0.7f) }
            }
        }
        answer2.apply {
            setBounds(343f, 264f, 223f, 81f)
            setOnClickListener {
                restart()
                game.soundUtil.apply { play(fail, 0.15f) }
            }
        }
        answer3.apply {
            setBounds(203f, 144f, 223f, 81f)
            setOnClickListener {
                restart()
                game.soundUtil.apply { play(fail, 0.15f) }
            }
        }
    }

    private fun AdvancedStage.addImgResult() {
        addActor(imgResult)
        imgResult.color.a = 0f
        imgResult.setBounds(261f, 16f, 106f, 106f)
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

            launch { lblQues.animHideSuspend(TIME_ANIM) }
            launch { answer1.animHideSuspend(TIME_ANIM) }
            launch { answer2.animHideSuspend(TIME_ANIM) }
            launch { answer3.animHideSuspend(TIME_ANIM) }

            launch { trica.animHideSuspend(TIME_ANIM) }
            delay((TIME_ANIM.toMS * 0.5f).toLong())
            launch { imgMisha2.animHideSuspend(TIME_ANIM) }
            launch { imgMisha1.animHideSuspend(TIME_ANIM) }

            launch { lblTimer.animHideSuspend(TIME_ANIM) }
            delay((TIME_ANIM.toMS * 0.7f).toLong())
            launch { imgStar1.animHideSuspend(TIME_ANIM) }
            delay((TIME_ANIM.toMS * 0.7f).toLong())
            launch { imgStar2.animHideSuspend(TIME_ANIM) }
            delay((TIME_ANIM.toMS * 0.7f).toLong())
            launch { imgTimer.animHideSuspend(TIME_ANIM) }

            delay((TIME_ANIM.toMS * 0.5f).toLong())
            launch { imgFrame.animHideSuspend(TIME_ANIM) }

            delay((TIME_ANIM.toMS * 0.25f).toLong())

            launch { btnMenu.animByNY(TIME_ANIM, -100f, 982f) }

            launch { lblA.animHideSuspend(TIME_ANIM) }
            launch { lblB.animHideSuspend(TIME_ANIM) }
            launch { lblC.animHideSuspend(TIME_ANIM) }

            delay((TIME_ANIM.toMS * 0.8f).toLong())

            launch { imgC.animByNY(TIME_ANIM, WIDTH_UI, HEIGHT) }
            delay((TIME_ANIM.toMS * 0.1f).toLong())
            launch { imgB.animByNY(TIME_ANIM, WIDTH_UI, HEIGHT) }
            delay((TIME_ANIM.toMS * 0.1f).toLong())
            launch { imgA.animByNY(TIME_ANIM, WIDTH_UI, HEIGHT) }

            delay(TIME_ANIM.toMS)
            runGDX { block() }
        }
    }

    // Logic ------------------------------------------------------------------------


    private val ansPos = listOf(
        Vector2(63f, 264f),
        Vector2(343f, 264f),
        Vector2(203f, 144f),
    )

    private fun restart() {
        val quessin = listQuessin.random()
        val pos     = ansPos.shuffled()

        isStartTimer    = false
        timerFlow.value = 0

        val p1 = pos[0]
        val p2 = pos[1]
        val p3 = pos[2]

        lblQues.animHide(TIME_ANIM)
        answer1.animHide(TIME_ANIM)
        answer2.animHide(TIME_ANIM)
        answer3.animHide(TIME_ANIM) {
            lblQues.setText(quessin.text)
            answer1.drawable = TextureRegionDrawable(quessin.region)

            val result = game.all.items.filterIndexed { index, _ -> index != quessin.id }.shuffled()
            answer2.drawable = TextureRegionDrawable(result.first())
            answer3.drawable = TextureRegionDrawable(result.last())

            answer1.setPosition(p1.x, p1.y)
            answer2.setPosition(p2.x, p2.y)
            answer3.setPosition(p3.x, p3.y)

            lblQues.animShow(TIME_ANIM)
            answer1.animShow(TIME_ANIM)
            answer2.animShow(TIME_ANIM)
            answer3.animShow(TIME_ANIM) { isStartTimer = true }
        }
    }

}