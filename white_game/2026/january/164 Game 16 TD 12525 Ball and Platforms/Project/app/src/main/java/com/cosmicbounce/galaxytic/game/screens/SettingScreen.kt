package com.cosmicbounce.galaxytic.game.screens

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.cosmicbounce.galaxytic.game.LibGDXGame
import com.cosmicbounce.galaxytic.game.actors.AButton
import com.cosmicbounce.galaxytic.game.actors.checkbox.ACheckBox
import com.cosmicbounce.galaxytic.game.utils.HEIGHT_UI
import com.cosmicbounce.galaxytic.game.utils.TIME_ANIM
import com.cosmicbounce.galaxytic.game.utils.WIDTH_UI
import com.cosmicbounce.galaxytic.game.utils.actor.animHide
import com.cosmicbounce.galaxytic.game.utils.actor.animHideSuspend
import com.cosmicbounce.galaxytic.game.utils.actor.animShowSuspend
import com.cosmicbounce.galaxytic.game.utils.advanced.AdvancedScreen
import com.cosmicbounce.galaxytic.game.utils.advanced.AdvancedStage
import com.cosmicbounce.galaxytic.game.utils.region
import com.cosmicbounce.galaxytic.game.utils.runGDX
import com.cosmicbounce.galaxytic.game.utils.toMS
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SettingScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val boxMusic = ACheckBox(this, ACheckBox.Static.Type.DEFAULT)
    private val boxSound = ACheckBox(this, ACheckBox.Static.Type.DEFAULT)

    private val btnNazad = AButton(this, AButton.Static.Type.Nazad)
    private val imgTitle = Image(game.all.std)
    private val imgPanel = Image(game.all.SETTINGS)

    override fun show() {
        setBackBackground(game.all.backgrounds.random().region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                goAsteroids()

                addNazad()
                addImgTitle()
                addImgPanel()
                addBoxes()
            }

            launch { btnNazad.animByNY(TIME_ANIM, 221f, 153f) }
            launch { imgTitle.animByNY(TIME_ANIM, 124f, 886f) }
            launch { imgPanel.animByNY(TIME_ANIM, 24f, 352f) }
            delay((TIME_ANIM.toMS * 0.85f).toLong())
            launch { boxMusic.animShowSuspend(TIME_ANIM) }
            launch { boxSound.animShowSuspend(TIME_ANIM) }
        }
    }

    private fun AdvancedStage.addNazad() {
        addActor(btnNazad)
        btnNazad.apply {
            setBounds(221f, -110f, 194f, 108f)
            setOnClickListener {
                animHideScreen {
                    stageUI.root.animHide(TIME_ANIM) { game.navigationManager.back() }
                }
            }
        }
    }

    private fun AdvancedStage.addImgTitle() {
        addActor(imgTitle)
        imgTitle.setBounds(124f, HEIGHT_UI, 388f, 132f)
    }

    private fun AdvancedStage.addImgPanel() {
        addActor(imgPanel)
        imgPanel.setBounds(WIDTH_UI, 352f, 589f, 501f)
    }

    private fun AdvancedStage.addBoxes() {
        addActors(boxMusic, boxSound)
        boxMusic.apply {
            color.a = 0f
            setBounds(385f, 630f, 92f, 87f)
            if(game.musicUtil.music?.isPlaying == true) check(false)
            setOnCheckListener { isCheck ->
                if (isCheck) game.musicUtil.music?.play() else game.musicUtil.music?.stop()
            }
        }
        boxSound.apply {
            color.a = 0f
            setBounds(385f, 492f, 92f, 87f)
            if(game.soundUtil.isPause.not()) check(false)
            setOnCheckListener { isCheck ->
                game.soundUtil.isPause = isCheck.not()
            }
        }
    }

    // Anim ------------------------------------------------------------------------

    private fun animHideScreen(block: () -> Unit) {
        coroutine?.launch {
            launch { boxMusic.animHideSuspend(TIME_ANIM) }
            launch { boxSound.animHideSuspend(TIME_ANIM) }
            delay((TIME_ANIM.toMS * 0.85f).toLong())
            launch { btnNazad.animByNY_From(TIME_ANIM, 221f, -110f) }
            launch { imgTitle.animByNY_From(TIME_ANIM, 124f, HEIGHT_UI) }
            launch { imgPanel.animByNY_From(TIME_ANIM, WIDTH_UI, 352f) }

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