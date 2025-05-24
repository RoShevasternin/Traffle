package com.slotscity.official.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.slotscity.official.game.manager.AudioManager
import com.slotscity.official.game.manager.SoundManager
import com.slotscity.official.game.utils.runGDX
class SoundUtil {

    // Common
    val boom  = SoundManager.EnumSound.boom.data.sound
    val click = SoundManager.EnumSound.click.data.sound
    val start = SoundManager.EnumSound.start.data.sound
    val laser = SoundManager.EnumSound.laser.data.sound
    val game_bonus = SoundManager.EnumSound.game_bonus.data.sound

    // 0..100
    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound, volume: Float = volumeLevel) = runGDX {
        if (isPause.not()) sound.play(volume / 100f)
    }
}

