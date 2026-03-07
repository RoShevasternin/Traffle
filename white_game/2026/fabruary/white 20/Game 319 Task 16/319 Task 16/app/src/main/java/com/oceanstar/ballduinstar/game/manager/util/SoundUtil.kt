package com.oceanstar.ballduinstar.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.oceanstar.ballduinstar.game.manager.AudioManager
import com.oceanstar.ballduinstar.game.utils.runGDX
import com.oceanstar.ballduinstar.game.manager.SoundManager
import kotlin.compareTo
import kotlin.div
import kotlin.times

class SoundUtil {

    val click = AdvancedSound(SoundManager.EnumSound.click.data.sound, 0.3f)
    val bonus = AdvancedSound(SoundManager.EnumSound.bonus.data.sound, 0.35f)
    val touch = AdvancedSound(SoundManager.EnumSound.touch.data.sound, 0.5f)
    val win   = AdvancedSound(SoundManager.EnumSound.win.data.sound, 1f)

    // 0..100
    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(advancedSound: AdvancedSound, playCoff: Float = 1f) {
        if (isPause.not()) {
            advancedSound.apply {
                sound.play(((volumeLevel / 100f) * coff) * playCoff)
            }
        }
    }

    data class AdvancedSound(val sound: Sound, val coff: Float)
}