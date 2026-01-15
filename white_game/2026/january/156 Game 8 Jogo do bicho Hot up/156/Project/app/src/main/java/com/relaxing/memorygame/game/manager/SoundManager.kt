package com.relaxing.memorygame.game.manager

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
        TOUCH(SoundData("sound/touch.mp3")),
        LOSE(SoundData("sound/lose.mp3")),
        NEPARA(SoundData("sound/nepara.mp3")),
        URA(SoundData("sound/ura.mp3")),
        PARA(SoundData("sound/para.mp3")),
    }

    data class SoundData(
        val path: String,
    ) {
        lateinit var sound: Sound
    }

}