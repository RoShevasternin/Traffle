package com.swee.ttrio.comb.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.swee.ttrio.comb.game.LibGDXGame
import com.swee.ttrio.comb.game.actors.AButton
import com.swee.ttrio.comb.game.actors.AProgress
import com.swee.ttrio.comb.game.actors.checkbox.ACheckBox
import com.swee.ttrio.comb.game.utils.TIME_ANIM
import com.swee.ttrio.comb.game.utils.actor.animHide
import com.swee.ttrio.comb.game.utils.actor.animShow
import com.swee.ttrio.comb.game.utils.actor.setOnClickListener
import com.swee.ttrio.comb.game.utils.advanced.AdvancedScreen
import com.swee.ttrio.comb.game.utils.advanced.AdvancedStage
import com.swee.ttrio.comb.game.utils.region
import com.swee.ttrio.comb.game.utils.runGDX
import kotlinx.coroutines.launch

class SettingScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val btnMenu = AButton(this, AButton.Static.Type.Menu)
    private val imgMSV  = Image(game.all.SETTINGS)

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.all.BACKGROUND_2.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addMenu()
                addImgMSV()
                addPanel()
            }
        }
    }

    private fun AdvancedStage.addMenu() {
        addActor(btnMenu)
        btnMenu.apply {
            setBounds(26f,764f,51f,51f)
            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.back()
                }
            }
        }
    }

    private fun AdvancedStage.addImgMSV() {
        addActor(imgMSV)
        imgMSV.setBounds(0f,201f,390f,548f)
    }

    private fun AdvancedStage.addPanel() {
        val progressMusic = AProgress(this@SettingScreen)
        val progressSound = AProgress(this@SettingScreen)

        addActors(progressMusic, progressSound)

        progressMusic.apply {
            setBounds(22f,460f,346f,26f)

            progressPercentFlow.value = screen.game.musicUtil.volumeLevelFlow.value

            coroutine?.launch {
                progressPercentFlow.collect {
                    runGDX {
                        screen.game.musicUtil.volumeLevelFlow.value = it
                    }
                }
            }
        }
        progressSound.apply {
            setBounds(22f,280f,346f,26f)

            progressPercentFlow.value = screen.game.soundUtil.volumeLevel

            coroutine?.launch {
                progressPercentFlow.collect {
                    runGDX {
                        screen.game.soundUtil.volumeLevel = it
                    }
                }
            }
        }
    }

}