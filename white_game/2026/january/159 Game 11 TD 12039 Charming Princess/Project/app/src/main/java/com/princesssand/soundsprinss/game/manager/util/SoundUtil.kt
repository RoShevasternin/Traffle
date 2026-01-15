package com.princesssand.soundsprinss.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.princesssand.soundsprinss.game.manager.AudioManager
import com.princesssand.soundsprinss.game.utils.runGDX
import com.princesssand.soundsprinss.game.manager.SoundManager

class SoundUtil {

    val click = SoundManager.EnumSound.click.data.sound
    val get   = SoundManager.EnumSound.get.data.sound
    val save  = SoundManager.EnumSound.save.data.sound
    val win   = SoundManager.EnumSound.win.data.sound
    val border = SoundManager.EnumSound.border.data.sound
    val touch  = SoundManager.EnumSound.touch.data.sound


    // 0..100
    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound, volume: Float = volumeLevel) = runGDX { if (isPause.not()) sound.play(volume / 100f) }
}