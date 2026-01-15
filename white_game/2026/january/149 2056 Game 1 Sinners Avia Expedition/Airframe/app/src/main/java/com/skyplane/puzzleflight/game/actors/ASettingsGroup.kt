package com.skyplane.puzzleflight.game.actors

import com.badlogic.gdx.scenes.scene2d.Actor
import com.skyplane.puzzleflight.game.manager.AudioManager
import com.skyplane.puzzleflight.game.utils.actor.setOnClickListener
import com.skyplane.puzzleflight.game.utils.advanced.AdvancedGroup
import com.skyplane.puzzleflight.game.utils.advanced.AdvancedScreen

class ASettingsGroup(override val screen: AdvancedScreen): AdvancedGroup() {

    companion object {
        private const val ONE_PERCENT = 100f / 6f
        private val currentVolumeLevelPercent = AudioManager.volumeLevelPercent

        // min 0 | max 6
        private var soundCounter = (currentVolumeLevelPercent / ONE_PERCENT).toInt()
        private var musicCounter = (currentVolumeLevelPercent / ONE_PERCENT).toInt()
    }

    private val assets    = screen.game.allAssets
    private val soundUtil = screen.game.soundUtil
    private val musicUtil = screen.game.musicUtil

    private val soundVolumeGroup = AVolumeGroup(screen)
    private val musicVolumeGroup = AVolumeGroup(screen)

    override fun addActorsOnGroup() {
        addSoundVolumeGroup()
        addMusicVolumeGroup()
        addSoundBtn()
        addMusicBtn()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun addSoundVolumeGroup() {
        addActor(soundVolumeGroup)
        soundVolumeGroup.setBounds(119f, 338f, 235f, 48f)

        soundVolumeGroup.update(soundCounter)
        soundUtil.volumeLevel = soundCounter * ONE_PERCENT
    }

    private fun addMusicVolumeGroup() {
        addActor(musicVolumeGroup)
        musicVolumeGroup.setBounds(119f, 172f, 235f, 48f)

        musicVolumeGroup.update(musicCounter)
        musicUtil.volumeLevelFlow.value = musicCounter * ONE_PERCENT
    }

    private fun addSoundBtn() {
        val minus = Actor()
        val plus  = Actor()

        addActors(minus, plus)

        minus.apply {
            setBounds(57f, 338f, 48f, 48f)
            setOnClickListener(soundUtil) {
                if (soundCounter-1 >= 0) {
                    soundCounter--
                    soundVolumeGroup.update(soundCounter)
                    soundUtil.volumeLevel = soundCounter * ONE_PERCENT
                }
            }
        }
        plus.apply {
            setBounds(368f, 338f, 48f, 48f)
            setOnClickListener(soundUtil) {
                if (soundCounter+1 <= 6) {
                    soundCounter++
                    soundVolumeGroup.update(soundCounter)
                    soundUtil.volumeLevel = soundCounter * ONE_PERCENT
                }
            }
        }
    }

    private fun addMusicBtn() {
        val minus = Actor()
        val plus  = Actor()

        addActors(minus, plus)

        minus.apply {
            setBounds(57f, 172f, 48f, 48f)
            setOnClickListener(soundUtil) {
                if (musicCounter-1 >= 0) {
                    musicCounter--
                    musicVolumeGroup.update(musicCounter)
                    musicUtil.volumeLevelFlow.value = musicCounter * ONE_PERCENT
                }
            }
        }
        plus.apply {
            setBounds(368f, 172f, 48f, 48f)
            setOnClickListener(soundUtil) {
                if (musicCounter+1 <= 6) {
                    musicCounter++
                    musicVolumeGroup.update(musicCounter)
                    musicUtil.volumeLevelFlow.value = musicCounter * ONE_PERCENT
                }
            }
        }
    }

}