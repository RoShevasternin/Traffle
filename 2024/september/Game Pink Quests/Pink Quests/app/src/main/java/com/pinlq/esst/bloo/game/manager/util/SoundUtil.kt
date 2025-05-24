package com.pinlq.esst.bloo.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.pinlq.esst.bloo.game.manager.AudioManager
import com.pinlq.esst.bloo.game.utils.runGDX
import com.pinlq.esst.bloo.game.manager.SoundManager

class SoundUtil {

    val CLICK   = SoundManager.EnumSound.click.data.sound
    val BORDER  = SoundManager.EnumSound.border.data.sound
    val CARD    = SoundManager.EnumSound.card.data.sound
    val WIN     = SoundManager.EnumSound.win.data.sound

    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound, coff: Float = 1f) = runGDX { if (isPause.not()) sound.play((volumeLevel / 100f) * coff) }
}