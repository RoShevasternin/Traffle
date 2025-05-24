package com.viade.bepuzzle.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.viade.bepuzzle.game.manager.AudioManager
import com.viade.bepuzzle.game.utils.runGDX
import com.viade.bepuzzle.game.manager.SoundManager

class SoundUtil {

    val click          = AdvancedSound(SoundManager.EnumSound.click.data.sound, 0.5f)
    val win            = AdvancedSound(SoundManager.EnumSound.win.data.sound, 0.5f)
    val fail           = AdvancedSound(SoundManager.EnumSound.fail.data.sound, 0.8f)
    val use_bonus      = AdvancedSound(SoundManager.EnumSound.use_bonus.data.sound, 0.5f)
    val fail_use_bonus = AdvancedSound(SoundManager.EnumSound.fail_use_bonus.data.sound, 0.5f)
    val buy            = AdvancedSound(SoundManager.EnumSound.buy.data.sound, 0.75f)
    val fail_buy       = AdvancedSound(SoundManager.EnumSound.fail_buy.data.sound, 0.45f)
    val puzzle_click   = AdvancedSound(SoundManager.EnumSound.puzzle_click.data.sound, 0.5f)

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