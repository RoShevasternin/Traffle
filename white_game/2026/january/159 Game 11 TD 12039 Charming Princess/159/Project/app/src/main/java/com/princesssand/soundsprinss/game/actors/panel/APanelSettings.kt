package com.princesssand.soundsprinss.game.actors.panel

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.princesssand.soundsprinss.game.actors.AProgress
import com.princesssand.soundsprinss.game.actors.ATutorials
import com.princesssand.soundsprinss.game.actors.button.AButton
import com.princesssand.soundsprinss.game.utils.actor.disable
import com.princesssand.soundsprinss.game.utils.actor.enable
import com.princesssand.soundsprinss.game.utils.advanced.AdvancedGroup
import com.princesssand.soundsprinss.game.utils.advanced.AdvancedScreen
import com.princesssand.soundsprinss.game.utils.font.FontParameter
import com.princesssand.soundsprinss.game.utils.runGDX
import kotlinx.coroutines.launch

class APanelSettings(override val screen: AdvancedScreen): AdvancedGroup() {

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars + "%").setSize(30)
    private val font      = screen.fontGenerator_Merienda.generateFont(parameter)

    private val panel = Image(screen.game.assetsAll.panel)
    private val mssn  = Image(screen.game.assetsAll.ms_sn)
    val back  = AButton(screen, AButton.Static.Type.Back)

    val musicProgress = AProgress(screen)
    val soundProgress = AProgress(screen)
    private val musicLbl = Label("", Label.LabelStyle(font, Color.WHITE))
    private val soundLbl = Label("", Label.LabelStyle(font, Color.WHITE))

    var hideBlock: (() -> Unit) -> Unit = {}

    override fun addActorsOnGroup() {
        addAndFillActor(panel)
        addMusicSound()
        addBack()
        addProgress()
        addPercentage()

        if (screen.game.isTutorialsUtil.isTutorials) {
            when (ATutorials.STEP) {
                ATutorials.Static.Step.SettingsMusic -> {
                    children.onEach { it.disable() }
                    musicProgress.enable()
                }
                else -> {}
            }
        }
    }

    // Add Actors
    private fun addMusicSound() {
        addActor(mssn)
        mssn.setBounds(88f, 171f, 101f, 198f)
    }

    private fun addBack() {
        addActor(back)
        back.setBounds(676f, 25f, 98f, 58f)
        back.setOnClickListener {
            hideBlock {
                if (screen.game.isTutorialsUtil.isTutorials) ATutorials.nextStep()
                screen.game.navigationManager.back()
            }
        }
    }

    private fun addProgress() {
        addActors(musicProgress, soundProgress)
        // Music
        musicProgress.apply {
            setBounds(88f, 273f, 637f, 52f)
            progressPercentFlow.value = screen.game.musicUtil.volumeLevelFlow.value

            coroutine?.launch {
                progressPercentFlow.collect {
                    runGDX {
                        musicLbl.setText("${it.toInt()}%")
                        screen.game.musicUtil.volumeLevelFlow.value = it
                    }
                }
            }
        }
        // Sound
        soundProgress.apply {
            setBounds(88f, 119f, 637f, 52f)
            progressPercentFlow.value = screen.game.soundUtil.volumeLevel

            coroutine?.launch {
                progressPercentFlow.collect {
                    runGDX {
                        soundLbl.setText("${it.toInt()}%")
                        screen.game.soundUtil.volumeLevel = it
                    }
                }
            }
        }
    }

    private fun addPercentage() {
        addActors(musicLbl, soundLbl)
        musicLbl.apply {
            setBounds(373f, 277f, 67f, 44f)
            setAlignment(Align.center)
            disable()
        }
        soundLbl.apply {
            setBounds(373f, 123f, 67f, 44f)
            setAlignment(Align.center)
            disable()
        }
    }

}