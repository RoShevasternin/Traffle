package com.royaltombsecrets.miniquizler.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.royaltombsecrets.miniquizler.game.manager.AudioManager
import com.royaltombsecrets.miniquizler.game.utils.runGDX
import com.royaltombsecrets.miniquizler.game.manager.SoundManager

class SoundUtil {

    val click = SoundManager.EnumSound.TOUCH.data.sound
    val FAIL  = SoundManager.EnumSound.FAIL.data.sound
    val WIN   = SoundManager.EnumSound.WIN.data.sound

    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound, coff: Float = 1f) = runGDX { if (isPause.not()) sound.play((volumeLevel / 100f) * coff) }
}