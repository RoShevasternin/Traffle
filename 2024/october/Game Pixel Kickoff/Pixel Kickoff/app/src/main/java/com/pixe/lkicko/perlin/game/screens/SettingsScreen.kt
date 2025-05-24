package com.pixe.lkicko.perlin.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.utils.Align
import com.pixe.lkicko.perlin.game.LibGDXGame
import com.pixe.lkicko.perlin.game.actors.AProgress
import com.pixe.lkicko.perlin.game.utils.TIME_ANIM
import com.pixe.lkicko.perlin.game.utils.actor.animHide
import com.pixe.lkicko.perlin.game.utils.actor.animShow
import com.pixe.lkicko.perlin.game.utils.actor.setOnClickListener
import com.pixe.lkicko.perlin.game.utils.advanced.AdvancedScreen
import com.pixe.lkicko.perlin.game.utils.advanced.AdvancedStage
import com.pixe.lkicko.perlin.game.utils.runGDX
import kotlinx.coroutines.launch

class SettingsScreen(override val game: LibGDXGame) : AdvancedScreen() {

    // Actors
    private val aBack = Actor()
    private val aPP   = Actor()
    private val aWEB  = Actor()
    private val progSound = AProgress(this)
    private val progMusic = AProgress(this)

    override fun show() {
        setUIBackground(game.all.background_6)
        stageUI.root.animHide()
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addBack()
        addPP()
        addWEB()

        addProgress()
    }

    private fun AdvancedStage.addBack() {
        addActor(aBack)
        aBack.setBounds(833f, 1811f, 218f, 90f)

        aBack.setOnClickListener(game.soundUtil) {
            root.animHide(TIME_ANIM) {
                game.navigationManager.back()
            }
        }
    }

    private fun AdvancedStage.addPP() {
        addActor(aPP)
        aPP.setBounds(288f, 1673f, 421f, 174f)

        aPP.setOnClickListener(game.soundUtil) {
            game.activity.showUrl(false, "https://pixel-kickoff.site/lander/pixel-kickoff/privacy.html")
        }
    }

    private fun AdvancedStage.addWEB() {
        addActor(aWEB)
        aWEB.setBounds(288f, 1494f, 421f, 174f)

        aWEB.setOnClickListener(game.soundUtil) {
            game.activity.showUrl(false, "https://pixel-kickoff.site/")
        }
    }

    private fun AdvancedStage.addProgress() {
        addActor(progSound)
        progSound.setBounds(105f, 1131f, 914f, 201f)
        progSound.setOrigin(Align.center)
        progSound.rotation = 10f
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
        progMusic.setBounds(105f, 798f, 914f, 201f)
        progMusic.setOrigin(Align.center)
        progMusic.rotation = 10f
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