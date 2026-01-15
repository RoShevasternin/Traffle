package com.sweetfruit.catchermandarin.game.manager

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
        boom(SoundData("sundeo/boom.mp3")),
        clik(SoundData("sundeo/clik.ogg")),
    }

    data class SoundData(
        val path: String,
    ) {
        lateinit var sound: Sound
    }

}