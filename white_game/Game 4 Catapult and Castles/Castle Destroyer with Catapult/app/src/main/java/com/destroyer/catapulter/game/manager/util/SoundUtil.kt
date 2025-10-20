package com.destroyer.catapulter.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.destroyer.catapulter.game.manager.AudioManager
import com.destroyer.catapulter.game.manager.SoundManager
import com.destroyer.catapulter.game.utils.runGDX
class SoundUtil {

    val BRICK     = SoundManager.EnumSound.BRICK.data.sound
    val CATAPULTA = SoundManager.EnumSound.CATAPULTA.data.sound

    // 0..100
    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound, volume: Float = volumeLevel) = runGDX {
        if (isPause.not()) sound.play(volume / 100f)
    }
}

