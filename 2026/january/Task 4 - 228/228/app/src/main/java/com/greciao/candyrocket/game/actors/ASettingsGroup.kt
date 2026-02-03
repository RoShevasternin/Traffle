package com.greciao.candyrocket.game.actors

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.greciao.candyrocket.game.manager.AudioManager
import com.greciao.candyrocket.game.utils.actor.setOnClickListener
import com.greciao.candyrocket.game.utils.advanced.AdvancedGroup
import com.greciao.candyrocket.game.utils.advanced.AdvancedScreen

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
        addAndFillActor(Image(assets.settings))
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
        soundVolumeGroup.setBounds(68f, 262f, 199f, 41f)

        soundVolumeGroup.update(soundCounter)
        soundUtil.volumeLevel = soundCounter * ONE_PERCENT
    }

    private fun addMusicVolumeGroup() {
        addActor(musicVolumeGroup)
        musicVolumeGroup.setBounds(68f, 137f, 199f, 41f)

        musicVolumeGroup.update(musicCounter)
        musicUtil.volumeLevelFlow.value = musicCounter * ONE_PERCENT
    }

    private fun addSoundBtn() {
        val minus = Actor()
        val plus  = Actor()

        addActors(minus, plus)

        minus.apply {
            setBounds(25f, 262f, 40f, 40f)
            setOnClickListener(soundUtil) {
                if (soundCounter-1 >= 0) {
                    soundCounter--
                    soundVolumeGroup.update(soundCounter)
                    soundUtil.volumeLevel = soundCounter * ONE_PERCENT
                }
            }
        }
        plus.apply {
            setBounds(270f, 262f, 40f, 40f)
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
            setBounds(25f, 136f, 40f, 40f)
            setOnClickListener(soundUtil) {
                if (musicCounter-1 >= 0) {
                    musicCounter--
                    musicVolumeGroup.update(musicCounter)
                    musicUtil.volumeLevelFlow.value = musicCounter * ONE_PERCENT
                }
            }
        }
        plus.apply {
            setBounds(270f, 136f, 40f, 40f)
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