package com.cosmostarsca.quantumfruits.game.screens

import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.cosmostarsca.quantumfruits.game.LibGDXGame
import com.cosmostarsca.quantumfruits.game.actors.AButton
import com.cosmostarsca.quantumfruits.game.actors.AProgress
import com.cosmostarsca.quantumfruits.game.utils.GColor
import com.cosmostarsca.quantumfruits.game.utils.TIME_ANIM
import com.cosmostarsca.quantumfruits.game.utils.actor.animHide
import com.cosmostarsca.quantumfruits.game.utils.actor.animShow
import com.cosmostarsca.quantumfruits.game.utils.advanced.AdvancedScreen
import com.cosmostarsca.quantumfruits.game.utils.advanced.AdvancedStage
import com.cosmostarsca.quantumfruits.game.utils.font.FontParameter
import com.cosmostarsca.quantumfruits.game.utils.region
import com.cosmostarsca.quantumfruits.game.utils.runGDX
import kotlinx.coroutines.launch

class SettingsScreen(override val game: LibGDXGame): AdvancedScreen() {

    private val param = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars + "%").setSize(54)
    private val font  = fontGenerator_Regular.generateFont(param)

    private val settings = Image(game.assets.settings)
    private val back     = AButton(this, AButton.Static.Type.Back)
    private val musicLbl = Label("", Label.LabelStyle(font, GColor.black))
    private val soundLbl = Label("", Label.LabelStyle(font, GColor.black))

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.load.Loader.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addActor(settings.apply { setBounds(175f, 296f, 1177f, 522f) })

        addPanel()

        addActor(back)
        back.apply {
            setBounds(529f, 26f, 469f, 147f)
            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.back()
                }
            }
        }

        addActors(musicLbl, soundLbl)
        musicLbl.apply {
            this.touchable = Touchable.disabled
            setBounds(693f, 619f, 142f, 90f)
            setAlignment(Align.center)
        }
        soundLbl.apply {
            this.touchable = Touchable.disabled
            setBounds(693f, 405f, 142f, 90f)
            setAlignment(Align.center)
        }
    }

    private fun AdvancedStage.addPanel() {
        val progressMusic = AProgress(this@SettingsScreen)
        val progressSound = AProgress(this@SettingsScreen)

        addActors(progressMusic, progressSound)

        progressMusic.apply {
            setBounds(183f, 608f, 1161f, 112f)

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
        progressSound.apply {
            setBounds(183f, 394f, 1161f, 112f)

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

}