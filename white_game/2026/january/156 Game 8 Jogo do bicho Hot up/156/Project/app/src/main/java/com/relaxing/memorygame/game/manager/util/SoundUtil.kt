package com.relaxing.memorygame.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.relaxing.memorygame.game.manager.AudioManager
import com.relaxing.memorygame.game.manager.SoundManager
import com.relaxing.memorygame.game.utils.runGDX

class SoundUtil {

    // Common
    val CLICK  = SoundManager.EnumSound.TOUCH.data.sound
    val LOSE   = SoundManager.EnumSound.LOSE.data.sound
    val NEPARA = SoundManager.EnumSound.NEPARA.data.sound
    val URA    = SoundManager.EnumSound.URA.data.sound
    val PARA   = SoundManager.EnumSound.PARA.data.sound

    var volumeLevel = AudioManager.volumeLevelPercent / 100f

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound) = runGDX { if (isPause.not()) sound.play(volumeLevel) }
}