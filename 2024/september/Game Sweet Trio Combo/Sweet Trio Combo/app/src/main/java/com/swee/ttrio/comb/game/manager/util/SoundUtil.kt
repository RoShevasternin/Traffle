package com.swee.ttrio.comb.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.swee.ttrio.comb.game.manager.AudioManager
import com.swee.ttrio.comb.game.utils.runGDX
import com.swee.ttrio.comb.game.manager.SoundManager

class SoundUtil {

    val card           = SoundManager.EnumSound.card.data.sound
    val click          = SoundManager.EnumSound.click_game.data.sound
    val gray           = SoundManager.EnumSound.gray.data.sound
    val select         = SoundManager.EnumSound.select.data.sound
    val super_win_game = SoundManager.EnumSound.super_win_game.data.sound

    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound, coff: Float = 1f) = runGDX { if (isPause.not()) sound.play((volumeLevel / 100f) * coff) }
}