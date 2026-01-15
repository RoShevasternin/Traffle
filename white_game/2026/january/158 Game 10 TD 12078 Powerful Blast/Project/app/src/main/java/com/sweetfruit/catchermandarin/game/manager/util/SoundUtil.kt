package com.sweetfruit.catchermandarin.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.sweetfruit.catchermandarin.game.manager.AudioManager
import com.sweetfruit.catchermandarin.game.utils.runGDX
import com.sweetfruit.catchermandarin.game.manager.SoundManager

class SoundUtil {

    val boom = SoundManager.EnumSound.boom.data.sound
    val clik = SoundManager.EnumSound.clik.data.sound

    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound) = runGDX { if (isPause.not()) sound.play(volumeLevel / 100f) }
}