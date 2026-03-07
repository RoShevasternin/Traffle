package com.neonticiton.toetacker.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.neonticiton.toetacker.game.manager.AudioManager
import com.neonticiton.toetacker.game.manager.SoundManager

class SoundUtil {

    val click     = AdvancedSound(SoundManager.EnumSound.click.data.sound, 1f)
    val bam       = AdvancedSound(SoundManager.EnumSound.bam.data.sound, 1f)
    val fail      = AdvancedSound(SoundManager.EnumSound.fail.data.sound, 1f)
    val win       = AdvancedSound(SoundManager.EnumSound.win.data.sound, 1f)

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