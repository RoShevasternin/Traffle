package com.portalend.fruitomaner.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.portalend.fruitomaner.game.manager.AudioManager
import com.portalend.fruitomaner.game.utils.runGDX
import com.portalend.fruitomaner.game.manager.SoundManager

class SoundUtil {

    val click   = SoundManager.EnumSound.click.data.sound
    val collect = SoundManager.EnumSound.collect.data.sound
    val win     = SoundManager.EnumSound.win.data.sound

    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound, coff: Float = 1f) = runGDX { if (isPause.not()) sound.play((volumeLevel / 100f) * coff) }
}