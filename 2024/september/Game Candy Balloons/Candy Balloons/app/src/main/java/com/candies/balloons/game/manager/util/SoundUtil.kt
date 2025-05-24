package com.candies.balloons.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.candies.balloons.game.manager.AudioManager
import com.candies.balloons.game.utils.runGDX
import com.candies.balloons.game.manager.SoundManager

class SoundUtil {

    val ball  = SoundManager.EnumSound.ball.data.sound
    val click = SoundManager.EnumSound.click.data.sound
    val cubik = SoundManager.EnumSound.cubik.data.sound
    val point = SoundManager.EnumSound.point.data.sound
    val won   = SoundManager.EnumSound.won.data.sound

    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound, coff: Float = 1f) = runGDX { if (isPause.not()) sound.play((volumeLevel / 100f) * coff) }
}