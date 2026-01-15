package com.skyplane.puzzleflight.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.skyplane.puzzleflight.game.manager.AudioManager
import com.skyplane.puzzleflight.game.manager.SoundManager
import com.skyplane.puzzleflight.game.utils.runGDX

class SoundUtil {

    // Common
    val bonus = SoundManager.EnumSound.bonus.data.sound
    val hit   = SoundManager.EnumSound.hit.data.sound
    val lose  = SoundManager.EnumSound.lose.data.sound
    val touch = SoundManager.EnumSound.touch.data.sound

    // 0..100
    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound) = runGDX { if (isPause.not()) sound.play(volumeLevel / 100f) }
}