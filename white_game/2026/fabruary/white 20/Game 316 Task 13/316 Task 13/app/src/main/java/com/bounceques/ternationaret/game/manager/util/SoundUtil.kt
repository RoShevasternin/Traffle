package com.bounceques.ternationaret.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.bounceques.ternationaret.game.manager.AudioManager
import com.bounceques.ternationaret.game.manager.SoundManager
import com.bounceques.ternationaret.game.utils.runGDX
class SoundUtil {

    val BORDER = SoundManager.EnumSound.BORDER.data.sound
    val CLICK  = SoundManager.EnumSound.CLICK.data.sound
    val LOSE   = SoundManager.EnumSound.LOSE.data.sound
    val STAR   = SoundManager.EnumSound.STAR.data.sound
    val WIN    = SoundManager.EnumSound.WIN.data.sound

    // 0..100
    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound, volume: Float = volumeLevel) = runGDX {
        if (isPause.not()) sound.play(volume / 100f)
    }
}

