package com.princesssand.soundsprinss.game.manager

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
        click(SoundData("sound/click.wav")),
        get  (SoundData("sound/get.wav")),
        save (SoundData("sound/save.wav")),
        win  (SoundData("sound/win.wav")),
        border(SoundData("sound/border.mp3")),
        touch (SoundData("sound/touch.mp3")),
    }

    data class SoundData(
        val path: String,
    ) {
        lateinit var sound: Sound
    }

}