package com.viesta.delacoca.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.viesta.delacoca.game.manager.AudioManager
import com.viesta.delacoca.game.manager.SoundManager
import com.viesta.delacoca.game.utils.runGDX

class SoundUtil {

    // Common
    val clack        = SoundManager.EnumSound.clack.data.sound
    val goodresult   = SoundManager.EnumSound.goodresult.data.sound
    val wrong_answer = SoundManager.EnumSound.wrong_answer.data.sound

    // 0..100
    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound) = runGDX { if (isPause.not()) sound.play(volumeLevel / 100f) }
}