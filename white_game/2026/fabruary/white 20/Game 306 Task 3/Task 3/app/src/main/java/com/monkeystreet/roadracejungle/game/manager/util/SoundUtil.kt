package com.monkeystreet.roadracejungle.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.monkeystreet.roadracejungle.game.manager.AudioManager
import com.monkeystreet.roadracejungle.game.utils.runGDX
import com.monkeystreet.roadracejungle.game.manager.SoundManager
import kotlin.compareTo
import kotlin.div
import kotlin.times

class SoundUtil {

    val click = AdvancedSound(SoundManager.EnumSound.click.data.sound, 1f)
    val game_fail_jungle     = AdvancedSound(SoundManager.EnumSound.game_fail_jungle.data.sound, 1f)
    val game_step            = AdvancedSound(SoundManager.EnumSound.game_step.data.sound, 1f)
    val game_win_jungle_monk = AdvancedSound(SoundManager.EnumSound.game_win_jungle_monk.data.sound, 1f)

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