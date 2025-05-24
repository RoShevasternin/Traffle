package com.pink.plinuirtaster.game.manager

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
        CLICK       (SoundData("sound/click.mp3")),
        ERROR       (SoundData("sound/error.mp3")),
        PLUS        (SoundData("sound/plus.mp3")),
        RESULT_SHAKE(SoundData("sound/result_shake.mp3")),
        WIN         (SoundData("sound/win.mp3")),
        WOODEN_1    (SoundData("sound/wooden_1.mp3")),
        WOODEN_2    (SoundData("sound/wooden_2.mp3")),
    }

    data class SoundData(
        val path: String,
    ) {
        lateinit var sound: Sound
    }

}