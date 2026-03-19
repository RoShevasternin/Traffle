package com.vortemika208.w1n.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.vortemika208.w1n.game.manager.AudioManager
import com.vortemika208.w1n.game.utils.runGDX
import com.vortemika208.w1n.game.manager.SoundManager
import kotlin.compareTo
import kotlin.div
import kotlin.times

class SoundUtil {

    val click   = AdvancedSound(SoundManager.EnumSound.click.data.sound, 1f)
    val roulette_fail = AdvancedSound(SoundManager.EnumSound.roulette_fail.data.sound, 1f)
    val roulette_win  = AdvancedSound(SoundManager.EnumSound.roulette_win.data.sound, 1f)
    val win           = AdvancedSound(SoundManager.EnumSound.win.data.sound, 1f)

    // 0..100
    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(advancedSound: AdvancedSound) {
        if (isPause.not()) {
            advancedSound.apply {
                sound.play((volumeLevel / 100f) * coff)
            }
        }
    }

    data class AdvancedSound(val sound: Sound, val coff: Float)
}