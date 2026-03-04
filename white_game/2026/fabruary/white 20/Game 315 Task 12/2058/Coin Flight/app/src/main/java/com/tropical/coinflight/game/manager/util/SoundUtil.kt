package com.tropical.coinflight.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.tropical.coinflight.game.manager.AudioManager
import com.tropical.coinflight.game.manager.SoundManager
import com.tropical.coinflight.game.utils.runGDX
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

