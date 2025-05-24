package com.pixe.lkicko.perlin.game.manager

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
        buy          (SoundData("sound/buy.mp3")),
        click        (SoundData("sound/click.mp3")),
        fail         (SoundData("sound/fail.mp3")),
        football_win (SoundData("sound/football_win.mp3")),
        soft_click   (SoundData("sound/soft_click.mp3")),
    }

    data class SoundData(
        val path: String,
    ) {
        lateinit var sound: Sound
    }

}