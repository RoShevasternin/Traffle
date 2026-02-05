/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.novaburst.pixelrally.game.manager.SoundController
import com.novaburst.pixelrally.game.utils.runGDX
import com.novaburst.pixelrally.game.manager.AudioController

class SoundUtil {

    val click = AudioController.EnumSound.click.data.sound
    val touch = AudioController.EnumSound.touch.data.sound

    private val boom1 = AudioController.EnumSound.boom1.data.sound
    private val boom2 = AudioController.EnumSound.boom2.data.sound
    private val boom3 = AudioController.EnumSound.boom3.data.sound
    private val boom4 = AudioController.EnumSound.boom4.data.sound
    private val boom5 = AudioController.EnumSound.boom5.data.sound
    private val boom6 = AudioController.EnumSound.boom6.data.sound
    private val boom7 = AudioController.EnumSound.boom7.data.sound

    val boomList = listOf(boom1, boom2, boom3, boom4, boom5, boom6, boom7)

    // 0..100
    var volumeLevel = SoundController.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound, volume: Float = volumeLevel) = runGDX { if (isPause.not()) sound.play(volume / 100f) }
}