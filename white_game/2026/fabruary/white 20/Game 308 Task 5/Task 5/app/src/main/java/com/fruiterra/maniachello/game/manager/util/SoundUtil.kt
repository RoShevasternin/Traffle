package com.fruiterra.maniachello.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.fruiterra.maniachello.game.manager.AudioManager
import com.fruiterra.maniachello.game.utils.runGDX
import com.fruiterra.maniachello.game.manager.SoundManager
import kotlin.compareTo
import kotlin.div
import kotlin.times

class SoundUtil {

    val bonus = AdvancedSound(SoundManager.EnumSound.bonus.data.sound, 0.15f)
    val click = AdvancedSound(SoundManager.EnumSound.click.data.sound, 1f)
    val win   = AdvancedSound(SoundManager.EnumSound.win.data.sound, 0.5f)
    val touch   = AdvancedSound(SoundManager.EnumSound.touch.data.sound, 0.1f)

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