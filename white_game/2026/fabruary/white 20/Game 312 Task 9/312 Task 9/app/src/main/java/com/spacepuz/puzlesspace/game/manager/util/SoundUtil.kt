package com.spacepuz.puzlesspace.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.spacepuz.puzlesspace.game.manager.AudioManager
import com.spacepuz.puzlesspace.game.manager.SoundManager

class SoundUtil {

    val click      = AdvancedSound(SoundManager.EnumSound.click_game.data.sound, 0.5f)
    val lose_game  = AdvancedSound(SoundManager.EnumSound.lose_game.data.sound, 1f)
    val touch_game = AdvancedSound(SoundManager.EnumSound.touch_game.data.sound, 0.5f)
    val win_game   = AdvancedSound(SoundManager.EnumSound.win_game.data.sound, 1f)

    // 0..100
    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(advancedSound: AdvancedSound, playCoff: Float = 1f) {
        if (isPause.not()) {
            advancedSound.apply {
                sound.play(((volumeLevel / 100f) * coff) * playCoff)
            }
        }
    }

    data class AdvancedSound(val sound: Sound, val coff: Float)
}