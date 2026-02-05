/*
 * Refactored Application Module
 * Build: B9A4B608
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.moonarcade.starlabyrinth.game.manager.AudioSystemController
import com.moonarcade.starlabyrinth.game.utils.runGDX
import com.moonarcade.starlabyrinth.game.manager.SoundEffectController

class SoundResourceHelper {

    val click = SoundEffectController.EnumSound.click.data.sound
    val touch = SoundEffectController.EnumSound.touch.data.sound

    private val boom1 = SoundEffectController.EnumSound.boom1.data.sound
    private val boom2 = SoundEffectController.EnumSound.boom2.data.sound
    private val boom3 = SoundEffectController.EnumSound.boom3.data.sound
    private val boom4 = SoundEffectController.EnumSound.boom4.data.sound
    private val boom5 = SoundEffectController.EnumSound.boom5.data.sound
    private val boom6 = SoundEffectController.EnumSound.boom6.data.sound
    private val boom7 = SoundEffectController.EnumSound.boom7.data.sound

    val boomList = listOf(boom1, boom2, boom3, boom4, boom5, boom6, boom7)

    // 0..100
    var volumeLevel = AudioSystemController.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound, volume: Float = volumeLevel) = runGDX { if (isPause.not()) sound.play(volume / 100f) }
}