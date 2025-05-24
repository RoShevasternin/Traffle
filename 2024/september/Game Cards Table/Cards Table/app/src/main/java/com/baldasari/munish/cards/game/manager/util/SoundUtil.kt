package com.baldasari.munish.cards.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.baldasari.munish.cards.game.manager.AudioManager
import com.baldasari.munish.cards.game.utils.runGDX
import com.baldasari.munish.cards.game.manager.SoundManager

class SoundUtil {

    val magical_click   = SoundManager.EnumSound.magical_click.data.sound
    val card            = SoundManager.EnumSound.card.data.sound
    val magical_victory = SoundManager.EnumSound.magical_victory.data.sound
    val select          = SoundManager.EnumSound.select.data.sound

    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound, coff: Float = 1f) = runGDX { if (isPause.not()) sound.play((volumeLevel / 100f) * coff) }
}