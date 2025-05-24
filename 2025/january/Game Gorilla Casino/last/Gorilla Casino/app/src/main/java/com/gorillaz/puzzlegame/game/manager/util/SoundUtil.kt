package com.gorillaz.puzzlegame.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.gorillaz.puzzlegame.game.manager.AudioManager
import com.gorillaz.puzzlegame.game.utils.runGDX
import com.gorillaz.puzzlegame.game.manager.SoundManager
import kotlin.compareTo
import kotlin.div
import kotlin.times

class SoundUtil {

    val click = AdvancedSound(SoundManager.EnumSound.click.data.sound, 0.5f)

    val bet          = AdvancedSound(SoundManager.EnumSound.bet.data.sound, 1f)
    val coins        = AdvancedSound(SoundManager.EnumSound.coins.data.sound, 1f)
    val fail         = AdvancedSound(SoundManager.EnumSound.fail.data.sound, 0.85f)
    val spin         = AdvancedSound(SoundManager.EnumSound.spin.data.sound, 0.5f)
    val upgrade      = AdvancedSound(SoundManager.EnumSound.upgrade.data.sound, 0.6f)
    val win_roulette = AdvancedSound(SoundManager.EnumSound.win_roulette.data.sound, 0.5f)
    val win_slot     = AdvancedSound(SoundManager.EnumSound.win_slot.data.sound, 1f)
    val boom         = AdvancedSound(SoundManager.EnumSound.boom.data.sound, 1f)

    // 0..100
    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    var currentSound: Sound? = null

    fun play(advancedSound: AdvancedSound) {
        if (isPause.not()) {
            advancedSound.apply {
                currentSound = advancedSound.sound
                sound.play((volumeLevel / 100f) * coff)
            }
        }
    }

    data class AdvancedSound(val sound: Sound, val coff: Float)
}