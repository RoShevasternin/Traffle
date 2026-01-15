package com.fontantwo.tennisfortwo.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.fontantwo.tennisfortwo.game.actors.AProgress
import com.fontantwo.tennisfortwo.game.actors.button.AButton
import com.fontantwo.tennisfortwo.game.utils.GameColor
import com.fontantwo.tennisfortwo.game.utils.advanced.AdvancedGroup
import com.fontantwo.tennisfortwo.game.utils.advanced.AdvancedScreen
import com.fontantwo.tennisfortwo.game.utils.font.FontParameter
import com.fontantwo.tennisfortwo.game.utils.runGDX
import kotlinx.coroutines.launch

class APanelSettings(override val screen: AdvancedScreen): AdvancedGroup() {

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars + "Music :% Sound").setSize(27)
    private val font      = screen.fontGenerator_Langar.generateFont(parameter)

    private val labelStyle = LabelStyle(font, GameColor.whiter)

    val stolImg = Image(screen.game.assetsAll.COSMO_STOL)
    val backBtn = AButton(screen, AButton.Static.Type.Back)

    val musicProgress    = AProgress(screen)
    val soundProgress    = AProgress(screen)
    val musicLbl         = Label("Music:", labelStyle)
    val soundLbl         = Label("Sound:", labelStyle)
    val musicProgressLbl = Label("", labelStyle)
    val soundProgressLbl = Label("", labelStyle)

    override fun addActorsOnGroup() {
        addStol()
        addBack()
        addProgress()
        addPercentage()
    }

    // Actors ------------------------------------------------------------------------
    private fun addStol() {
        addActor(stolImg)
        stolImg.setBounds(90f, 152f, 1227f, 615f)
    }

    private fun addBack() {
        addActor(backBtn)
        backBtn.setBounds(1407f, 22f, 133f, 125f)
    }

    private fun addProgress() {
        addActors(musicProgress, soundProgress)
        // Music
        musicProgress.apply {
            setBounds(272f, 498f, 864f, 54f)
            progressPercentFlow.value = screen.game.musicUtil.volumeLevelFlow.value

            coroutine?.launch {
                progressPercentFlow.collect {
                    runGDX {
                        musicProgressLbl.setText("${it.toInt()}%")
                        screen.game.musicUtil.volumeLevelFlow.value = it
                    }
                }
            }
        }
        // Sound
        soundProgress.apply {
            setBounds(272f, 325f, 864f, 54f)
            progressPercentFlow.value = screen.game.soundUtil.volumeLevel

            coroutine?.launch {
                progressPercentFlow.collect {
                    runGDX {
                        soundProgressLbl.setText("${it.toInt()}%")
                        screen.game.soundUtil.volumeLevel = it
                    }
                }
            }
        }
    }

    private fun addPercentage() {
        addActors(musicLbl, soundLbl)
        musicLbl.setBounds(272f, 556f, 78f, 38f)
        soundLbl.setBounds(983f, 383f, 83f, 38f)

        addActors(musicProgressLbl, soundProgressLbl)
        musicProgressLbl.setBounds(362f, 556f, 62f, 38f)
        soundProgressLbl.setBounds(1073f, 383f, 63f, 38f)
        soundProgressLbl.setAlignment(Align.right)
    }

}