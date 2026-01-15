package com.fontantwo.tennisfortwo.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.fontantwo.tennisfortwo.game.manager.AudioManager
import com.fontantwo.tennisfortwo.game.utils.runGDX
import com.fontantwo.tennisfortwo.game.manager.SoundManager

class SoundUtil {

    private val boom1 = SoundManager.EnumSound.boom1.data.sound
    private val boom2 = SoundManager.EnumSound.boom2.data.sound
    private val boom3 = SoundManager.EnumSound.boom3.data.sound

    val click = SoundManager.EnumSound.click.data.sound
    val tennis = SoundManager.EnumSound.tennis.data.sound

    val goal = SoundManager.EnumSound.goal.data.sound

    val boomList = listOf(boom1, boom2, boom3)

    // 0..100
    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound, coff: Float = 1f) = runGDX { if (isPause.not()) sound.play((volumeLevel / 100f) * coff) }
}