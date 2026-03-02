package com.shoote.maniapink.game.manager

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Sound

class SoundManager(var assetManager: AssetManager) {

    var loadableSoundList = mutableListOf<SoundData>()

    fun load() {
        loadableSoundList.onEach { assetManager.load(it.path, Sound::class.java) }
    }

    fun init() {
        loadableSoundList.onEach { it.sound = assetManager[it.path, Sound::class.java] }
        loadableSoundList.clear()
    }

    enum class EnumSound(val data: SoundData) {
        click  (SoundData("sound/click.mp3")),

        bomb   (SoundData("sound/bomb.mp3")),
        bonus  (SoundData("sound/bonus.mp3")),
        boom   (SoundData("sound/boom.mp3")),
        fail   (SoundData("sound/fail.mp3")),
        lose   (SoundData("sound/lose.mp3")),
        win    (SoundData("sound/win.mp3")),
        wooden (SoundData("sound/wooden.mp3")),
        load_of_big_gun_fun(SoundData("sound/load_of_big_gun_fun_.mp3")),
        shot_of_big_gun_fun(SoundData("sound/shot_of_big_gun_fun_.mp3")),
    }

    data class SoundData(
        val path: String,
    ) {
        lateinit var sound: Sound
    }

}