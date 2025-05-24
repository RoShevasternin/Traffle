package com.shoote.maniapink.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.shoote.maniapink.game.manager.AudioManager
import com.shoote.maniapink.game.utils.runGDX
import com.shoote.maniapink.game.manager.SoundManager

class SoundUtil {

    val click  = SoundManager.EnumSound.click.data.sound

    val bomb   = SoundManager.EnumSound.bomb.data.sound
    val bonus  = SoundManager.EnumSound.bonus.data.sound
    val boom   = SoundManager.EnumSound.boom.data.sound
    val fail   = SoundManager.EnumSound.fail.data.sound
    val lose   = SoundManager.EnumSound.lose.data.sound
    val win    = SoundManager.EnumSound.win.data.sound
    val wooden = SoundManager.EnumSound.wooden.data.sound

   val load_of_big_gun_fun = SoundManager.EnumSound.load_of_big_gun_fun.data.sound
   val shot_of_big_gun_fun = SoundManager.EnumSound.shot_of_big_gun_fun.data.sound

    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound, coff: Float = 1f) = runGDX { if (isPause.not()) sound.play((volumeLevel / 100f) * coff) }
}