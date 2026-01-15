package com.flightcoll.bridgertons.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.flightcoll.bridgertons.game.manager.AudioManager
import com.flightcoll.bridgertons.game.utils.runGDX
import com.flightcoll.bridgertons.game.manager.SoundManager

class SoundUtil {

    val TOUCH_DOWN  = SoundManager.EnumSound.TOUCH_DOWN.data.sound
    val barier           = SoundManager.EnumSound.barier.data.sound
    val xxx          = SoundManager.EnumSound.xxx.data.sound
    val compass          = SoundManager.EnumSound.compass.data.sound

    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound) = runGDX { if (isPause.not()) sound.play(volumeLevel / 100f) }
}