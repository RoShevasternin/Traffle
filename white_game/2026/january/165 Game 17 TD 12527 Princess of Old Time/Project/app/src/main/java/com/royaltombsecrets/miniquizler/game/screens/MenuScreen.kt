package com.royaltombsecrets.miniquizler.game.screens

import android.content.pm.ActivityInfo
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.royaltombsecrets.miniquizler.game.LibGDXGame
import com.royaltombsecrets.miniquizler.game.actors.AButton
import com.royaltombsecrets.miniquizler.game.actors.checkbox.ACheckBox
import com.royaltombsecrets.miniquizler.game.box2d.AbstractBody
import com.royaltombsecrets.miniquizler.game.box2d.WorldUtil
import com.royaltombsecrets.miniquizler.game.box2d.bodies.BItem
import com.royaltombsecrets.miniquizler.game.utils.*
import com.royaltombsecrets.miniquizler.game.utils.actor.animHide
import com.royaltombsecrets.miniquizler.game.utils.actor.animShow
import com.royaltombsecrets.miniquizler.game.utils.advanced.AdvancedBox2dScreen
import com.royaltombsecrets.miniquizler.game.utils.advanced.AdvancedStage
import com.royaltombsecrets.miniquizler.game.utils.font.FontParameter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class MenuScreen(override val game: LibGDXGame) : AdvancedBox2dScreen(WorldUtil()) {

    companion object {
        private var isFirst = true
    }

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font47        = fontGenerator_GlassAntiqua.generateFont(fontParameter.setSize(47))

    private val imgSettPanel   = Image(game.all.left)
    private val imgYellowPanel = Image(game.all.yellow)

    private val btnGive  = AButton(this, AButton.Static.Type.Give)
    private val btnRules = AButton(this, AButton.Static.Type.Rules)
    private val btnView  = AButton(this, AButton.Static.Type.View)
    private val btnExit  = AButton(this, AButton.Static.Type.Exit)

    private val boxMusic = ACheckBox(this, ACheckBox.Static.Type.MUSIC)
    private val boxSound = ACheckBox(this, ACheckBox.Static.Type.SOUND)

    private val lblNotCorrect = Label("Not correct: ${game.sharedPreferences.getInt("NotCorrect", 0)}", Label.LabelStyle(font47, Color.WHITE))
    private val lblAnswers    = Label("Answers: ${game.sharedPreferences.getInt("Answers", 0)}", Label.LabelStyle(font47, Color.WHITE))

    // Body
    private val bItemList = List(10) { BItem(this) }

    // Field
    private val bItemFlow = MutableSharedFlow<BItem>(10)

    override fun show() {
        game.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        if (isFirst) {
            isFirst = false
            game.musicUtil.apply {
                music = musidal.apply {
                    isLooping = true
                    volumeLevelFlow.value = 22f
                }
            }
        }

        stageUI.root.animHide()
        setBackBackground(game.splash.BACKGROUND.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        createB_Items()

        addSettingsPanel()
        addYellowPanel()
        addBtns()
        addBoxes()
    }

    private fun AdvancedStage.addSettingsPanel() {
        addActor(imgSettPanel)
        imgSettPanel.setBounds(0f, 233f, 201f, 434f)
    }

    private fun AdvancedStage.addYellowPanel() {
        addActor(imgYellowPanel)
        imgYellowPanel.setBounds(314f, 573f, 351f, 214f)

        addActors(lblNotCorrect, lblAnswers)
        lblNotCorrect.apply {
            setAlignment(Align.center)
            setBounds(368f, 688f, 248f, 49f)
        }
        lblAnswers.apply {
            setAlignment(Align.center)
            setBounds(393f, 622f, 198f, 49f)
        }
    }

    private fun AdvancedStage.addBtns() {
        addActors(btnGive, btnRules, btnView, btnExit)
        btnGive.apply {
            setBounds(279f, 236f, 420f, 255f)
            setOrigin(Align.center)
            setOnClickListener {
                animHideScreen {
                    game.navigationManager.navigate(GameScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        btnRules.apply {
            setBounds(777f, 397f, 289f, 176f)
            setOrigin(Align.center)
            setOnClickListener {
                animHideScreen {
                    game.navigationManager.navigate(CollectInfoScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        btnView.apply {
            setBounds(777f, 156f, 289f, 176f)
            setOrigin(Align.center)
            setOnClickListener {
                animHideScreen {
                    game.navigationManager.navigate(CollectionScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        btnExit.apply {
            setBounds(1159f, 294f, 230f, 140f)
            setOnClickListener {
                animHideScreen {
                    stageUI.root.animHide(TIME_ANIM) { game.navigationManager.exit() }
                }
            }
        }
    }

    private fun AdvancedStage.addBoxes() {
        addActors(boxMusic, boxSound)
        boxMusic.apply {
            setBounds(27f, 467f, 136f, 136f)
            if (game.musicUtil.music?.isPlaying == false) check(false)
            setOnCheckListener { isCheck ->
                if (isCheck) game.musicUtil.music?.pause() else game.musicUtil.music?.play()
            }
        }
        boxSound.apply {
            setBounds(50f, 313f, 100f, 100f)
            if (game.soundUtil.isPause) check(false)
            setOnCheckListener { isCheck ->
                game.soundUtil.isPause = isCheck
            }
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