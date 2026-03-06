package com.parrotrun.skydrink.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.parrotrun.skydrink.game.manager.AudioManager
import com.parrotrun.skydrink.game.manager.SoundManager
import com.parrotrun.skydrink.game.utils.runGDX
class SoundUtil {

    // Common
    val bonus = SoundManager.EnumSound.bonus.data.sound
    val boom = SoundManager.EnumSound.boom.data.sound
    val click = SoundManager.EnumSound.click.data.sound
    val lose = SoundManager.EnumSound.lose.data.sound
    val win = SoundManager.EnumSound.win.data.sound

    // 0..100
    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound, volume: Float = volumeLevel) = runGDX {
        if (isPause.not()) sound.play(volume / 100f)
    }
}

