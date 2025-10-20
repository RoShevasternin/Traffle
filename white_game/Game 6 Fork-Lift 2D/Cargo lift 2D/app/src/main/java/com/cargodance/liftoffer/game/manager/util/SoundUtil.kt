package com.cargodance.liftoffer.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.cargodance.liftoffer.game.manager.AudioManager
import com.cargodance.liftoffer.game.manager.SoundManager
import com.cargodance.liftoffer.game.utils.runGDX
class SoundUtil {


    val BOX   = SoundManager.EnumSound.BOX.data.sound
    val CLICK = SoundManager.EnumSound.CLICK.data.sound
    val TRACTOR_START = SoundManager.EnumSound.TRACTOR_START.data.sound
    val TRACTOR_GO    = SoundManager.EnumSound.TRACTOR_GO.data.sound
    val FORK = SoundManager.EnumSound.FORK.data.sound

    // 0..100
    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound, volume: Float = volumeLevel) = runGDX {
        if (isPause.not()) sound.play(volume / 100f)
    }
}

