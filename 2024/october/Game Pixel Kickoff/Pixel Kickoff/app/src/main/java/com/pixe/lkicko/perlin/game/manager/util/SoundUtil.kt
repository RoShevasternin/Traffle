package com.pixe.lkicko.perlin.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.pixe.lkicko.perlin.game.manager.AudioManager
import com.pixe.lkicko.perlin.game.utils.runGDX
import com.pixe.lkicko.perlin.game.manager.SoundManager

class SoundUtil {

    val buy          = SoundManager.EnumSound.buy.data.sound
    val click        = SoundManager.EnumSound.click.data.sound
    val fail         = SoundManager.EnumSound.fail.data.sound
    val football_win = SoundManager.EnumSound.football_win.data.sound
    val soft_click   = SoundManager.EnumSound.soft_click.data.sound

    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound, coff: Float = 1f) = runGDX { if (isPause.not()) sound.play((volumeLevel / 100f) * coff) }
}