package com.royaltombsecrets.miniquizler.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.royaltombsecrets.miniquizler.R
import com.royaltombsecrets.miniquizler.appContext
import com.royaltombsecrets.miniquizler.game.LibGDXGame
import com.royaltombsecrets.miniquizler.game.actors.AButton
import com.royaltombsecrets.miniquizler.game.box2d.AbstractBody
import com.royaltombsecrets.miniquizler.game.box2d.WorldUtil
import com.royaltombsecrets.miniquizler.game.box2d.bodies.BItem
import com.royaltombsecrets.miniquizler.game.utils.*
import com.royaltombsecrets.miniquizler.game.utils.actor.animHide
import com.royaltombsecrets.miniquizler.game.utils.actor.animShow
import com.royaltombsecrets.miniquizler.game.utils.actor.setOnClickListener
import com.royaltombsecrets.miniquizler.game.utils.advanced.AdvancedBox2dScreen
import com.royaltombsecrets.miniquizler.game.utils.advanced.AdvancedStage
import com.royaltombsecrets.miniquizler.game.utils.font.FontParameter
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class GameScreen(override val game: LibGDXGame) : AdvancedBox2dScreen(WorldUtil()) {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS)
    private val font50        = fontGenerator_GlassAntiqua.generateFont(fontParameter.setCharacters(FontParameter.CharType.ALL).setSize(50))
    private val font40        = fontGenerator_GlassAntiqua.generateFont(fontParameter.setCharacters(FontParameter.CharType.ALL).setSize(40))
    private val font25        = fontGenerator_GlassAntiqua.generateFont(fontParameter.setCharacters(FontParameter.CharType.ALL).setSize(25))

    private val imgTop = Image(game.all.top)
    private val lblTop = Label(appContext.resources.getString(R.string.rules_text), Label.LabelStyle(font50, Color.WHITE))

    private val btnBack = AButton(this, AButton.Static.Type.Menu)

    private val imgA = Image(game.all.BLUE)
    private val imgB = Image(game.all.RED)
    private val imgC = Image(game.all.PURPLE)

    private val lblA = Label("", Label.LabelStyle(font50, Color.WHITE))
    private val lblB = Label("", Label.LabelStyle(font50, Color.WHITE))
    private val lblC = Label("", Label.LabelStyle(font50, Color.WHITE))

    private val imgTimer = Image(game.all.prpl)
    private val lblTimer = Label("Time: 0", Label.LabelStyle(font40, Color.WHITE))

    private val imgYellowPanel = Image(game.all.yellow)
    private val lblNotCorrect  = Label("Not correct: ${game.sharedPreferences.getInt("NotCorrect", 0)}", Label.LabelStyle(font25, Color.WHITE))
    private val lblAnswers     = Label("Answers: ${game.sharedPreferences.getInt("Answers", 0)}", Label.LabelStyle(font25, Color.WHITE))

    private var notCorrectCount = game.sharedPreferences.getInt("NotCorrect", 0)
    private var answersCount    = game.sharedPreferences.getInt("Answers", 0)

    // Body
    private val bItemList = List(10) { BItem(this) }

    // Field
    private val bItemFlow = MutableSharedFlow<BItem>(10)

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.splash.BACKGROUND.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        createB_Items()

        addBack()
        addImgTop()

        addImgABC()
        addLblAnswers()
        addTimer()
        addYellowPanel()

        start()
    }

    private fun AdvancedStage.addBack() {
        addActor(btnBack)
        btnBack.apply {
            setBounds(213f, 63f, 186f, 113f)
            setOnClickListener {
                animHideScreen {
                    stageUI.root.animHide(TIME_ANIM) { game.navigationManager.back() }
                }
            }
        }
    }

    private fun AdvancedStage.addImgTop() {
        addActor(imgTop)
        imgTop.setBounds(0f, 699f, 1521f, 201f)
        addActor(lblTop)
        lblTop.setBounds(34f, 699f, 1453f, 201f)
        lblTop.setAlignment(Align.center)
        lblTop.wrap = true
    }

    private fun AdvancedStage.addImgABC() {
        addActors(imgA, imgB, imgC)
        imgA.setBounds(87f, 236f, 398f, 445f)
        imgB.setBounds(541f, 236f, 398f, 445f)
        imgC.setBounds(995f, 236f, 398f, 445f)
    }

    private fun AdvancedStage.addLblAnswers() {
        addActors(lblA, lblB, lblC)
        lblA.setBounds(178f, 298f, 256f, 241f)
        lblB.setBounds(632f, 298f, 256f, 241f)
        lblC.setBounds(1086f, 298f, 256f, 241f)
        lblA.setAlignment(Align.center)
        lblB.setAlignment(Align.center)
        lblC.setAlignment(Align.center)
        lblA.wrap = true
        lblB.wrap = true
        lblC.wrap = true
        lblA.setOnClickListener {
            // Win
            game.soundUtil.apply { play(WIN, 0.5f) }
            answersCount++
            lblAnswers.setText("Answers: $answersCount")
            game.sharedPreferences.edit().putInt("Answers", answersCount).apply()
            start()
        }
        lblB.setOnClickListener {
            // Fail
            game.soundUtil.apply { play(FAIL, 1f) }
            notCorrectCount++
            lblNotCorrect.setText("Not Correct:$notCorrectCount")
            game.sharedPreferences.edit().putInt("NotCorrect", answersCount).apply()
            start()
        }
        lblC.setOnClickListener {
            // Fail
            game.soundUtil.apply { play(FAIL, 1f) }
            notCorrectCount++
            lblNotCorrect.setText("Not Correct:$notCorrectCount")
            game.sharedPreferences.edit().putInt("NotCorrect", answersCount).apply()
            start()
        }
    }

    private fun AdvancedStage.addTimer() {
        addActor(imgTimer)
        imgTimer.setBounds(668f, 63f, 185f, 113f)
        addActor(lblTimer)
        lblTimer.setBounds(699f, 100f, 124f, 41f)
        lblTimer.setAlignment(Align.center)
        lblTimer.wrap = true

        fun timer() {
            runGDX {
                coroutine?.launch {
                    var timer = 30
                    while (timer > 0 && isActive) {
                        delay(1000)
                        timer--
                        runGDX { lblTimer.setText("Time: $timer") }
                    }
                    cancel()
                    start()
                    timer()
                }
            }
        }

        timer()
    }


    // Logic  ------------------------------------------------------------------------

    private val answers = listOf(
        Answer("Narmer", "Ramses II", "Tutankhamun"),
        Answer("Nile", "Tigris", "Euphrates"),
        Answer("Pyramid of Khufu", "Pyramid of Khafre", "Pyramid of Menkaure"),
        Answer("Narmer", "Ram III", "Tutan VI"),
        Answer("The Sphinx", "Colossus of Rhodes", "The Great Barakana"),
        Answer("Osiris", "Ra", "Anubis"),
        Answer("The Book of the Dead", "The Book of Life", "The Book of Pharaohs"),
        Answer("Ramses II", "Thutmose III", "Hatshepsut"),
        Answer("Scepter", "Throne", "Staff"),
        Answer("Falcon", "Eagle", "Stork"),
        Answer("Thebes", "Memphis", "Alexandria"),
        Answer("Cow", "Cat", "Elephant"),
        Answer("Bronze", "Gold", "Silver"),
        Answer("Akhenaten", "Ramses III", "Tutankhamun"),
        Answer("Mummification", "Cremation", "Internment"),
        Answer("Tutankhamun", "Khufu", "Ptolemy I"),
        Answer("Papyrus", "Lotus", "Olive Tree"),
        Answer("Ramses II", "Thutmose I", "Senusret III"),
        Answer("Thoth", "Geb", "Sobek"),
        Answer("Cleopatra", "Nefertiti", "Hatshepsut"),
    )

    data class Answer(val yes: String, val no1: String, val no2: String)

    private fun start() {
        val randomIndex = (0..19).random()

        lblTop.setText(appContext.resources.getStringArray(R.array.questions)[randomIndex])
        val answer = answers[randomIndex]

        val lblX = listOf(178f, 632f, 1086f).shuffled()
        lblA.apply {
            x = lblX[0]
            setText(answer.yes)
        }
        lblB.apply {
            x = lblX[1]
            setText(answer.no1)
        }
        lblC.apply {
            x = lblX[2]
            setText(answer.no2)
        }

    }

    private fun AdvancedStage.addYellowPanel() {
        addActor(imgYellowPanel)
        imgYellowPanel.setBounds(1121f, 63f, 185f, 113f)

        addActors(lblNotCorrect, lblAnswers)
        lblNotCorrect.apply {
            setAlignment(Align.center)
            setBounds(1150f, 124f, 131f, 26f)
        }
        lblAnswers.apply {
            setAlignment(Align.center)
            setBounds(1163f, 89f, 105f, 26f)
        }
    }

    // Create Body ------------------------------------------------------------------------

    private fun createB_Items() {
        bItemList.onEach { bItem ->
            bItem.create(0f, HEIGHT_UI + 100f, 200f, 200f)

            var timer = 0f
            bItem.renderBlockArray.add(AbstractBody.RenderBlock {
                timer += it
                if (timer >= 1) {
                    timer = 0f
                    if ((bItem.body?.position?.y ?: 0f) < (-120f).toB2) {
                        if (bItem.isOnStart.getAndSet(false)) {
                            bItemFlow.tryEmit(bItem)
                        }
                    }
                }
            })

            bItemFlow.tryEmit(bItem)
        }

        val startPos = Vector2()

        coroutine?.launch {
            bItemFlow.collect { bItem ->
                bItem.body?.apply {
                    setLinearVelocity(0f, 0f)
                    isAwake      = false
                    gravityScale = 0f

                    runGDX {
                        val nx = (100..1420).random().toFloat()
                        setTransform(startPos.set(nx, HEIGHT_UI + 100f).toB2, 0f)
                        bItem.isOnStart.set(true)
                    }
                }
            }
        }
        coroutine?.launch {
            bItemFlow.collect { bItem ->
                delay((100..2000L).random())

                runGDX {
                    bItem.body?.apply {
                        gravityScale = 1f
                        isAwake = true

                        val torque = listOf(-50f, -25f, -10f, 50f, 25f, 10f).random()
                        applyTorque(torque, true)
                        applyForceToCenter(0f, -10f, true)
                    }
                }
            }
        }
    }

}