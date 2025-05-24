package com.candies.balloons.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.candies.balloons.game.LibGDXGame
import com.candies.balloons.game.actors.AButton
import com.candies.balloons.game.actors.checkbox.ACheckBox
import com.candies.balloons.game.utils.TIME_ANIM
import com.candies.balloons.game.utils.WIDTH_UI
import com.candies.balloons.game.utils.actor.animHide
import com.candies.balloons.game.utils.actor.animShow
import com.candies.balloons.game.utils.actor.setOnClickListener
import com.candies.balloons.game.utils.advanced.AdvancedScreen
import com.candies.balloons.game.utils.advanced.AdvancedStage
import com.candies.balloons.game.utils.region
import com.candies.balloons.game.utils.runGDX
import kotlinx.coroutines.launch

class SettingScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        var IS_NOTIFICATION = false
    }

    private val musicBox = ACheckBox(this, ACheckBox.Static.Type.OFF_ON)
    private val soundBox = ACheckBox(this, ACheckBox.Static.Type.OFF_ON)
    private val notifBox = ACheckBox(this, ACheckBox.Static.Type.OFF_ON)

    private val btnMenu     = AButton(this, AButton.Static.Type.Dom)
    private val imgTitle    = Image(game.all.settings)
    private val imgLanguage = Image(game.all.language)
    private val imgEnglish  = Image(game.all.english)

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.splash.DARK.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addMenu()
                addImgTitle()
                addImgLanguage()
                addBoxes()
                addImgEnglish()
            }
        }
    }

    private fun AdvancedStage.addMenu() {
        addActor(btnMenu)
        btnMenu.apply {
            setBounds(880f, 1724f, 157f, 154f)
            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM) {
                    stageUI.root.animHide(TIME_ANIM) { game.navigationManager.back() }
                }
            }
        }
    }

    private fun AdvancedStage.addImgTitle() {
        addActor(imgTitle)
        imgTitle.setBounds(151f, 1489f, 724f, 276f)
    }

    private fun AdvancedStage.addImgEnglish() {
        addActor(imgEnglish)
        imgEnglish.color.a = 0f
        imgEnglish.setBounds(89f, -4f, 916f, 629f)
        var isVisible = false
        imgEnglish.setOnClickListener(game.soundUtil) {
            isVisible = !isVisible
            imgEnglish.clearActions()
            if (isVisible) imgEnglish.animShow(TIME_ANIM) else imgEnglish.animHide(TIME_ANIM)
        }
    }

    private fun AdvancedStage.addImgLanguage() {
        addActor(imgLanguage)
        imgLanguage.setBounds(89f, 285f, 916f, 1134f)
    }

    private fun AdvancedStage.addBoxes() {
        addActors(musicBox, soundBox, notifBox)
        musicBox.apply {
            setBounds(669f, 1034f, 301f, 154f)

            screen.game.musicUtil.music?.also { mu ->
                if (mu.isPlaying) check(false)

                setOnCheckListener { isCHeck ->
                    if (isCHeck) mu.play() else mu.pause()
                }
            }
        }
        soundBox.apply {
            setBounds(669f, 1245f, 301f, 154f)

            screen.game.soundUtil.also { su ->
                if (su.isPause.not()) check(false)

                setOnCheckListener { isCHeck ->
                    su.isPause = isCHeck.not()
                }
            }
        }
        notifBox.apply {
            setBounds(669f, 823f, 301f, 154f)
            if (IS_NOTIFICATION) check(false)
            setOnCheckListener { isCHeck ->
                IS_NOTIFICATION = isCHeck
            }
        }
    }

}