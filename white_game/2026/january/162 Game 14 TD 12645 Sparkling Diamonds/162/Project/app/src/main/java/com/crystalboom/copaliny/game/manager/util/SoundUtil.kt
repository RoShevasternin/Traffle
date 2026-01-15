package com.crystalboom.copaliny.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.crystalboom.copaliny.game.manager.AudioManager
import com.crystalboom.copaliny.game.utils.runGDX
import com.crystalboom.copaliny.game.manager.SoundManager

class SoundUtil {

    val childs_joy          = SoundManager.EnumSound.childs_joy.data.sound
    val click               = SoundManager.EnumSound.click.data.sound
    val item                = SoundManager.EnumSound.item.data.sound
    val plus                = SoundManager.EnumSound.plus.data.sound
    val soft_explosion_bomb = SoundManager.EnumSound.soft_explosion_bomb.data.sound

    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound, coff: Float = 1f) = runGDX { if (isPause.not()) sound.play((volumeLevel / 100f) * coff) }
}