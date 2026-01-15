package com.cosmicbounce.galaxytic.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.cosmicbounce.galaxytic.game.manager.AudioManager
import com.cosmicbounce.galaxytic.game.utils.runGDX
import com.cosmicbounce.galaxytic.game.manager.SoundManager

class SoundUtil {

    val click   = SoundManager.EnumSound.click_mouse.data.sound
    private val hit_a_tree_1 = SoundManager.EnumSound.hit_a_tree_1.data.sound
    private val hit_a_tree_2 = SoundManager.EnumSound.hit_a_tree_2.data.sound
    private val hit_a_tree_3 = SoundManager.EnumSound.hit_a_tree_3.data.sound

    val hitList = listOf(hit_a_tree_1, hit_a_tree_2, hit_a_tree_3)

    val jump         = SoundManager.EnumSound.jump.data.sound
    val win_in_game  = SoundManager.EnumSound.win_in_game.data.sound

    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound, coff: Float = 1f) = runGDX { if (isPause.not()) sound.play((volumeLevel / 100f) * coff) }
}