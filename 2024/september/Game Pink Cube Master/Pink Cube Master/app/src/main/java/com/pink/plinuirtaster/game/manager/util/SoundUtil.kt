package com.pink.plinuirtaster.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.pink.plinuirtaster.game.manager.AudioManager
import com.pink.plinuirtaster.game.utils.runGDX
import com.pink.plinuirtaster.game.manager.SoundManager

class SoundUtil {

    val CLICK   = SoundManager.EnumSound.CLICK.data.sound
    val ERROR        = SoundManager.EnumSound.ERROR.data.sound
    val PLUS         = SoundManager.EnumSound.PLUS.data.sound
    val RESULT_SHAKE = SoundManager.EnumSound.RESULT_SHAKE.data.sound
    val WIN          = SoundManager.EnumSound.WIN.data.sound
    private val WOODEN_1     = SoundManager.EnumSound.WOODEN_1.data.sound
    private val WOODEN_2     = SoundManager.EnumSound.WOODEN_2.data.sound

    val WOODEN_LIST = listOf(WOODEN_1, WOODEN_2)

    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound, coff: Float = 1f) = runGDX { if (isPause.not()) sound.play((volumeLevel / 100f) * coff) }
}