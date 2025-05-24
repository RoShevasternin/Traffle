package com.pinlq.esst.bloo.game.screens

import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.pinlq.esst.bloo.game.LibGDXGame
import com.pinlq.esst.bloo.game.actors.AButton
import com.pinlq.esst.bloo.game.actors.checkbox.ACheckBox
import com.pinlq.esst.bloo.game.utils.TIME_ANIM
import com.pinlq.esst.bloo.game.utils.actor.animHideScreen
import com.pinlq.esst.bloo.game.utils.actor.animShowScreen
import com.pinlq.esst.bloo.game.utils.advanced.AdvancedScreen
import com.pinlq.esst.bloo.game.utils.advanced.AdvancedStage
import com.pinlq.esst.bloo.game.utils.region
import com.pinlq.esst.bloo.game.utils.runGDX
import com.pinlq.esst.bloo.game.actors.checkbox.ACheckBoxGroup
import com.pinlq.esst.bloo.game.actors.progress.AProgress
import com.pinlq.esst.bloo.game.utils.actor.setOnClickListener
import kotlinx.coroutines.launch

class SettScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val imgPanel  = Image(game.all.setter)
    private val btnBack   = AButton(this, AButton.Static.Type.Back)
    private val listCheck = List(5) { ACheckBox(this, ACheckBox.Static.Type.CHECKER) }
    private val progMusic = AProgress(this)
    private val progSound = AProgress(this)

    override fun show() {
          setBackBackground(game.splash.listBackground[MenuScreen.BACKGROUND_INDEX].region)
        super.show()
        stageUI.root.animShowScreen(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addImgPanel()
                addBtnBack()

                addImgBackground()
                addBoxBackground()
                addProgress()
            }
        }
    }

    private fun AdvancedStage.addImgPanel() {
        addActor(imgPanel)
        imgPanel.setBounds(70f,490f,940f,1188f)
    }

    private fun AdvancedStage.addBtnBack() {
        addActor(btnBack)
        btnBack.setBounds(298f,114f,484f,239f)
        btnBack.setOnClickListener {
            stageUI.root.animHideScreen(TIME_ANIM) {
                game.navigationManager.back()
            }
        }
    }

    private fun AdvancedStage.addImgBackground() {
        var nx = 149f

        game.splash.listBackground.onEachIndexed { index, texture ->
            val img = Image(texture)
            addActor(img)
            img.setBounds(nx,542f,130f,232f)
            nx += 32+130

            img.setOnClickListener(game.soundUtil) {
                listCheck[index].check()
            }
        }
    }

    private fun AdvancedStage.addBoxBackground() {
        var nx  = 189f
        val cbg = ACheckBoxGroup()

        listCheck.onEachIndexed { index, aCheckBox ->

            aCheckBox.checkBoxGroup = cbg
            if (MenuScreen.BACKGROUND_INDEX == index) aCheckBox.check(false)

            addActor(aCheckBox)
            aCheckBox.setBounds(nx,750f,51f,51f)
            nx += 112+51

            aCheckBox.touchable = Touchable.disabled

            aCheckBox.setOnCheckListener {
                if (it) {
                    MenuScreen.BACKGROUND_INDEX = index
                    setBackBackground(game.splash.listBackground[index].region)
                }
            }
        }
    }

    private fun AdvancedStage.addProgress() {
        addActor(progSound)
        progSound.setBounds(206f, 942f, 669f, 72f)
        progSound.apply {
            progressPercentFlow.value = screen.game.soundUtil.volumeLevel

            coroutine?.launch {
                progressPercentFlow.collect {
                    runGDX {
                        screen.game.soundUtil.volumeLevel = it
                    }
                }
            }
        }

        addActor(progMusic)
        progMusic.setBounds(206f, 1186f, 669f, 72f)
        progMusic.apply {
            progressPercentFlow.value = screen.game.musicUtil.volumeLevelFlow.value

            coroutine?.launch {
                progressPercentFlow.collect {
                    runGDX {
                        screen.game.musicUtil.volumeLevelFlow.value = it
                    }
                }
            }
        }
    }

}