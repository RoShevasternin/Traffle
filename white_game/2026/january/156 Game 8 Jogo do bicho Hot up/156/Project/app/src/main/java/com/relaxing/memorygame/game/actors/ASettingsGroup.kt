package com.relaxing.memorygame.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.relaxing.memorygame.game.actors.progress.ADerevoProgress
import com.relaxing.memorygame.game.utils.advanced.AdvancedGroup
import com.relaxing.memorygame.game.utils.advanced.AdvancedScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ASettingsGroup(override val screen: AdvancedScreen): AdvancedGroup() {

    private val musicProgress = ADerevoProgress(screen)
    private val soundProgress = ADerevoProgress(screen)

    override fun addActorsOnGroup() {
        addAndFillActor(Image(screen.game.gameAssets.SETTINGS))
        addProgress()
    }

    private fun addProgress() {
        addActors(musicProgress, soundProgress)
        musicProgress.apply {
            setBounds(203f, 498f, 386f, 35f)
            setProgressPercent(screen.game.musicUtil.music!!.volume * 100f)

            coroutine?.launch(Dispatchers.Default) {
                progressPercentFlow.collect {
                    screen.game.musicUtil.volumeLevelFlow.value = it / 100f
                }
            }
        }
        soundProgress.apply {
            setBounds(203f, 716f, 386f, 35f)
            setProgressPercent(screen.game.soundUtil.volumeLevel * 100f)

            coroutine?.launch(Dispatchers.Default) {
                progressPercentFlow.collect {
                    screen.game.soundUtil.volumeLevel = it / 100f
                }
            }
        }
    }

}