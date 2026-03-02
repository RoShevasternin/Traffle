package com.fishfestival.bubbleparty.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.fishfestival.bubbleparty.game.manager.AudioManager
import com.fishfestival.bubbleparty.game.utils.runGDX
import com.fishfestival.bubbleparty.game.manager.SoundManager
import kotlin.compareTo
import kotlin.div
import kotlin.times

class SoundUtil {

    val click               = AdvancedSound(SoundManager.EnumSound.click.data.sound, 0.35f)
    val bomb                = AdvancedSound(SoundManager.EnumSound.bomb.data.sound, 0.35f)
    val bonus               = AdvancedSound(SoundManager.EnumSound.bonus.data.sound, 0.35f)
    val boom                = AdvancedSound(SoundManager.EnumSound.boom.data.sound, 0.35f)
    val fail                = AdvancedSound(SoundManager.EnumSound.fail.data.sound, 0.35f)
    val lose                = AdvancedSound(SoundManager.EnumSound.lose.data.sound, 0.35f)
    val win                 = AdvancedSound(SoundManager.EnumSound.win.data.sound, 0.35f)
    val wooden              = AdvancedSound(SoundManager.EnumSound.wooden.data.sound, 0.35f)
    val load_of_big_gun_fun = AdvancedSound(SoundManager.EnumSound.load_of_big_gun_fun.data.sound, 0.35f)
    val shot_of_big_gun_fun = AdvancedSound(SoundManager.EnumSound.shot_of_big_gun_fun.data.sound, 0.35f)

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